package com.freelanceplatform.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JobCreatedEvent {

    private Long jobId;
    private String jobTitle;

    @Override
    public String toString() {
        return "JobCreatedEvent{jobId=" + jobId + ", jobTitle='" + jobTitle + "'}";
    }
}
