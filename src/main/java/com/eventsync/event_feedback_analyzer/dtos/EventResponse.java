package com.eventsync.event_feedback_analyzer.dtos;

import java.time.LocalDateTime;

public class EventResponse {
    private long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private int feedbackCount;

    // Constructors

    public EventResponse() {
    }

    public EventResponse(long id, String name, String description, LocalDateTime createdAt, int feedbackCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.feedbackCount = feedbackCount;
    }

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getFeedbackCount() {
        return feedbackCount;
    }

    public void setFeedbackCount(int feedbackCount) {
        this.feedbackCount = feedbackCount;
    }
}
