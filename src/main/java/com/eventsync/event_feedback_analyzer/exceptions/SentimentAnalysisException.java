package com.eventsync.event_feedback_analyzer.exceptions;

public class SentimentAnalysisException extends RuntimeException {

    public SentimentAnalysisException(String message) {
        super(message);
    }

    public SentimentAnalysisException(String message, Throwable cause) {
        super(message, cause);
    }
}
