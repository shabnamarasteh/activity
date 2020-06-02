package com.eshragh.activity.modules.jobs.service;

import com.eshragh.activity.modules.jobs.entity.CountJob;
import com.eshragh.activity.modules.jobs.entity.Job;
import com.eshragh.activity.modules.jobs.repository.CountJobRepository;
import com.eshragh.activity.modules.jobs.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class JobService {
    private JobRepository jobRepository;
    private CountJobRepository countJobRepository;

    @Autowired
    public JobService(JobRepository jobRepository,CountJobRepository countJobRepository) {
        this.jobRepository = jobRepository;
        this.countJobRepository = countJobRepository;
    }

    @Transactional
    public Job saveJob(Job job) {
        return this.jobRepository.save(job);
    }

    public List<Job> findAllJob(){
        return this.jobRepository.findAll();
    }
    public Job findOne(long id){
        if(this.jobRepository.existsById(id)){
            return this.jobRepository.getOne(id);
        }
        return new Job();
    }

    @Transactional
    public void delete(long id) {
        if(this.jobRepository.existsById(id)){
            Job job = this.jobRepository.getOne(id);
            this.jobRepository.delete(job);
        }
    }

    public List<CountJob> findCountJob() {
        System.out.println(this.countJobRepository.findAll());
        return this.countJobRepository.findAll();

    }
}
