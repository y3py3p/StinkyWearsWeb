package com.SafeWebDev.attempt.Models.Respositories;

import com.SafeWebDev.attempt.Models.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

        Item findById(long id);
}
