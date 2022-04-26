package com.SafeWebDev.attempt.Models.Respositories;

import com.SafeWebDev.attempt.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.userName = ?1 and u.userPass = ?2")
    public User findByName(String name,String pass);
}
