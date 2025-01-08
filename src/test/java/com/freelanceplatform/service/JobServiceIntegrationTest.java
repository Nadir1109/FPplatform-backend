package com.freelanceplatform.service;

import com.freelanceplatform.DAL.Entity.Job;
import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DAL.Interface.IJobDAL;
import com.freelanceplatform.DAL.Repository.JobRepository;
import com.freelanceplatform.DAL.Repository.UserRepository;
import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.DTO.JobDTO;
import com.freelanceplatform.DTO.UserDTO;
import com.freelanceplatform.DTO.UserRole;
import com.freelanceplatform.Service.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class JobServiceIntegrationTest {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JobService jobService;

    private UserDTO owner;

    @BeforeEach
    public void setUp() {
        // Clear repositories before each test
        jobRepository.deleteAll();
        userRepository.deleteAll();

        // Create and save a user in the database
        User user = new User();
        user.setName("Owner Name");
        user.setEmail("owner@example.com");
        user.setPassword("password");
        user.setRole(UserRole.CLIENT);

        userRepository.save(user);

        // Map the User entity to a UserDTO for the test
        owner = new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPassword(), user.getRole().name());
    }

    @Test
    public void testCreateJob_HappyFlow() {
        // Arrange
        CreateJobDTO createJobDTO = new CreateJobDTO();
        createJobDTO.setTitle("Integration Test Job");
        createJobDTO.setBudget(2000);
        createJobDTO.setDeadline(LocalDate.now().plusDays(15));
        createJobDTO.setDescription("Testing the integration of service and database layers");

        // Act
        JobDTO createdJobDTO = jobService.createJob(createJobDTO, owner);

        // Assert
        assertNotNull(createdJobDTO, "The created job should not be null");
        assertEquals("Integration Test Job", createdJobDTO.getTitle(), "The job title should match");
        assertEquals(2000, createdJobDTO.getBudget(), "The job budget should match");
        assertEquals("Testing the integration of service and database layers", createdJobDTO.getDescription(), "The job description should match");

        // Verify that the job exists in the database
        Job savedJob = jobRepository.findById(createdJobDTO.getId()).orElse(null);
        assertNotNull(savedJob, "The job should be saved in the database");
        assertEquals("Integration Test Job", savedJob.getTitle(), "The saved job title should match");
        assertEquals(2000, savedJob.getBudget(), "The saved job budget should match");
    }
}
