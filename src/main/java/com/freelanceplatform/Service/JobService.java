package com.freelanceplatform.Service;

import com.freelanceplatform.DAL.Entity.Job;
import com.freelanceplatform.DAL.Interface.IJobDAL;
import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.DTO.EditJobDTO;
import com.freelanceplatform.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class JobService {

    private final IJobDAL jobDAL;

    public JobService(IJobDAL jobDAL) {
        this.jobDAL = jobDAL;
    }

    public Job createJob(CreateJobDTO createJob) {
        validateJobData(createJob);

        Job job = new Job();
        job.setTitle(createJob.getTitle());
        job.setBudget(createJob.getBudget());
        job.setDeadline(createJob.getDeadline().atStartOfDay());
        job.setDescription(createJob.getDescription());

        return jobDAL.save(job);
    }

    public List<Job> getAllJobs() {
        return jobDAL.findAll();
    }

    public Job getJobById(Long id) {
        return jobDAL.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
    }

    public Job updateJob(Long jobId, EditJobDTO editJobDTO) {
        Job job = getJobById(jobId);

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
