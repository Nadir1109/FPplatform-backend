package com.freelanceplatform.Service.Implementation;

import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.DTO.EditJobDTO;
import com.freelanceplatform.Service.Interface.IJobService;
import com.freelanceplatform.DAL.Entity.Job;
import com.freelanceplatform.DAL.Repository.JobRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobServiceImpl implements IJobService {
    private final JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    @Override
    public Job createJob(CreateJobDTO createJobDTO) {
        Job job = new Job();
        job.setTitle(createJobDTO.getTitle());
        job.setBudget(createJobDTO.getBudget());
        job.setDeadline(createJobDTO.getDeadline().atStartOfDay());
        job.setDescription(createJobDTO.getDescription());
        return jobRepository.save(job);
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }
    @Override
    public Job getJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }
    @Override
    public Job updateJob(Long id, EditJobDTO editJobDTO) {
        if (jobRepository.existsById(id)) {
            Job job = jobRepository.findById(id).orElse(null);
            if (job != null) {
                job.setTitle(editJobDTO.getTitle());
                job.setBudget(editJobDTO.getBudget());
                job.setDeadline(editJobDTO.getDeadline().atStartOfDay()); // Pas conversie aan indien nodig
                job.setDescription(editJobDTO.getDescription());
                return jobRepository.save(job);
            }
        }
        return null;
    }
    @Override
    public void deleteJob(Long id) {
        jobRepository.deleteById(id);
    }
}
