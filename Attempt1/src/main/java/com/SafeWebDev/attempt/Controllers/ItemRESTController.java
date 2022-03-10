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
@RequestMapping("/api")
public class ItemRESTController {

    @Autowired
    private GeneralHolder generalHolder=new GeneralHolder();


    @GetMapping("/see")
    public Map<Long, Item> getItems(){

        return generalHolder.getItems();
    }

    @GetMapping("/see/{id}")
    public ResponseEntity<Item> getById(@PathVariable long id){

        Item item = generalHolder.getItemId(id);
        if(item != null){
            return new ResponseEntity<Item>(item,HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addItem")
    public ResponseEntity<Item> add(@RequestBody Item item){

        generalHolder.addItem(item);

        return new ResponseEntity<Item>(item, HttpStatus.CREATED);
    }

    @GetMapping("/addCart/{id}")
    public ResponseEntity<List<Item>> addCart(@PathVariable long id){

        User user = generalHolder.getCurrentUser();
        user.addCart(generalHolder.getItemId(id));
        return new ResponseEntity<List<Item>>(generalHolder.getCurrentUser().getCart(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/seeCart")
    public List<Item> seeCart(){
        return generalHolder.getCurrentUser().getCart();
    }

    @GetMapping("/removeCart/{id}")
    public ResponseEntity<List<Item>> removeCart(@PathVariable int id){

        generalHolder.getCurrentUser().delCart(id);
        return new ResponseEntity<List<Item>>(generalHolder.getCurrentUser().getCart(), HttpStatus.ACCEPTED);
    }

    @GetMapping("/usr")
    public User seeUser(){

        return generalHolder.getCurrentUser();
    }
}