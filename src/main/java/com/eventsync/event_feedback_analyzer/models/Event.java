package com.eventsync.event_feedback_analyzer.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private List<Feedback> feedbackList = new ArrayList<>();

    // Constructors
    public Event() {
        this.createdAt = LocalDateTime.now();
    }

    public Event(Long Id, String name, String description) {
        this();
        this.id = Id;
        this.name = name;
        this.description = description;
    }

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(Long Id) {
        this.id = Id;
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

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    // Helper methods to add feedback to the list
    public void addFeedback(Feedback feedback) {
        this.feedbackList.add(feedback);
        feedback.setEvent(this);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", createdAt=" + createdAt +
                ", feedbackList=" + feedbackList +
                '}';
    }
}
