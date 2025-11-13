package com.eventsync.event_feedback_analyzer.services;

import org.springframework.stereotype.Service;

import com.eventsync.event_feedback_analyzer.dtos.*;
import com.eventsync.event_feedback_analyzer.enums.Sentiment;
import com.eventsync.event_feedback_analyzer.models.*;
import com.eventsync.event_feedback_analyzer.repositories.EventRepository;

import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public EventResponse createEvent(CreateEventRequest request) {
        Event event = new Event();
        event.setName(request.getName());
        event.setDescription(request.getDescription());

        Event savedEvent = eventRepository.save(event);

        return convertToEventResponse(savedEvent);
    }

    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll()
                .stream()
                .map(this::convertToEventResponse)
                .toList();
    }

    public Event getEventById(long id) {
        return eventRepository.findById(id).orElse(null);
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
                neutralCount,
                negativeCount);
    }
}
