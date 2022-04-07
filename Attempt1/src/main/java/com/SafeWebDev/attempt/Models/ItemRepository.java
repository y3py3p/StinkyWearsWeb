package com.SafeWebDev.attempt.Models;

import com.SafeWebDev.attempt.Models.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findById(long id);


}
