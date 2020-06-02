package com.eshragh.activity.modules.jobs.entity;

import com.eshragh.activity.modules.unity.entity.Unity;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.annotation.Generated;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class , property = "id")
public class Job {
    @Id
    @GeneratedValue
    @Column(columnDefinition = "number")
    private long id;

    @Column(columnDefinition = "varchar2(20)")
    private String title;

    @Column(columnDefinition = "number")
    private long state;

    @CreationTimestamp
    @Column(name = "creation_at" , updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "unity_id")
    private Unity unity_id;

    public Job() {
    }

    public Job(String title, long state) {
        this.title = title;
        this.state = state;
    }

    public Job(String title, long state, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.title = title;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
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

    public Unity getUnity_id() {
        return unity_id;
    }

    public void setUnity_id(Unity unity_id) {
        this.unity_id = unity_id;
    }
}
