
package com.eventsync.event_feedback_analyzer.controllers;

import com.eventsync.event_feedback_analyzer.dtos.FeedbackRequest;
import com.eventsync.event_feedback_analyzer.dtos.FeedbackResponse;
import com.eventsync.event_feedback_analyzer.enums.Sentiment;
import com.eventsync.event_feedback_analyzer.services.FeedbackService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


import org.springframework.test.context.bean.override.mockito.MockitoBean;


import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FeedbackController.class)
class FeedbackControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FeedbackService feedbackService;
    

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void submitFeedback_returnsCreated() throws Exception {
        long eventId = 1L;

        FeedbackRequest request = new FeedbackRequest("This was great!");

        FeedbackResponse response = new FeedbackResponse(
                10L,
                "This was great!",
                Sentiment.POSITIVE,
                0.9,
                0.05,
                0.05,
                LocalDateTime.now(),
                eventId
        );

        when(feedbackService.submitFeedback(eq(eventId), any(FeedbackRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/events/{eventId}/feedback", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10L))
                .andExpect(jsonPath("$.content").value("This was great!"))
                .andExpect(jsonPath("$.sentiment").value("POSITIVE"))
                .andExpect(jsonPath("$.eventId").value((int) eventId));

        verify(feedbackService).submitFeedback(eq(eventId), any(FeedbackRequest.class));
    }

    @Test
    void getEventFeedback_returnsList() throws Exception {
        long eventId = 2L;

        FeedbackResponse f1 = new FeedbackResponse(
                1L,
                "Good",
                Sentiment.POSITIVE,
                0.8,
                0.1,
                0.1,
                LocalDateTime.now(),
                eventId
        );

        FeedbackResponse f2 = new FeedbackResponse(
                2L,
                "Bad",
                Sentiment.NEGATIVE,
                0.1,
                0.1,
                0.8,
                LocalDateTime.now(),
                eventId
        );

        when(feedbackService.getFeedbacksById(eventId))
                .thenReturn(List.of(f1, f2));

        mockMvc.perform(get("/events/{eventId}/feedback", eventId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[1].id").value(2L));

        verify(feedbackService).getFeedbacksById(eventId);
    }

    @Test
    void submitFeedback_withEmptyContent_returnsBadRequest() throws Exception {
        long eventId = 3L;

        // content is blank -> violates @NotBlank on FeedbackRequest.content
        FeedbackRequest invalidRequest = new FeedbackRequest("");
        

        mockMvc.perform(post("/events/{eventId}/feedback", eventId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Validation Failed"));

        verifyNoInteractions(feedbackService);
    }
}
