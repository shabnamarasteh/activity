package com.eshragh.activity.modules.unity.entity;

import com.eshragh.activity.modules.jobs.entity.Job;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "id")

public class Unity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "number")
    private long id;

    @Column(columnDefinition = "varchar2(20)")
    private String title;

    @CreationTimestamp
    @Column(name = "creation_at" , updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "unity_id" )
    private List<Job> jobList;

    public Unity() {
    }

    public Unity(String title, LocalDateTime createdAt, LocalDateTime updatedAt, List<Job> jobList) {
        this.title = title;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.jobList = jobList;
    }

    public Unity(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Job> getJobList() {
        return jobList;
    }

    public void setJobList(List<Job> jobList) {
        this.jobList = jobList;
    }
}
