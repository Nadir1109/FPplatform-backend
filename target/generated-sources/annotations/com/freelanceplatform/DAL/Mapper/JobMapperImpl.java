package com.freelanceplatform.DAL.Mapper;

import com.freelanceplatform.DAL.Entity.Job;
import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.DTO.EditJobDTO;
import com.freelanceplatform.DTO.JobDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-08T19:29:22+0100",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 23 (Oracle Corporation)"
)
@Component
public class JobMapperImpl implements JobMapper {

    @Override
    public JobDTO toDTO(Job job) {
        if ( job == null ) {
            return null;
        }

        JobDTO jobDTO = new JobDTO();

        jobDTO.setUserName( jobUserName( job ) );
        jobDTO.setUserEmail( jobUserEmail( job ) );
        jobDTO.setId( job.getId() );
        jobDTO.setTitle( job.getTitle() );
        jobDTO.setDescription( job.getDescription() );
        jobDTO.setBudget( job.getBudget() );
        jobDTO.setDeadline( job.getDeadline() );

        return jobDTO;
    }

    @Override
    public Job toEntity(CreateJobDTO createJobDTO) {
        if ( createJobDTO == null ) {
            return null;
        }

        Job job = new Job();

        job.setTitle( createJobDTO.getTitle() );
        job.setDescription( createJobDTO.getDescription() );
        job.setBudget( createJobDTO.getBudget() );
        job.setDeadline( createJobDTO.getDeadline() );

        return job;
    }

    @Override
    public void updateEntityFromDTO(EditJobDTO editJobDTO, Job job) {
        if ( editJobDTO == null ) {
            return;
        }

        job.setTitle( editJobDTO.getTitle() );
        job.setDescription( editJobDTO.getDescription() );
        job.setBudget( editJobDTO.getBudget() );
        job.setDeadline( editJobDTO.getDeadline() );
    }

    private String jobUserName(Job job) {
        if ( job == null ) {
            return null;
        }
        User user = job.getUser();
        if ( user == null ) {
            return null;
        }
        String name = user.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String jobUserEmail(Job job) {
        if ( job == null ) {
            return null;
        }
        User user = job.getUser();
        if ( user == null ) {
            return null;
        }
        String email = user.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }
}
