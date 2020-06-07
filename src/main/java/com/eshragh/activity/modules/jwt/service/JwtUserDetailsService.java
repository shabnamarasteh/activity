package com.eshragh.activity.modules.jwt.service;

import com.eshragh.activity.modules.admins.entity.Admin;
import com.eshragh.activity.modules.admins.repository.AdminRepository;
import com.eshragh.activity.modules.jwt.entity.AdminPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

@Service
public class JwtUserDetailsService  implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);
        if (admin == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
//        return new org.springframework.security.core.userdetails.User(admin.getUsername(), admin.getPassword(),
//                new ArrayList<>());

        return AdminPrincipal.create(admin);
    }

//    public AdminRepository save(AdminDTO user) {
//        Admin newUser = new Admin();
//        newUser.setUsername(user.getUsername());
//        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
//        return (AdminRepository) adminRepository.save(newUser);
//    }
    // This method is used by JWTAuthenticationFilter
    @Transactional
    public UserDetails loadUserById(Long id) {
        Admin admin = adminRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id : " + id)
        );

        return AdminPrincipal.create(admin);
    }

    public boolean existsByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin == null) {
            return false;
        }else{
            return true;
        }
    }

    public Admin save(Admin admin) {
        return adminRepository.save(admin);

    }
}
