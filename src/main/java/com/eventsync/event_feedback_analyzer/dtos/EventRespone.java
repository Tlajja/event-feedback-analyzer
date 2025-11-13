package com.eventsync.event_feedback_analyzer.dtos;

import java.time.LocalDateTime;

public class EventRespone {
    private long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private int FeedbackCount;

    // Constructors

    public EventRespone() {
    }

    public EventRespone(long id, String name, String description, LocalDateTime createdAt, int FeedbackCount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
        this.FeedbackCount = FeedbackCount;
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

    public String setDescription(String description) {
        this.description = description;
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getFeedbackCount() {
        return FeedbackCount;
    }

    public void setFeedbackCount(int FeedbackCount) {
        this.FeedbackCount = FeedbackCount;
    }
}
