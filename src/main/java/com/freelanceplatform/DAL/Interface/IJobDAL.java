package com.freelanceplatform.DAL.Interface;
import com.freelanceplatform.DAL.Entity.Job;
import java.util.List;
import java.util.Optional;

public interface IJobDAL {
    Job save(Job job);
    List<Job> findAll();
    Optional<Job> findById(Long id);
    void deleteById(Long id);
    boolean existsById(Long id);
}
