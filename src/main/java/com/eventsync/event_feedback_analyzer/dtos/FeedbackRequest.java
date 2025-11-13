package com.eventsync.event_feedback_analyzer.dtos;

public class FeedbackRequest {
    public String content;

    // Constructors

    public FeedbackRequest() {
    }

    public FeedbackRequest(String content) {
        this.content = content;
    }

    // Getters and Setters

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
