package com.eventsync.event_feedback_analyzer.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
    public Long getId() {
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
                ", feedbackCount=" + feedbackList.size() +
                '}';
    }
}
