package com.eventsync.event_feedback_analyzer.repositories;

import com.eventsync.event_feedback_analyzer.models.Feedback;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

    // Find all feedback by event ID
    List<Feedback> findByEventId(Long eventId);
}
