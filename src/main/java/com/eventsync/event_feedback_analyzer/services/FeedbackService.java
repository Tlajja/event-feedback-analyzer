package com.eventsync.event_feedback_analyzer.services;

import com.eventsync.event_feedback_analyzer.dtos.*;
import com.eventsync.event_feedback_analyzer.models.*;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FeedbackService {
    private final EventService eventService;
    private final SentimentAnalysisService sentimentAnalysisService;
    private long feedbackIdCounter = 1;

    public FeedbackService(EventService eventService, SentimentAnalysisService sentimentAnalysisService) {
        this.eventService = eventService;
        this.sentimentAnalysisService = sentimentAnalysisService;
    }

    public FeedbackResponse submitFeedback(long id, FeedbackRequest request) {
        Event event = eventService.getEventById(id);
        if (event == null)
            return null;

        SentimentAnalysisService.SentimentAnalysisResult sentimentResult = sentimentAnalysisService
                .analyzeSentiment(request.getContent());

        Feedback feedback = new Feedback(
                feedbackIdCounter++,
                request.getContent(),
                null,
                event);

        feedback.setSentiment(sentimentResult.getSentiment());
        feedback.setPositiveScore(sentimentResult.getPositiveScore());
        feedback.setNeutralScore(sentimentResult.getNeutralScore());
        feedback.setNegativeScore(sentimentResult.getNegativeScore());

        event.addFeedback(feedback);

        return convertToFeedbackResponse(feedback);
    }

    public List<FeedbackResponse> getFeedbacksById(long id) {
        Event event = eventService.getEventById(id);
        if (event == null)
            return List.of();
        return event.getFeedbackList()
                .stream()
                .map(this::convertToFeedbackResponse)
                .toList();
    }

    public FeedbackResponse convertToFeedbackResponse(Feedback feedback) {
        return new FeedbackResponse(
                feedback.getId(),
                feedback.getContent(),
                feedback.getSentiment(),
                feedback.getPositiveScore(),
                feedback.getNeutralScore(),
                feedback.getNegativeScore(),
                feedback.getCreatedAt(),
                feedback.getEvent().getId());
    }
}
