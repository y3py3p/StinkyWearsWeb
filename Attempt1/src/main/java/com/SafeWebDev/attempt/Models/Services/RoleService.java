package com.SafeWebDev.attempt.Models.Services;

import com.SafeWebDev.attempt.Models.Respositories.RolRepository;
import com.SafeWebDev.attempt.Models.Role;
import com.SafeWebDev.attempt.Models.RoleName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Role> getByRoleName(RoleName roleName){
        return rolRepository.findByRoleName(roleName);
    }

    public void save(Role role){
        rolRepository.save(role);
    }
}
