package com.eventsync.event_feedback_analyzer.services;

import com.eventsync.event_feedback_analyzer.dtos.*;
import com.eventsync.event_feedback_analyzer.models.*;
import com.eventsync.event_feedback_analyzer.repositories.FeedbackRepository;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class FeedbackService {
    private final EventService eventService;
    private final SentimentAnalysisService sentimentAnalysisService;
    private final FeedbackRepository feedbackRepository;

    public FeedbackService(EventService eventService, SentimentAnalysisService sentimentAnalysisService,
            FeedbackRepository feedbackRepository) {
        this.eventService = eventService;
        this.sentimentAnalysisService = sentimentAnalysisService;
        this.feedbackRepository = feedbackRepository;
    }

    public FeedbackResponse submitFeedback(long id, FeedbackRequest request) {
        Event event = eventService.getEventById(id);

        SentimentAnalysisService.SentimentAnalysisResult sentimentResult = sentimentAnalysisService
                .analyzeSentiment(request.getContent());

        Feedback feedback = new Feedback();
        feedback.setContent(request.getContent());
        feedback.setEvent(event);
        feedback.setSentiment(sentimentResult.getSentiment());
        feedback.setPositiveScore(sentimentResult.getPositiveScore());
        feedback.setNeutralScore(sentimentResult.getNeutralScore());
        feedback.setNegativeScore(sentimentResult.getNegativeScore());

        Feedback savedFeedback = feedbackRepository.save(feedback);

        return convertToFeedbackResponse(savedFeedback);
    }

    public List<FeedbackResponse> getFeedbacksById(Long id) {
        List<Feedback> feedback = feedbackRepository.findByEventId(id);
        return feedback
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
