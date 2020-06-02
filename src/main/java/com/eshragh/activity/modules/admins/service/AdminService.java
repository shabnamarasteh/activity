package com.eshragh.activity.modules.admins.service;

import com.eshragh.activity.MyBeanCopy;
import com.eshragh.activity.modules.admins.entity.Admin;
import com.eshragh.activity.modules.admins.repository.AdminRepository;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

@Service
public class AdminService {
    private AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }
    public List<Admin> findAllAdmin(){
        return this.adminRepository.findAll();
    }

    @Transactional
    public Admin saveAdmin(Admin admin) throws InvocationTargetException, IllegalAccessException {

        if(!admin.getPassword().isEmpty()){
            admin.setPassword(new BCryptPasswordEncoder().encode(admin.getPassword()));
        }

        if(admin.getId() != 0){
            Admin exist = this.adminRepository.getOne(admin.getId());
            MyBeanCopy myBeanCopy = new MyBeanCopy();
            myBeanCopy.copyProperties(exist, admin);

            return this.adminRepository.save(exist);
        }

        return this.adminRepository.save(admin);
    }

    public Admin findOne(long id){
        if(this.adminRepository.existsById(id)){
            return this.adminRepository.getOne(id);
        }
        return new Admin();
    }

    @Transactional
    public void delete(long id) {
        if (this.adminRepository.existsById(id)) {
            Admin admin = this.adminRepository.getOne(id);
            this.adminRepository.delete(admin);
        }
    }

}
