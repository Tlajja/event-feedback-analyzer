package com.eventsync.event_feedback_analyzer.dtos;

public class CreateEventRequest {
    public String name;
    public String description;

    // Constructors
    public CreateEventRequest() {
    }

    public CreateEventRequest(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters

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
}
