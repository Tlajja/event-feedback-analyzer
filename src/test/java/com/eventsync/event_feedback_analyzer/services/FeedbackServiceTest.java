package com.eventsync.event_feedback_analyzer.services;

import com.eventsync.event_feedback_analyzer.dtos.FeedbackRequest;
import com.eventsync.event_feedback_analyzer.dtos.FeedbackResponse;
import com.eventsync.event_feedback_analyzer.enums.Sentiment;
import com.eventsync.event_feedback_analyzer.models.Event;
import com.eventsync.event_feedback_analyzer.models.Feedback;
import com.eventsync.event_feedback_analyzer.repositories.FeedbackRepository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FeedbackServiceTest {

    @Mock
    private EventService eventService;

    @Mock
    private SentimentAnalysisService sentimentAnalysisService;

    @Mock
    private FeedbackRepository feedbackRepository;

    @InjectMocks
    private FeedbackService feedbackService;

    @Test
    @DisplayName("submitFeedback should save feedback with sentiment and return DTO")
    void submitFeedback_savesAndReturns() {
        long eventId = 1L;
        FeedbackRequest request = new FeedbackRequest("Great event!");

        Event event = new Event();
        event.setId(eventId);
        event.setName("My Event");

        when(eventService.getEventById(eventId)).thenReturn(event);

        SentimentAnalysisService.SentimentAnalysisResult result =
                new SentimentAnalysisService.SentimentAnalysisResult(
                        Sentiment.POSITIVE,
                        0.9,
                        0.05,
                        0.05);

        when(sentimentAnalysisService.analyzeSentiment("Great event!"))
                .thenReturn(result);

        Feedback saved = new Feedback();
        saved.setId(100L);
        saved.setContent("Great event!");
        saved.setEvent(event);
        saved.setSentiment(Sentiment.POSITIVE);
        saved.setPositiveScore(0.9);
        saved.setNeutralScore(0.05);
        saved.setNegativeScore(0.05);
        saved.setCreatedAt(LocalDateTime.now());

        when(feedbackRepository.save(any(Feedback.class))).thenReturn(saved);

        FeedbackResponse response = feedbackService.submitFeedback(eventId, request);

        assertEquals(100L, response.getId());
        assertEquals("Great event!", response.getContent());
        assertEquals(Sentiment.POSITIVE, response.getSentiment());
        assertEquals(eventId, response.getEventId());
        assertEquals(0.9, response.getPositiveScore(), 1e-6);

        verify(eventService).getEventById(eventId);
        verify(sentimentAnalysisService).analyzeSentiment("Great event!");
        verify(feedbackRepository).save(any(Feedback.class));
    }

    @Test
    @DisplayName("getFeedbacksById should map Feedback entities to DTOs")
    void getFeedbacksById_mapsToDto() {
        Event event = new Event();
        event.setId(1L);

        Feedback f = new Feedback();
        f.setId(10L);
        f.setContent("ok");
        f.setEvent(event);
        f.setSentiment(Sentiment.NEUTRAL);
        f.setCreatedAt(LocalDateTime.now());

        when(feedbackRepository.findByEventId(1L)).thenReturn(List.of(f));

        var responses = feedbackService.getFeedbacksById(1L);

        assertEquals(1, responses.size());
        FeedbackResponse resp = responses.get(0);
        assertEquals(10L, resp.getId());
        assertEquals("ok", resp.getContent());
        assertEquals(Sentiment.NEUTRAL, resp.getSentiment());
        assertEquals(1L, resp.getEventId());
    }
}
