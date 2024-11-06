package com.freelanceplatform.Service;

import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.DTO.EditJobDTO;
import com.freelanceplatform.DAL.Entity.Job;
import com.freelanceplatform.DAL.Interface.IJobDAL;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@SuppressWarnings("ALL")
@Service
public class JobService {
    private final IJobDAL jobDAL;

    public JobService(IJobDAL jobDAL) {
        this.jobDAL = jobDAL;
    }

    public Job createJob(CreateJobDTO createJobDTO) {

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
        return jobDAL.save(job);
    }

    public List<Job> getAllJobs() {
        return jobDAL.findAll();
    }

    public Job getJobById(Long id) {
        return jobDAL.findById(id).orElse(null);
    }

    public Job updateJob(Long id, EditJobDTO editJobDTO) {
        if (jobDAL.existsById(id)) {
            Job job = jobDAL.findById(id).get();
            job.setTitle(editJobDTO.getTitle());
            job.setBudget(editJobDTO.getBudget());
            job.setDeadline(editJobDTO.getDeadline().atStartOfDay());
            job.setDescription(editJobDTO.getDescription());
            return jobDAL.save(job);
        }
        return null;
    }

    public void deleteJob(Long id) {
        jobDAL.deleteById(id);
    }
}
