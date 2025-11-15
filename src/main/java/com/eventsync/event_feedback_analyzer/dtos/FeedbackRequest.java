package com.eventsync.event_feedback_analyzer.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class FeedbackRequest {

    @NotBlank(message = "Feedback content is required")
    @Size(max = 2000, message = "Feedback content must not exceed 2000 characters")
    private String content;

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
