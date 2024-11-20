package com.freelanceplatform.controller;

import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DTO.EditJobDTO;
import com.freelanceplatform.Service.JobService;
import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.DAL.Entity.Job;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @PostMapping("/jobs")
    public ResponseEntity<Job> createJob(@RequestBody CreateJobDTO createJobDTO, @AuthenticationPrincipal User currentUser) {
        Job createdJob = jobService.createJob(createJobDTO, currentUser);
        return ResponseEntity.ok(createdJob);
    }

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> jobs = jobService.getAllJobs();
        return ResponseEntity.ok(jobs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobById(@PathVariable Long id) {
        Job job = jobService.getJobById(id);
        return ResponseEntity.ok(job);
    }

    @PutMapping("/jobs/{id}")
    public ResponseEntity<Job> updateJob(@PathVariable Long id, @RequestBody EditJobDTO editJobDTO, @AuthenticationPrincipal User currentUser) throws AccessDeniedException {
        Job updatedJob = jobService.updateJob(id, editJobDTO, currentUser);
        return ResponseEntity.ok(updatedJob);
    }
    @DeleteMapping("/jobs/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id, @AuthenticationPrincipal User currentUser) throws AccessDeniedException {
        jobService.deleteJob(id, currentUser);
        return ResponseEntity.noContent().build();
    }
}
