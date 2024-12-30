package com.freelanceplatform.DAL.Mapper;

import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.DTO.EditJobDTO;
import com.freelanceplatform.DTO.JobDTO;
import com.freelanceplatform.DAL.Entity.Job;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface JobMapper {

    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "userEmail", source = "user.email")
    JobDTO toDTO(Job job);

    Job toEntity(CreateJobDTO createJobDTO);

    @Mapping(target = "id", ignore = true) // ID wordt niet overschreven
    @Mapping(target = "user", ignore = true) // User wordt niet geüpdatet hier
    void updateEntityFromDTO(EditJobDTO editJobDTO, @MappingTarget Job job);
}
