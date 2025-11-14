package com.eventsync.event_feedback_analyzer.dtos;

public class EventSummaryResponse {
    private long eventId;
    private String eventName;
    private int totalFeedback;
    private int totalPositiveFeedback;
    private int totalNegativeFeedback;
    private int totalNeutralFeedback;

    // Constructors
    public EventSummaryResponse() {
    }

    public EventSummaryResponse(long eventId, String eventName, int totalFeedback, int totalPositiveFeedback,
            int totalNegativeFeedback, int totalNeutralFeedback) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.totalFeedback = totalFeedback;
        this.totalPositiveFeedback = totalPositiveFeedback;
        this.totalNegativeFeedback = totalNegativeFeedback;
        this.totalNeutralFeedback = totalNeutralFeedback;
    }

    // Getters and Setters

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public long getEventId() {
        return eventId;
    }

    public void setEventId(long eventId) {
        this.eventId = eventId;
    }

    public int getTotalFeedback() {
        return totalFeedback;
    }

    public void setTotalFeedback(int totalFeedback) {
        this.totalFeedback = totalFeedback;
    }

    public int getTotalPositiveFeedback() {
        return totalPositiveFeedback;
    }

    public void setTotalPositiveFeedback(int totalPositiveFeedback) {
        this.totalPositiveFeedback = totalPositiveFeedback;
    }

    public int getTotalNegativeFeedback() {
        return totalNegativeFeedback;
    }

    public void setTotalNegativeFeedback(int totalNegativeFeedback) {
        this.totalNegativeFeedback = totalNegativeFeedback;
    }

    public int getTotalNeutralFeedback() {
        return totalNeutralFeedback;
    }

    public void setTotalNeutralFeedback(int totalNeutralFeedback) {
        this.totalNeutralFeedback = totalNeutralFeedback;
    }

    // Helper methods for percentages

    public double getPositivePercentage() {
        if (totalFeedback == 0) {
            return 0.0;
        }
        return (double) totalPositiveFeedback / totalFeedback * 100;
    }

    public double getNegativePercentage() {
        if (totalFeedback == 0) {
            return 0.0;
        }
        return (double) totalNegativeFeedback / totalFeedback * 100;
    }

    public double getNeutralPercentage() {
        if (totalFeedback == 0) {
            return 0.0;
        }
        return (double) totalNeutralFeedback / totalFeedback * 100;
    }
}
