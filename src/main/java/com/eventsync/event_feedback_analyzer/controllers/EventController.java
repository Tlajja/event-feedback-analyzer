package com.eventsync.event_feedback_analyzer.controllers;

import com.eventsync.event_feedback_analyzer.services.EventService;

import jakarta.validation.Valid;

import com.eventsync.event_feedback_analyzer.dtos.*;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // POST /events - Create new event
    @PostMapping
    public ResponseEntity<EventResponse> createEvent(@Valid @RequestBody CreateEventRequest request) {
        EventResponse eventResponse = eventService.createEvent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(eventResponse);
    }

    // Get /events - Get all events
    @GetMapping
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        List<EventResponse> eventResponses = eventService.getAllEvents();
        return ResponseEntity.ok(eventResponses);
    }

    // Get /events/{id} - Get event by id
    @GetMapping("/{id}")
    public ResponseEntity<EventResponse> getEventById(@PathVariable Long id) {
        EventResponse eventResponse = eventService.getEventResponseById(id);
        return ResponseEntity.ok(eventResponse);
    }

    // Get /events/{id}/summary - Get event summary by id
    @GetMapping("/{id}/summary")
    public ResponseEntity<EventSummaryResponse> getEventSummary(@PathVariable Long id) {
        EventSummaryResponse eventSummaryResponse = eventService.getEventSummary(id);
        return ResponseEntity.ok(eventSummaryResponse);
    }
}
