package com.SafeWebDev.attempt.Models.Respositories;

import com.SafeWebDev.attempt.Models.Entities.Item;
import com.SafeWebDev.attempt.Models.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /*@Query("select cart from user")
    List<Item> getCart();*/
}
