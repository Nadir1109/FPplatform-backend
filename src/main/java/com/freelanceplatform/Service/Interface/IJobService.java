package com.freelanceplatform.Service.Interface;

import com.freelanceplatform.DAL.Entity.Job;
import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.DTO.EditJobDTO;

import java.util.List;

public interface IJobService {
    Job createJob(CreateJobDTO createJobDTO);
    Job updateJob(Long id, EditJobDTO editJobDTO);
    List<Job> getAllJobs();
    Job getJobById(Long id);
    void deleteJob(Long id);
}

