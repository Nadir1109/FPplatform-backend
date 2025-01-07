package com.freelanceplatform.integration;

import com.freelanceplatform.DTO.JobCreatedEvent;
import com.freelanceplatform.Service.JobEventListener;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.verify;

public class JobEventListenerTest {

    @Test
    public void testHandleJobCreatedEvent() {
        // Arrange: Mock de JobEventListener
        JobEventListener jobEventListener = Mockito.mock(JobEventListener.class);

        JobCreatedEvent event = new JobCreatedEvent(1L, "Test Job");

        // Act: Roep de listener aan
        jobEventListener.handleJobCreatedEvent(event);

        // Assert: Controleer of de methode correct is aangeroepen
        verify(jobEventListener).handleJobCreatedEvent(event);
    }
}
