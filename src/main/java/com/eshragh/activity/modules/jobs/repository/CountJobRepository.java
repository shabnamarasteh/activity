package com.eshragh.activity.modules.jobs.repository;

import com.eshragh.activity.modules.jobs.entity.CountJob;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CountJobRepository extends JpaRepository<CountJob , Long> {
}
