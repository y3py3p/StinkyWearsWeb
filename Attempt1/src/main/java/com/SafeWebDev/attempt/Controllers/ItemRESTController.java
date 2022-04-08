package com.SafeWebDev.attempt.Controllers;


import com.SafeWebDev.attempt.Models.ItemRepository;
import com.SafeWebDev.attempt.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.sql.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemRESTController {


//    private GeneralHolder generalHolder=new GeneralHolder();
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/see") //to see every item on stock
    public /*Map<Long, Item>*/List<Item> getItems(){

        //return generalHolder.getItems();
        return itemRepository.findAll();
    }

    @GetMapping("/see/{id}")    //see a specified item with id
    public ResponseEntity<Item> getById(@PathVariable long id){

        /*if(generalHolder.containsItem(id)){ //check if item exists
            return new ResponseEntity<Item>(generalHolder.getItemId(id), HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
        }*/
        return new ResponseEntity<Item>(itemRepository.findById(id), HttpStatus.ACCEPTED);
    }

    @PostMapping("/addItem")    //add item to stock
    public ResponseEntity<Item> add(@RequestBody Item item){

        //generalHolder.addItem(item);
        itemRepository.save(item);


        return new ResponseEntity<Item>(item, HttpStatus.CREATED);
    }

    @PostMapping("/editItem/{id}")  //edit item info
    public ResponseEntity<Item> edit(@RequestBody Item item, @PathVariable long id){

        /*if(!generalHolder.containsItem(id)){    //check if item exists
            return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
        }else{
            generalHolder.getItemId(id).editItem(item);
            return new ResponseEntity<Item>(generalHolder.getItemId(id), HttpStatus.CREATED);
        }*/

        itemRepository.findById(id).update(item);

        return new ResponseEntity<>(itemRepository.findById(id), HttpStatus.CREATED);

    }

    @PostMapping("/newUser")
    public ResponseEntity<User> newUser(@RequestBody User user){

        userRepository.save(user);

        return new ResponseEntity<>(userRepository.getById(user.getUser()), HttpStatus.CREATED);
    }

    /*@GetMapping("/addCart/{id}")    //add item to cart
    public ResponseEntity<List<Item>> addCart(@PathVariable long id){

        *//*if(!generalHolder.logedIn()){   //check if you're loged in
            return new ResponseEntity<List<Item>>((List<Item>) null, HttpStatus.NOT_FOUND);
        }else if(!generalHolder.containsItem(id)){  //check if item exists
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else if(generalHolder.getCurrentUser().cartContains(generalHolder.getItemId(id))){   //check if it's already in cart
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{  //adds item to cart
            generalHolder.getCurrentUser().addCart(generalHolder.getItemId(id));
            return new ResponseEntity<List<Item>>(generalHolder.getCurrentUser().getCart(), HttpStatus.ACCEPTED);
        }*//*
    }

    @GetMapping("/seeCart") //see the cart
    public List<Item> seeCart(){
        if(!generalHolder.logedIn()){   //check if you're loged in
            return null;
        }else{
            return generalHolder.getCurrentUser().getCart();
        }

    }

    @GetMapping("/removeCart/{id}") //remove item from cart
    public ResponseEntity<List<Item>> removeCart(@PathVariable int id){

        if(!generalHolder.logedIn()){   //check if you're loged in
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else if(!generalHolder.containsItem(id)){  //check if item exists
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else{
            generalHolder.getCurrentUser().delCart(generalHolder.getItemId(id));
            return new ResponseEntity<List<Item>>(generalHolder.getCurrentUser().getCart(), HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/usr") //see your user
    public User seeUser(){

        if(generalHolder.logedIn()){    //check if you're loged in
            return generalHolder.getCurrentUser();
        }else{
            return null;
        }

    }*/
}