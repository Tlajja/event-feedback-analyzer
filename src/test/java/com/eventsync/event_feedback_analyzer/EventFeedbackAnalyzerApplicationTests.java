package com.eventsync.event_feedback_analyzer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.eventsync.event_feedback_analyzer.services.SentimentAnalysisService;

@SpringBootTest
class EventFeedbackAnalyzerApplicationTests {

    @MockBean
    private SentimentAnalysisService sentimentAnalysisService;

    @Test
    void contextLoads() {
    }

}
