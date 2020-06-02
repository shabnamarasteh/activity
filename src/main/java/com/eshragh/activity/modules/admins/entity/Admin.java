package com.eshragh.activity.modules.admins.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "admins")
public class Admin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "number")
    private long id;

    @Column(columnDefinition = "varchar2(100)", name = "email")
    private String username;

    @Column(columnDefinition = "varchar2(100)")
    private String firstname;

    @Column(columnDefinition = "varchar2(100)")
    private String lastname;

    @Column(columnDefinition = "varchar2(200)")
    private String password;

    @Column(columnDefinition = "varchar2(5)")
    private String role = "Admin";

    private Boolean enabled = true;

    @Column(columnDefinition = "number")
    private long nationalcode;

    @CreationTimestamp
    @Column(name = "creation_at" , updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Admin() {
    }

    public Admin(String username, String firstname, String lastname, String password, String role, Boolean enabled, long nationalcode) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
        this.nationalcode = nationalcode;
    }

    public Admin(String username, String firstname, String lastname, String password, String role, Boolean enabled, long nationalcode, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
        this.nationalcode = nationalcode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public <E> Admin(String javainuse, String $2a$10$slYQmyNdGzTn7ZLBXBChFOC9f6kFjAqPhccnP6DxlWXx2lPk1C3G6, ArrayList<E> es) {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public long getNationalcode() {
        return nationalcode;
    }

    public void setNationalcode(long nationalcode) {
        this.nationalcode = nationalcode;
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
}
