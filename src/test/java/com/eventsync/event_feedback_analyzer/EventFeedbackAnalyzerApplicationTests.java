package com.eventsync.event_feedback_analyzer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.eventsync.event_feedback_analyzer.services.SentimentAnalysisService;

@SpringBootTest
class EventFeedbackAnalyzerApplicationTests {

    @MockitoBean
    private SentimentAnalysisService sentimentAnalysisService;

    @Test
    void contextLoads() {
    }

}
