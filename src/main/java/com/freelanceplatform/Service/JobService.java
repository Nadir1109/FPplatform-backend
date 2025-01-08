package com.freelanceplatform.Service;

import com.freelanceplatform.DAL.Entity.Job;
import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DAL.Interface.IJobDAL;
import com.freelanceplatform.DAL.Mapper.JobMapper;
import com.freelanceplatform.DAL.Mapper.UserMapper;
import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.DTO.EditJobDTO;
import com.freelanceplatform.DTO.JobDTO;
import com.freelanceplatform.DTO.UserDTO;
import com.freelanceplatform.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobService {

    private final IJobDAL jobDAL;
    private final JobMapper jobMapper;
    private final UserMapper userMapper;

    public JobService(IJobDAL jobDAL, JobMapper jobMapper, UserMapper userMapper) {
        this.jobDAL = jobDAL;
        this.jobMapper = jobMapper;
        this.userMapper = userMapper;
    }

    public JobDTO createJob(CreateJobDTO createJobDTO, UserDTO userDTO) {
        validateJobData(createJobDTO);

        User user = userMapper.toEntity(userDTO);
        Job job = jobMapper.toEntity(createJobDTO);
        job.setUser(user);

        Job savedJob = jobDAL.save(job);
        return jobMapper.toDTO(savedJob);
    }

    public List<JobDTO> getAllJobs() {
        return jobDAL.findAll().stream()
                .map(jobMapper::toDTO)
                .collect(Collectors.toList());
    }

    public JobDTO getJobById(Long id) {
        Job job = jobDAL.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));
        return jobMapper.toDTO(job);
    }

    public JobDTO updateJob(Long jobId, EditJobDTO editJobDTO, UserDTO currentUserDTO) {
        Job job = jobDAL.findById(jobId)
                .orElseThrow(() -> new IllegalArgumentException("Job niet gevonden"));

        User currentUser = userMapper.toEntity(currentUserDTO);

        if (!job.getUser().getId().equals(currentUser.getId())) {
            throw new IllegalArgumentException("Je mag alleen je eigen jobs bewerken.");
        }

        jobMapper.updateEntityFromDTO(editJobDTO, job);
        Job updatedJob = jobDAL.save(job);
        return jobMapper.toDTO(updatedJob);
    }

    public void deleteJob(Long jobId) {
        if (!jobDAL.existsById(jobId)) {
            throw new IllegalArgumentException("Job not found.");
        }
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
