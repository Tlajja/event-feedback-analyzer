package com.eventsync.event_feedback_analyzer.controllers;

import com.eventsync.event_feedback_analyzer.dtos.CreateEventRequest;
import com.eventsync.event_feedback_analyzer.dtos.EventResponse;
import com.eventsync.event_feedback_analyzer.services.EventService;

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

@WebMvcTest(EventController.class)
class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventService eventService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void getAllEvents_returnsList() throws Exception {
        EventResponse resp = new EventResponse(
                1L,
                "Test Event",
                "Desc",
                LocalDateTime.now(),
                0);

        when(eventService.getAllEvents()).thenReturn(List.of(resp));

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test Event"));
    }

    @Test
    void createEvent_returnsCreated() throws Exception {
        CreateEventRequest request = new CreateEventRequest("New Event", "Some description");

        EventResponse response = new EventResponse(
                5L,
                "New Event",
                "Some description",
                LocalDateTime.now(),
                0);

        when(eventService.createEvent(any(CreateEventRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(5L))
                .andExpect(jsonPath("$.name").value("New Event"));

        verify(eventService).createEvent(any(CreateEventRequest.class));
    }
}
