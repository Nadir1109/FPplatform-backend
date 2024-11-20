package com.freelanceplatform.service;

import com.freelanceplatform.DAL.Entity.Job;
import com.freelanceplatform.DAL.Entity.User;
import com.freelanceplatform.DAL.MockJobDAL;
import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.DTO.UserRole;
import com.freelanceplatform.Service.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings("ALL")
public class JobServiceTest {

    private JobService jobService;
    private MockJobDAL jobDAL;
    private User owner;


    @BeforeEach
    public void setUp() {
        jobDAL = new MockJobDAL();

        jobService = new JobService(jobDAL);
        owner = new User(1L, "Test Owner", "owner@example.com", "password", UserRole.CLIENT); // Mock User instance
    }

    @Test
    public void testCreateJob_HappyFlow() {
        // Arrange
        CreateJobDTO createJobDTO = new CreateJobDTO();
        createJobDTO.setTitle("Happy Job");
        createJobDTO.setBudget(1000);
        createJobDTO.setDeadline(LocalDate.now().plusDays(30));
        createJobDTO.setDescription("A standard job description");

        // Act
        Job createdJob = jobService.createJob(createJobDTO, owner);

        // Assert
        assertNotNull(createdJob, "Job should not be null");
        assertEquals("Happy Job", createdJob.getTitle());
        assertEquals(1000, createdJob.getBudget(), "Job budget should be 1000");
        assertEquals("A standard job description", createdJob.getDescription(), "Job description should match");
        assertEquals(owner, createdJob.getOwner(), "Job owner should match the provided user");

        Job savedJob = jobDAL.getSavedJob();
        assertNotNull(savedJob, "Saved job should not be null");
        assertEquals("Happy Job", savedJob.getTitle(), "Saved job title should match 'Happy Job'");
    }

    @Test
    public void testCreateJob_UnhappyFlow_MissingTitle() {
        // Arrange
        CreateJobDTO createJobDTO = new CreateJobDTO();
        createJobDTO.setTitle(null);  // Missing title
        createJobDTO.setBudget(1000);
        createJobDTO.setDeadline(LocalDate.now().plusDays(30));
        createJobDTO.setDescription("Missing title");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> jobService.createJob(createJobDTO, owner));
        assertEquals("Title is required", exception.getMessage(), "Expected error for missing title");
    }

    @Test
    public void testCreateJob_UnhappyFlow_NegativeBudget() {
        // Arrange
        CreateJobDTO createJobDTO = new CreateJobDTO();
        createJobDTO.setTitle("Job with Negative Budget");
        createJobDTO.setBudget(-100);  // Invalid budget
        createJobDTO.setDeadline(LocalDate.now().plusDays(30));
        createJobDTO.setDescription("Negative budget");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> jobService.createJob(createJobDTO, owner));
        assertEquals("Budget must be positive", exception.getMessage(), "Expected error for negative budget");
    }

    @Test
    public void testCreateJob_EdgeCase_HighBudget() {
        // Arrange
        CreateJobDTO createJobDTO = new CreateJobDTO();
        createJobDTO.setTitle("High Budget Job");
        createJobDTO.setBudget(Integer.MAX_VALUE);  // Extremely high budget
        createJobDTO.setDeadline(LocalDate.now().plusDays(30));
        createJobDTO.setDescription("Edge case with maximum budget");

        // Act
        Job createdJob = jobService.createJob(createJobDTO, owner);

        // Assert
        assertNotNull(createdJob, "Job should not be null");
        assertEquals(Integer.MAX_VALUE, createdJob.getBudget(), "Job budget should be maximum integer value");
        assertEquals(owner, createdJob.getOwner(), "Job owner should match the provided user");
    }

    @Test
    public void testCreateJob_EdgeCase_PastDeadline() {
        // Arrange
        CreateJobDTO createJobDTO = new CreateJobDTO();
        createJobDTO.setTitle("Past Deadline Job");
        createJobDTO.setBudget(500);
        createJobDTO.setDeadline(LocalDate.now().minusDays(1));  // Past date
        createJobDTO.setDescription("Edge case with a past deadline");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> jobService.createJob(createJobDTO, owner));
        assertEquals("Deadline must be a future date", exception.getMessage(), "Expected error for past deadline");
    }
}
