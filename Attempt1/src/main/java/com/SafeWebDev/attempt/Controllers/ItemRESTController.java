package com.SafeWebDev.attempt.Controllers;


import com.SafeWebDev.attempt.Models.*;
import com.SafeWebDev.attempt.Models.Holders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ItemRESTController {

    @Autowired
    private GeneralHolder generalHolder=new GeneralHolder();


    @GetMapping("/api/see")
    public Map<Long, Item> getItems(){

        return generalHolder.getItems();
    }

    @GetMapping("/api/see/{id}")
    public ResponseEntity<Item> getById(@PathVariable long id){

        Item item = generalHolder.getItemId(id);
        if(item != null){
            return new ResponseEntity<Item>(item,HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/api/addItem")
    public ResponseEntity<Item> add(@RequestBody Item item){

        generalHolder.addItem(item);

        return new ResponseEntity<Item>(item, HttpStatus.CREATED);
    }

    @PostMapping("/api/addCarrito/{id}")
    public ResponseEntity<Item> addCarrito(@PathVariable long id){

        User user = generalHolder.getCurrentUser();
        user.addCart(generalHolder.getItemId(id));
        return new ResponseEntity<Item>(generalHolder.getItemId(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/seeCarrito")
    public List<Item> seeCarrito(){
        return generalHolder.getCurrentUser().getCart();
    }

    @GetMapping("/api/usr")
    public User seeUser(){

        return generalHolder.getCurrentUser();
    }
}
