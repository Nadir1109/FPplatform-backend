package com.freelanceplatform.service;

import com.freelanceplatform.DAL.Entity.Job;
import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DAL.Interface.IJobDAL;
import com.freelanceplatform.DAL.Mapper.JobMapper;
import com.freelanceplatform.DAL.Mapper.UserMapper;
import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.DTO.JobDTO;
import com.freelanceplatform.DTO.UserDTO;
import com.freelanceplatform.DTO.UserRole;
import com.freelanceplatform.Service.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class JobServiceTest {

    @Mock
    private IJobDAL jobDAL;
    @Mock
    private JobMapper jobMapper;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private JobService jobService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateJob_HappyFlow() {
        // Arrange
        CreateJobDTO createJobDTO = new CreateJobDTO();
        createJobDTO.setTitle("Test Job");
        createJobDTO.setBudget(1500);
        createJobDTO.setDeadline(LocalDate.now().plusDays(30));
        createJobDTO.setDescription("Test Description");

        UserDTO owner = new UserDTO(1L, "Owner Name", "owner@example.com", "password", UserRole.CLIENT.name());
        User userEntity = new User();
        userEntity.setId(1L);
        userEntity.setName("Owner Name");
        userEntity.setEmail("owner@example.com");
        userEntity.setPassword("password");
        userEntity.setRole(UserRole.CLIENT);

        Job jobEntity = new Job();
        jobEntity.setTitle("Test Job");
        jobEntity.setBudget(1500);
        jobEntity.setDeadline(LocalDate.from(LocalDate.now().plusDays(30).atStartOfDay()));
        jobEntity.setDescription("Test Description");
        jobEntity.setUser(userEntity);

        Job savedJob = new Job();
        savedJob.setId(1L);
        savedJob.setTitle("Test Job");
        savedJob.setDescription("Test Description");
        savedJob.setDeadline(LocalDate.from(LocalDate.now().plusDays(30).atStartOfDay()));
        savedJob.setBudget(1500);
        savedJob.setUser(userEntity);

        JobDTO expectedJobDTO = new JobDTO();
        expectedJobDTO.setId(1L);
        expectedJobDTO.setTitle("Test Job");
        expectedJobDTO.setBudget(1500);
        expectedJobDTO.setDeadline(LocalDate.now().plusDays(30));
        expectedJobDTO.setDescription("Test Description");
        expectedJobDTO.setUserName("Owner Name");
        expectedJobDTO.setUserEmail("owner@example.com");

        // Mock behavior
        when(userMapper.toEntity(owner)).thenReturn(userEntity);
        when(jobMapper.toEntity(createJobDTO)).thenReturn(jobEntity);
        when(jobDAL.save(any(Job.class))).thenReturn(savedJob);
        when(jobMapper.toDTO(savedJob)).thenReturn(expectedJobDTO);

        // Act
        JobDTO result = jobService.createJob(createJobDTO, owner);

        // Assert
        assertNotNull(result, "Result should not be null");
        assertEquals(expectedJobDTO.getTitle(), result.getTitle(), "Job title should match");
        assertEquals(expectedJobDTO.getBudget(), result.getBudget(), "Job budget should match");
        assertEquals(expectedJobDTO.getDescription(), result.getDescription(), "Job description should match");
        assertEquals(expectedJobDTO.getUserName(), result.getUserName(), "Job owner name should match");
        assertEquals(expectedJobDTO.getUserEmail(), result.getUserEmail(), "Job owner email should match");

        // Verify interactions
        verify(userMapper).toEntity(owner);
        verify(jobMapper).toEntity(createJobDTO);
        verify(jobDAL).save(jobEntity);
        verify(jobMapper).toDTO(savedJob);
    }

    @Test
    public void testCreateJob_MissingTitle() {
        // Arrange
        CreateJobDTO createJobDTO = new CreateJobDTO();
        createJobDTO.setTitle(null); // Missing title
        createJobDTO.setBudget(1500);
        createJobDTO.setDeadline(LocalDate.now().plusDays(30));
        createJobDTO.setDescription("Test Description");

        UserDTO owner = new UserDTO(1L, "Owner Name", "owner@example.com", "password", UserRole.CLIENT.name());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> jobService.createJob(createJobDTO, owner));
        assertEquals("Title is required", exception.getMessage(), "Expected error for missing title");

        // Verify no interaction with jobDAL
        verify(jobDAL, never()).save(any(Job.class));
    }

    @Test
    public void testCreateJob_PastDeadline() {
        // Arrange
        CreateJobDTO createJobDTO = new CreateJobDTO();
        createJobDTO.setTitle("Test Job");
        createJobDTO.setBudget(1500);
        createJobDTO.setDeadline(LocalDate.now().minusDays(1)); // Past deadline
        createJobDTO.setDescription("Test Description");

        UserDTO owner = new UserDTO(1L, "Owner Name", "owner@example.com", "password", UserRole.CLIENT.name());

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> jobService.createJob(createJobDTO, owner));
        assertEquals("Deadline must be a future date", exception.getMessage(), "Expected error for past deadline");

        // Verify no interaction with jobDAL
        verify(jobDAL, never()).save(any(Job.class));
    }


}
