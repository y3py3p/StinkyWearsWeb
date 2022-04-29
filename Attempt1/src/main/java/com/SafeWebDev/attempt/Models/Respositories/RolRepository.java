package com.SafeWebDev.attempt.Models.Respositories;

import com.SafeWebDev.attempt.Models.Role;
import com.SafeWebDev.attempt.Models.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByRoleName(RoleName roleName);
}
