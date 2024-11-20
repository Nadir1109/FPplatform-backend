package com.freelanceplatform.Service;

import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.DTO.EditJobDTO;
import com.freelanceplatform.DAL.Entity.Job;
import com.freelanceplatform.DAL.Interface.IJobDAL;
import com.freelanceplatform.DTO.UserRole;
import com.freelanceplatform.exceptions.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class JobService {
    private final IJobDAL jobDAL;


    public JobService(IJobDAL jobDAL) {
        this.jobDAL = jobDAL;

    }

    public Job createJob(CreateJobDTO createJobDTO, User owner) {

        if (createJobDTO.getTitle() == null || createJobDTO.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (createJobDTO.getBudget() <= 0) {
            throw new IllegalArgumentException("Budget must be positive");
        }
        if (createJobDTO.getDeadline().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Deadline must be a future date");
        }

        Job job = new Job();
        job.setTitle(createJobDTO.getTitle());
        job.setBudget(createJobDTO.getBudget());
        job.setDeadline(createJobDTO.getDeadline().atStartOfDay());
        job.setDescription(createJobDTO.getDescription());
        job.setOwner(owner);
        return jobDAL.save(job);
    }

    public List<Job> getAllJobs() {
        return jobDAL.findAll();
    }

    public Job getJobById(Long id) {
        return jobDAL.findById(id).orElse(null);
    }

    public Job updateJob(Long jobId, EditJobDTO editJobDTO, User currentUser) throws AccessDeniedException {
        Job job = jobDAL.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        // Controleer of de ingelogde gebruiker de eigenaar is
        if (!job.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not authorized to edit this job");
        }

        job.setTitle(editJobDTO.getTitle());
        job.setDescription(editJobDTO.getDescription());
        job.setBudget(editJobDTO.getBudget());
        job.setDeadline(editJobDTO.getDeadline().atStartOfDay());

        return jobDAL.save(job);
    }

    public void deleteJob(Long jobId, User currentUser) throws AccessDeniedException {
        Job job = jobDAL.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found"));

        // Controleer of de ingelogde gebruiker de eigenaar is
        if (!job.getOwner().getId().equals(currentUser.getId())) {
            throw new AccessDeniedException("You are not authorized to delete this job");
        }

        jobDAL.deleteById(jobId);
    }



}
