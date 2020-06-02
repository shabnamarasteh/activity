package com.eshragh.activity.modules.jobs.entity;

import com.eshragh.activity.modules.jobs.entity.Job;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "countjob")
@Immutable
public class CountJob {

    private long firstlevel;
    private long secondlevel;
    private long lastlevel;
    @Id
    private long unity_id;
    private String title;
    public CountJob() {
    }

    public long getFirstlevel() {
        return firstlevel;
    }

    public void setFirstlevel(long firstlevel) {
        this.firstlevel = firstlevel;
    }

    public long getSecondlevel() {
        return secondlevel;
    }

    public void setSecondlevel(long secondlevel) {
        this.secondlevel = secondlevel;
    }

    public long getLastlevel() {
        return lastlevel;
    }

    public void setLastlevel(long lastlevel) {
        this.lastlevel = lastlevel;
    }

    public long getUnity_id() {
        return unity_id;
    }

    public void setUnity_id(long unity_id) {
        this.unity_id = unity_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
