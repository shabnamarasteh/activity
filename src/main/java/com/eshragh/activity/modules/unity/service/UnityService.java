package com.eshragh.activity.modules.unity.service;

import com.eshragh.activity.modules.unity.entity.Unity;
import com.eshragh.activity.modules.jobs.repository.CountJobRepository;
import com.eshragh.activity.modules.unity.repository.UnityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UnityService {
    private UnityRepository unityRepository;
    private CountJobRepository countJobRepository;

    @Autowired
    public UnityService(UnityRepository unityRepository,CountJobRepository countJobRepository) {
        this.unityRepository = unityRepository;
        this.countJobRepository = countJobRepository;
    }

    @Transactional
    public Unity saveUnity(Unity unity){
        return this.unityRepository.save(unity);
    }

    public List<Unity> findAllUnity(){
        return this.unityRepository.findAll();
    }

    public Unity findOne(Long id){
        if(this.unityRepository.existsById(id)){
            return this.unityRepository.getOne(id);
        }else{
            return new Unity();
        }
    }

    public void delete(long id) {
        if(this.unityRepository.existsById(id)){
            Unity unity = this.unityRepository.getOne(id);
            this.unityRepository.delete(unity);
        }
    }

}
