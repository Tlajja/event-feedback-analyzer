package com.eventsync.event_feedback_analyzer.services;

import org.springframework.stereotype.Service;

import com.eventsync.event_feedback_analyzer.enums.Sentiment;

@Service
public class SentimentAnalysisService {

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
        // TODO: Call the sentiment analysis API and get the sentiment and scores
        return new SentimentAnalysisResult(Sentiment.POSITIVE, 0.8, 0.1, 0.1);
    }
}
