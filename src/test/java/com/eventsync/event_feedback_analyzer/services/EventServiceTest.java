package com.eventsync.event_feedback_analyzer.services;

import com.eventsync.event_feedback_analyzer.dtos.CreateEventRequest;
import com.eventsync.event_feedback_analyzer.dtos.EventResponse;
import com.eventsync.event_feedback_analyzer.dtos.EventSummaryResponse;
import com.eventsync.event_feedback_analyzer.enums.Sentiment;
import com.eventsync.event_feedback_analyzer.exceptions.ResourceNotFoundException;
import com.eventsync.event_feedback_analyzer.models.Event;
import com.eventsync.event_feedback_analyzer.models.Feedback;
import com.eventsync.event_feedback_analyzer.repositories.EventRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    @DisplayName("createEvent should save event and return EventResponse")
    void createEvent_savesAndReturnsResponse() {
        CreateEventRequest request = new CreateEventRequest("Workshop", "Spring Boot intro");

        Event saved = new Event();
        saved.setId(1L);
        saved.setName("Workshop");
        saved.setDescription("Spring Boot intro");

        when(eventRepository.save(any(Event.class))).thenReturn(saved);

        EventResponse response = eventService.createEvent(request);

        assertEquals(1L, response.getId());
        assertEquals("Workshop", response.getName());
        assertEquals("Spring Boot intro", response.getDescription());
        verify(eventRepository, times(1)).save(any(Event.class));
    }

    @Test
    @DisplayName("getEventSummary should correctly count POSITIVE/NEGATIVE/NEUTRAL feedback")
    void getEventSummary_countsSentiments() {
        Event event = new Event();
        event.setId(10L);
        event.setName("Team Building");

        Feedback f1 = new Feedback();
        f1.setSentiment(Sentiment.POSITIVE);
        event.addFeedback(f1);

        Feedback f2 = new Feedback();
        f2.setSentiment(Sentiment.POSITIVE);
        event.addFeedback(f2);

        Feedback f3 = new Feedback();
        f3.setSentiment(Sentiment.NEGATIVE);
        event.addFeedback(f3);

        Feedback f4 = new Feedback();
        f4.setSentiment(Sentiment.NEUTRAL);
        event.addFeedback(f4);

        when(eventRepository.findById(10L)).thenReturn(Optional.of(event));

        EventSummaryResponse summary = eventService.getEventSummary(10L);

        assertEquals(10L, summary.getEventId());
        assertEquals("Team Building", summary.getEventName());
        assertEquals(4, summary.getTotalFeedback());
        assertEquals(2, summary.getTotalPositiveFeedback());
        assertEquals(1, summary.getTotalNegativeFeedback());
        assertEquals(1, summary.getTotalNeutralFeedback());
    }

    @Test
    @DisplayName("getEventById should throw when event not found")
    void getEventById_notFound_throws() {
        when(eventRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> eventService.getEventById(99L));
    }
}

