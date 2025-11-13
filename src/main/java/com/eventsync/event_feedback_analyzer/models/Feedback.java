package com.eventsync.event_feedback_analyzer.models;

import com.eventsync.event_feedback_analyzer.enums.Sentiment;
import java.time.LocalDateTime;

public class Feedback {
    private long id;
    private String content;
    private Sentiment sentiment;
    private double positiveScore;
    private double neutralScore;
    private double negativeScore;
    private LocalDateTime createdAt;
    private Event event;

    // Constructors
    public Feedback() {
        this.createdAt = LocalDateTime.now();
    }

    public Feedback(long id, String content, LocalDateTime createdAt, Event event) {
        this();
        this.id = id;
        this.content = content;
        this.event = event;
    }

    // Getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public double getPositiveScore() {
        return positiveScore;
    }

    public void setPositiveScore(double positiveScore) {
        this.positiveScore = positiveScore;
    }

    public double getNeutralScore() {
        return neutralScore;
    }

    public void setNeutralScore(double neutralScore) {
        this.neutralScore = neutralScore;
    }

    public double getNegativeScore() {
        return negativeScore;
    }

    public void setNegativeScore(double negativeScore) {
        this.negativeScore = negativeScore;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", sentiment=" + sentiment +
                ", positiveScore=" + positiveScore +
                ", neutralScore=" + neutralScore +
                ", negativeScore=" + negativeScore +
                ", createdAt=" + createdAt +
                ", eventId=" + event.getId() +
                '}';
    }

}
