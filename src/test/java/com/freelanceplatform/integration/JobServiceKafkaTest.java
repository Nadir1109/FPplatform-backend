package com.freelanceplatform.integration;

import com.freelanceplatform.DTO.JobCreatedEvent;
import com.freelanceplatform.Service.JobEventPublisher;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.Mockito.verify;

public class JobServiceKafkaTest {

    @Test
    public void testPublishJobCreatedEvent() {
        // Arrange: Mock KafkaTemplate
        KafkaTemplate<String, JobCreatedEvent> kafkaTemplate = Mockito.mock(KafkaTemplate.class);
        JobEventPublisher jobEventPublisher = new JobEventPublisher(kafkaTemplate);

        JobCreatedEvent event = new JobCreatedEvent(1L, "Test Job");

        // Act: Publish event
        jobEventPublisher.publishJobCreatedEvent(event);

        // Assert: Verify that KafkaTemplate's send method was called
        verify(kafkaTemplate).send("job_created", event);
    }
}
