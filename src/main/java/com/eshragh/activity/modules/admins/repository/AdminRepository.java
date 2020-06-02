package com.eshragh.activity.modules.admins.repository;

import com.eshragh.activity.modules.admins.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin , Long> {
    Admin findByUsername(String username);
}
