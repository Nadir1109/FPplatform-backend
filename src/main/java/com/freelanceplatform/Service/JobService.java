package com.freelanceplatform.Service;

import com.freelanceplatform.DAL.Entity.Job;
import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DAL.Interface.IJobDAL;
import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.DTO.EditJobDTO;
import com.freelanceplatform.DTO.JobDTO;
import com.freelanceplatform.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final IJobDAL jobDAL;

    public JobService(IJobDAL jobDAL) {
        this.jobDAL = jobDAL;
    }

    public Job createJob(CreateJobDTO createJob, User user) {
        validateJobData(createJob);

        Job job = new Job();
        job.setTitle(createJob.getTitle());
        job.setBudget(createJob.getBudget());
        job.setDeadline(createJob.getDeadline().atStartOfDay());
        job.setDescription(createJob.getDescription());
        job.setUser(user);

        return jobDAL.save(job);
    }

    public List<JobDTO> getAllJobs() {
        List<Job> jobs = jobDAL.findAll(); // Haal alle jobs op

        return jobs.stream().map(job -> {
            JobDTO jobDTO = new JobDTO();
            jobDTO.setId(job.getId());
            jobDTO.setTitle(job.getTitle());
            jobDTO.setDescription(job.getDescription());
            jobDTO.setBudget(job.getBudget());
            jobDTO.setDeadline(job.getDeadline().toLocalDate());
            jobDTO.setUserName(job.getUser().getName()); // Naam van de eigenaar
            jobDTO.setUserEmail(job.getUser().getEmail()); // Email van de eigenaar
            return jobDTO;
        }).collect(Collectors.toList());
    }

    public Job getJobById(Long id) {
        return jobDAL.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
    }

    public Job updateJob(Long jobId, EditJobDTO editJobDTO, User currentUser) {
        Job job = jobDAL.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job niet gevonden"));

        // Controleer of de huidige gebruiker de eigenaar van de job is
        if (!job.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("Je mag alleen je eigen jobs bewerken.");
        }

        // Update de job
        job.setTitle(editJobDTO.getTitle());
        job.setDescription(editJobDTO.getDescription());
        job.setBudget(editJobDTO.getBudget());
        job.setDeadline(editJobDTO.getDeadline().atStartOfDay());

        return jobDAL.save(job);
    }



    public void deleteJob(Long jobId) {
        Job job = getJobById(jobId);
        jobDAL.deleteById(jobId);
    }

    private void validateJobData(CreateJobDTO createJob) {
        if (createJob.getTitle() == null || createJob.getTitle().isEmpty()) {
            throw new IllegalArgumentException("Title is required");
        }
        if (createJob.getBudget() == null || createJob.getBudget() <= 0) {
            throw new IllegalArgumentException("Budget must be positive");
        }
        if (createJob.getDeadline() == null || createJob.getDeadline().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Deadline must be a future date");
        }
    }
}
