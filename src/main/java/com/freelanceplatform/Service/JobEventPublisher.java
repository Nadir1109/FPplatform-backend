package com.freelanceplatform.Service;

import com.freelanceplatform.DTO.JobCreatedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class JobEventPublisher {

    private final KafkaTemplate<String, JobCreatedEvent> kafkaTemplate;

    @Autowired
    public JobEventPublisher(KafkaTemplate<String, JobCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void publishJobCreatedEvent(JobCreatedEvent event) {
        if (event == null || event.getJobId() == null || event.getJobTitle() == null) {
            throw new IllegalArgumentException("Invalid JobCreatedEvent");
        }
        try {
            kafkaTemplate.send("job_created", event).get();
            System.out.println("Event succesvol gepubliceerd: " + event);
        } catch (Exception e) {
            System.err.println("Fout bij het publiceren van het event: " + e.getMessage());
        }
    }
}
