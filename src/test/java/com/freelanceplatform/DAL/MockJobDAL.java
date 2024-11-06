package com.freelanceplatform.DAL;

import com.freelanceplatform.DAL.Entity.Job;
import com.freelanceplatform.DAL.Interface.IJobDAL;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("ALL")
public class MockJobDAL implements IJobDAL {
    private Job savedJob;

    @Override
    public Job save(Job job) {
        this.savedJob = job;
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
