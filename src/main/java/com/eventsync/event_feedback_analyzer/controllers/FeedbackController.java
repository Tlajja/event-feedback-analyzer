package com.eventsync.event_feedback_analyzer.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.eventsync.event_feedback_analyzer.services.FeedbackService;

import jakarta.validation.Valid;

import com.eventsync.event_feedback_analyzer.dtos.*;

import java.util.List;

@RestController
@RequestMapping("/events/{eventId}/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    // POST /events/{eventId}/feedback - Submit feedback for an event
    @PostMapping
    public ResponseEntity<FeedbackResponse> submitFeedback(
            @PathVariable Long eventId,
            @Valid @RequestBody FeedbackRequest request) {

        FeedbackResponse response = feedbackService.submitFeedback(eventId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // GET /events/{eventId}/feedback - Get all feedback for an event
    @GetMapping
    public ResponseEntity<List<FeedbackResponse>> getEventFeedback(@PathVariable Long eventId) {
        List<FeedbackResponse> feedback = feedbackService.getFeedbacksById(eventId);
        return ResponseEntity.ok(feedback);
    }
}
