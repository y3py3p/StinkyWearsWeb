package com.SafeWebDev.attempt.Controllers;


import com.SafeWebDev.attempt.Models.Item;
import com.SafeWebDev.attempt.Models.ItemHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ItemRESTController {

    @Autowired
    ItemHolder itemHolder;

    @GetMapping("/api/see")
    public Map<Long, Item> getItems(){

        return itemHolder.getItems();
    }

    @GetMapping("/api/see/{id}")
    public ResponseEntity<Item> getById(@PathVariable long id){

        Item item = itemHolder.getById(id);
        if(item != null){
            return ResponseEntity.ok().body(item);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/addItem")
    public ResponseEntity<Item> add(@RequestBody Item item){

        itemHolder.addItem(item);

        return new ResponseEntity<Item>(item, HttpStatus.CREATED);
    }
}
