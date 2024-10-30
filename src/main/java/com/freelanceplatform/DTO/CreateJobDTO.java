package com.freelanceplatform.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateJobDTO {
    @NotBlank(message = "Titel mag niet leeg zijn.")
    private String title;

    @NotNull(message = "Budget mag niet leeg zijn.")
    private Integer budget;

    @NotNull(message = "Deadline mag niet leeg zijn.")
    private LocalDate deadline;

    @NotBlank(message = "Beschrijving mag niet leeg zijn.")
    private String description;
}
