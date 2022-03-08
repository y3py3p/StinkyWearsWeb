package com.SafeWebDev.attempt.Controllers;


import com.SafeWebDev.attempt.Models.Item;
import com.SafeWebDev.attempt.Models.ItemHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

public class ItemRESTController {

    @Autowired
    ItemHolder itemHolder;

    @GetMapping("/see/items")
    public Map<Long, Item> getItems(){
        return itemHolder.getItems();
    }

    @GetMapping("/see/items/{id}")
    public ResponseEntity<Item> getById(@PathVariable(value = "id") long id){

        Item item = (Item) itemHolder.getById(id);
        if(item != null){
            return ResponseEntity.ok().body(item);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/addItem")
    public Item add(@Validated @RequestBody Item item){
        return itemHolder.addItem(item);
    }
}
