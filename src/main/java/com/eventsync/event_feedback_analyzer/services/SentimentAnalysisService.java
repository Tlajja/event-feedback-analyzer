package com.eventsync.event_feedback_analyzer.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.eventsync.event_feedback_analyzer.enums.Sentiment;
import com.eventsync.event_feedback_analyzer.exceptions.SentimentAnalysisException;

@Service
public class SentimentAnalysisService {
    @Value("${HUGGINGFACE_API_TOKEN:${huggingface.api.token}}")
    private String apiToken;

    @Value("${huggingface.api.url:https://router.huggingface.co/hf-inference/models/cardiffnlp/twitter-roberta-base-sentiment-latest}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public SentimentAnalysisService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public static class SentimentAnalysisResult {
        private Sentiment sentiment;
        private double positiveScore;
        private double neutralScore;
        private double negativeScore;

        public SentimentAnalysisResult(Sentiment sentiment, double positiveScore, double neutralScore,
                double negativeScore) {
            this.sentiment = sentiment;
            this.positiveScore = positiveScore;
            this.neutralScore = neutralScore;
            this.negativeScore = negativeScore;
        }

        public Sentiment getSentiment() {
            return sentiment;
        }

        public double getPositiveScore() {
            return positiveScore;
        }

        public double getNeutralScore() {
            return neutralScore;
        }

        public double getNegativeScore() {
            return negativeScore;
        }
    }

    public SentimentAnalysisResult analyzeSentiment(String text) {
        if (text == null || text.trim().isEmpty()) {
            throw new IllegalArgumentException("Text for sentiment analysis cannot be null or empty");
        }

        if (apiToken == null || apiToken.isEmpty()) {
            throw new SentimentAnalysisException("Hugging Face API token is not configured");
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiToken);
            headers.set("Content-Type", "application/json");
            headers.set("Accept", "application/json");

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("inputs", text);

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(apiUrl, request, String.class);

            if (responseEntity.getBody() == null) {
                throw new SentimentAnalysisException("Received empty response from sentiment analysis API");
            }

            String response = responseEntity.getBody();

            return parseHuggingFaceResponse(response);
        } catch (RestClientException e) {
            throw new SentimentAnalysisException("Failed to connect to sentiment analysis service", e);
        } catch (Exception e) {
            throw new SentimentAnalysisException("Failed to analyze sentiment: " + e.getMessage(), e);
        }
    }

    public SentimentAnalysisResult parseHuggingFaceResponse(String responseBody) throws Exception {
        try {
            JsonNode root = objectMapper.readTree(responseBody);

            if (!root.isArray() || root.size() == 0) {
                throw new SentimentAnalysisException("Invalid response format from sentiment analysis API");
            }

            JsonNode results = root.get(0);

            double positiveScore = 0.0;
            double negativeScore = 0.0;
            double neutralScore = 0.0;

            for (JsonNode result : results) {
                if (!result.has("label") || !result.has("score")) {
                    throw new SentimentAnalysisException("Missing required fields in API response");
                }

                String label = result.get("label").asText().toLowerCase();
                double score = result.get("score").asDouble();

                switch (label) {
                    case "positive":
                        positiveScore = score;
                        break;
                    case "negative":
                        negativeScore = score;
                        break;
                    case "neutral":
                        neutralScore = score;
                        break;
                }
            }

            Sentiment sentiment;
            if (positiveScore > negativeScore && positiveScore > neutralScore) {
                sentiment = Sentiment.POSITIVE;
            } else if (negativeScore > positiveScore && negativeScore > neutralScore) {
                sentiment = Sentiment.NEGATIVE;
            } else {
                sentiment = Sentiment.NEUTRAL;
            }
            return new SentimentAnalysisResult(sentiment, positiveScore, neutralScore, negativeScore);
        } catch (Exception e) {
            throw new SentimentAnalysisException("Failed to parse sentiment analysis response", e);
        }
    }
}
