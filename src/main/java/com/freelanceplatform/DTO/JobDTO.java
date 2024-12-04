package com.freelanceplatform.DTO;

import lombok.Data;

import java.time.LocalDate;

@Data
public class JobDTO {
    private Long id; // Job ID
    private String title;
    private String description;
    private Integer budget;
    private LocalDate deadline;
    private String userName; // Naam van de eigenaar
    private String userEmail; // Email van de eigenaar
}
