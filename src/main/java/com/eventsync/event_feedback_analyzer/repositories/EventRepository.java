package com.eventsync.event_feedback_analyzer.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.eventsync.event_feedback_analyzer.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
