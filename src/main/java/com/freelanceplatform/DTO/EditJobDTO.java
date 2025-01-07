package com.freelanceplatform.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditJobDTO {
    private Long id;

    @NotBlank(message = "Titel mag niet leeg.")
    private String title;

    private Integer budget;

    private LocalDate deadline;

    private String description;
}

