package com.freelanceplatform.DAL.Repository;

import com.freelanceplatform.DAL.Entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job, Long> {
}
