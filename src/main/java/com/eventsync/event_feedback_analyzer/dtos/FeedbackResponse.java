package com.eventsync.event_feedback_analyzer.dtos;

import com.eventsync.event_feedback_analyzer.enums.Sentiment;
import java.time.LocalDateTime;

public class FeedbackResponse {
    private long id;
    private String content;
    private Sentiment sentiment;
    private double positiveScore;
    private double neutralScore;
    private double negativeScore;
    private LocalDateTime createdAt;
    private long eventId;

    // Constructors
    public FeedbackResponse() {
    }

    public FeedbackResponse(long id, String content, Sentiment sentiment, double positiveScore, double neutralScore,
            double negativeScore, LocalDateTime createdAt, long eventId) {
        this.id = id;
        this.content = content;
        this.sentiment = sentiment;
        this.positiveScore = positiveScore;
        this.neutralScore = neutralScore;
        this.negativeScore = negativeScore;
        this.createdAt = createdAt;
        this.eventId = eventId;
    }

    // Getters and Setters

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

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

}
