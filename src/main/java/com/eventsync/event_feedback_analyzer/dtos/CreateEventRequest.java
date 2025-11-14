package com.eventsync.event_feedback_analyzer.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateEventRequest {
    @NotBlank(message = "Event name is required")
    @Size(max = 255, message = "Event name must not exceed 255 characters")
    public String name;

    @Size(max = 1000, message = "Description must not exceed 1000 characters")
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
