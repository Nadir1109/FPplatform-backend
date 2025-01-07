package com.freelanceplatform.Service;

import com.freelanceplatform.DTO.JobCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class JobEventListener {

    @KafkaListener(topics = "job_created", groupId = "notification-service")
    public void handleJobCreatedEvent(JobCreatedEvent event) {
        if (event == null) {
            System.err.println("Ontvangen een null event");
            return;
        }
        System.out.println("Ontvangen JobCreatedEvent: Job ID " + event.getJobId() + ", Titel: " + event.getJobTitle());
        // Hier kun je logica toevoegen om bijvoorbeeld notificaties te sturen.
    }
}
