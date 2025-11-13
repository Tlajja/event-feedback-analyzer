package com.eventsync.event_feedback_analyzer.services;

import org.springframework.stereotype.Service;

import com.eventsync.event_feedback_analyzer.dtos.*;
import com.eventsync.event_feedback_analyzer.enums.Sentiment;
import com.eventsync.event_feedback_analyzer.models.*;

import java.util.List;
import java.util.ArrayList;

@Service
public class EventService {
    private final List<Event> events = new ArrayList<>();
    private long eventIdCounter = 1;

    public EventResponse createEvent(CreateEventRequest request) {
        long newId = eventIdCounter++;
        Event event = new Event(newId, request.getName(), request.getDescription());
        events.add(event);
        return convertToEventResponse(event);
    }

    public List<EventResponse> getAllEvents() {
        return events
                .stream()
                .map(this::convertToEventResponse)
                .toList();
    }

    public Event getEventById(long id) {
        return events
                .stream()
                .filter(e -> e.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public EventResponse getEventResponseById(long id) {
        Event event = getEventById(id);
        return event != null ? convertToEventResponse(event) : null;
    }

    public EventResponse convertToEventResponse(Event event) {
        return new EventResponse(
                event.getId(),
                event.getName(),
                event.getDescription(),
                event.getCreatedAt(),
                event.getFeedbackList().size());
    }

    public EventSummaryResponse getEventSummary(long id) {
        Event event = getEventById(id);

        if (event == null) {
            return null;
        }

        int positiveCount = 0;
        int negativeCount = 0;
        int neutralCount = 0;

        for (Feedback feedback : event.getFeedbackList()) {
            if (feedback.getSentiment() == Sentiment.POSITIVE) {
                positiveCount++;
            } else if (feedback.getSentiment() == Sentiment.NEGATIVE) {
                negativeCount++;
            } else {
                neutralCount++;
            }
        }

        int totalFeedback = positiveCount + negativeCount + neutralCount;

        return new EventSummaryResponse(
                event.getId(),
                event.getName(),
                totalFeedback,
                positiveCount,
                negativeCount,
                neutralCount);
    }
}
