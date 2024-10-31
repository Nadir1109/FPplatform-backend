package com.freelanceplatform.service;

import com.freelanceplatform.DAL.Entity.Job;
import com.freelanceplatform.DAL.Interface.IJobDAL;
import com.freelanceplatform.DTO.CreateJobDTO;
import com.freelanceplatform.Service.Implementation.JobServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

///Dit is een mock van de JobDAL-interface die we kunnen gebruiken om de JobServiceImpl te testen
class SimpleJobDAL implements IJobDAL {
    private Job savedJob;

    @Override
    public Job save(Job job) {
        this.savedJob = job; // Sla het job-object op in de instantie
        return job;
    }

    @Override
    public List<Job> findAll() { return null; }

    @Override
    public Optional<Job> findById(Long id) { return Optional.empty(); }

    @Override
    public boolean existsById(Long id) { return false; }

    @Override
    public void deleteById(Long id) {}

    public Job getSavedJob() {
        return savedJob;
    }
}
////einde van de mock van de jobDAL



public class JobServiceImplTest {
    public static void main(String[] args) {

        SimpleJobDAL jobDAL = new SimpleJobDAL();
        JobServiceImpl jobService = new JobServiceImpl(jobDAL);

        CreateJobDTO createJobDTO = new CreateJobDTO();
        createJobDTO.setTitle("Test Job");
        createJobDTO.setBudget(1000);
        createJobDTO.setDeadline(LocalDate.now());
        createJobDTO.setDescription("Test Description");

        // Act - Roep de methode aan die we willen testen
        Job createdJob = jobService.createJob(createJobDTO);

        // Assert - Controleer of de resultaten zijn zoals verwacht
        if (createdJob != null
                && "Test Job".equals(createdJob.getTitle())
                && createdJob.getBudget() == 1000
                && "Test Description".equals(createdJob.getDescription())) {
            System.out.println("Test passed!");
        } else {
            System.out.println("Test failed.");
        }

        // Controleer ook of het job-object echt werd opgeslagen in de SimpleJobDAL
        Job savedJob = jobDAL.getSavedJob();
        if (savedJob != null && savedJob.getTitle().equals("Test Job")) {
            System.out.println("Job was saved correctly in DAL.");
        } else {
            System.out.println("Job was not saved correctly in DAL.");
        }
    }
}
