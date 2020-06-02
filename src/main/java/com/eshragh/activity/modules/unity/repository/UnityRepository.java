package com.eshragh.activity.modules.unity.repository;

import com.eshragh.activity.modules.unity.entity.Unity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UnityRepository extends JpaRepository<Unity, Long> {

}
