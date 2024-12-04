package com.freelanceplatform.DAL.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class JobResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job; // De job waarop wordt gereageerd

    @ManyToOne
    @JoinColumn(name = "freelancer_id", nullable = false)
    private User freelancer; // Freelancer die reageert

    private String message; // Optioneel bericht
    private Date responseDate;
}
