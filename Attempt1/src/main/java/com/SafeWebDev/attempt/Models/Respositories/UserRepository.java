package com.SafeWebDev.attempt.Models.Respositories;

import com.SafeWebDev.attempt.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /*@Query("select cart from user")
    List<Item> getCart();*/
}
