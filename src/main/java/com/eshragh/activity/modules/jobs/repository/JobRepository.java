package com.eshragh.activity.modules.jobs.repository;

import com.eshragh.activity.modules.jobs.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job , Long> {
}
