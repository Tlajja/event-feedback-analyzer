package com.eventsync.event_feedback_analyzer.models;

import com.eventsync.event_feedback_analyzer.enums.Sentiment;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 2000)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sentiment sentiment;

    @Column(name = "positive_score")
    private double positiveScore;

    @Column(name = "neutral_score")
    private double neutralScore;

    @Column(name = "negative_score")
    private double negativeScore;

    @Column(nullable = false, name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
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
