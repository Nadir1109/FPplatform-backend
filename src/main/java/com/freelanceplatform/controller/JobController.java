package com.freelanceplatform.controller;

import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.DTO.EditJobDTO;
import com.freelanceplatform.DAL.Entity.Job;
import com.freelanceplatform.DTO.JobDTO;
import com.freelanceplatform.DTO.UserRole;
import com.freelanceplatform.Service.JobService;
import com.freelanceplatform.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;
    private final UserService userService;

    public JobController(JobService jobService, UserService userService) {
        this.jobService = jobService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody CreateJobDTO createJobDTO, Authentication authentication) {
        // Haal de ingelogde gebruiker op
        String username = authentication.getName();
        User users = userService.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Controleer of de gebruiker een CLIENT is
        if (!users.getRole().equals(UserRole.CLIENT)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        // Maak de job aan en koppel deze aan de gebruiker
        Job createdJob = jobService.createJob(createJobDTO, users);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
    }


    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        List<JobDTO> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(
            @PathVariable Long id,
            @RequestBody EditJobDTO editJobDTO,
            Authentication authentication) {

        // Haal de ingelogde gebruiker op
        String username = authentication.getName();
        User user = userService.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("Gebruiker niet gevonden"));

        // Roep de service aan om de job te updaten
        try {
            Job updatedJob = jobService.updateJob(id, editJobDTO, user);
            return ResponseEntity.ok(updatedJob);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobService.deleteJob(id);
        return ResponseEntity.noContent().build();
    }
}
