package com.eventsync.event_feedback_analyzer.services;

import com.eventsync.event_feedback_analyzer.enums.Sentiment;
import com.eventsync.event_feedback_analyzer.exceptions.SentimentAnalysisException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SentimentAnalysisServiceTest {

    private final SentimentAnalysisService service = new SentimentAnalysisService();

    @Test
    @DisplayName("parseHuggingFaceResponse should pick sentiment with highest score")
    void parse_picksHighestScore() throws Exception {
        String json = """
            [
              [
                {"label": "positive", "score": 0.8},
                {"label": "neutral",  "score": 0.1},
                {"label": "negative", "score": 0.1}
              ]
            ]
            """;

        SentimentAnalysisService.SentimentAnalysisResult result =
                service.parseHuggingFaceResponse(json);

        assertEquals(Sentiment.POSITIVE, result.getSentiment());
        assertEquals(0.8, result.getPositiveScore(), 1e-6);
        assertEquals(0.1, result.getNeutralScore(), 1e-6);
        assertEquals(0.1, result.getNegativeScore(), 1e-6);
    }

    @Test
    @DisplayName("parseHuggingFaceResponse should throw for invalid response shape")
    void parse_invalid_throws() {
        String invalidJson = "{}";

        assertThrows(SentimentAnalysisException.class,
                () -> service.parseHuggingFaceResponse(invalidJson));
    }
}
