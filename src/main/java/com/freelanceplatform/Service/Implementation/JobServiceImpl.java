package com.freelanceplatform.Service.Implementation;

import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.DTO.EditJobDTO;
import com.freelanceplatform.DAL.Entity.Job;
import com.freelanceplatform.DAL.Interface.IJobDAL;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class JobServiceImpl {
    private final IJobDAL jobDAL;

    public JobServiceImpl(IJobDAL jobDAL) {
        this.jobDAL = jobDAL;
    }

    public Job createJob(CreateJobDTO createJobDTO) {
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
