package com.freelanceplatform.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EditJobDTO {
    private Long id;

    @NotBlank(message = "Titel mag niet leeg zijn.")
    private String title;

    private Integer budget;

    private LocalDate deadline;

    private String description;
}

