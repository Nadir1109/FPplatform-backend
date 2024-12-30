package com.freelanceplatform.controller;

import com.freelanceplatform.DTO.*;
import com.freelanceplatform.Service.JobService;
import com.freelanceplatform.Service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<JobDTO> createJob(@RequestBody CreateJobDTO createJobDTO, Authentication authentication) {
        String username = authentication.getName(); // Haal de geauthenticeerde gebruiker op
        UserDTO user = userService.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getRole().equals(UserRole.CLIENT.name())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        JobDTO createdJob = jobService.createJob(createJobDTO, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdJob);
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id) {
        return ResponseEntity.ok(jobService.getJobById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDTO> updateJob(@PathVariable Long id, @RequestBody EditJobDTO editJobDTO, Authentication authentication) {
        String username = authentication.getName();
        UserDTO user = userService.findByEmail(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        try {
            JobDTO updatedJob = jobService.updateJob(id, editJobDTO, user);
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
