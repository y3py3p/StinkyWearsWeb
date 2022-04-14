package com.SafeWebDev.attempt.Controllers;


import com.SafeWebDev.attempt.Models.Entities.Item;
import com.SafeWebDev.attempt.Models.Entities.User;
import com.SafeWebDev.attempt.Models.Respositories.ItemRepository;
import com.SafeWebDev.attempt.Models.Respositories.UserRepository;
import com.SafeWebDev.attempt.Models.Services.ItemService;
import com.SafeWebDev.attempt.Models.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ItemRESTController {


    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;

    @PostConstruct
    public void init(){
        userService.setCurrentUser(new User("Default"));
    }

    @GetMapping("/see") //to see every item on stock
    public List<Item> getItems(){

        return itemService.getAll();
    }

    @GetMapping("/see/{id}")    //see a specified item with id
    public ResponseEntity<Item> getById(@PathVariable long id){

        if(itemService.exists(id)){
            return new ResponseEntity<>(itemService.findById(id), HttpStatus.ACCEPTED);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping("/del/{id}")
    public ResponseEntity<Item> deleteItem(@PathVariable long id){
        itemService.delete(itemService.findById(id));
        return new ResponseEntity<>(itemService.findById(id),HttpStatus.ACCEPTED);
    }

    @PostMapping("/addItem")    //add item to stock
    public ResponseEntity<Item> add(@RequestBody Item item){

        itemService.add(item);

        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PostMapping("/editItem/{id}")  //edit item info
    public ResponseEntity<Item> edit(@RequestBody Item item, @PathVariable long id){

        if(itemService.exists(id)){
            itemService.updateItem(id, item);
            return new ResponseEntity<>(itemService.findById(id), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @PostMapping("/newUser")
    public ResponseEntity<User> newUser(@RequestBody User user){

        userService.saveUser(user);

        userService.setCurrentUser(user);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/addCart/{id}")    //add item to cart
    public ResponseEntity<List<Item>> addCart(@PathVariable long id){

        /*if(!generalHolder.logedIn()){   //check if you're loged in
            return new ResponseEntity<List<Item>>((List<Item>) null, HttpStatus.NOT_FOUND);
        }else if(!generalHolder.containsItem(id)){  //check if item exists
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else if(generalHolder.getCurrentUser().cartContains(generalHolder.getItemId(id))){   //check if it's already in cart
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else{  //adds item to cart
            generalHolder.getCurrentUser().addCart(generalHolder.getItemId(id));
            return new ResponseEntity<List<Item>>(generalHolder.getCurrentUser().getCart(), HttpStatus.ACCEPTED);
        }*/
        if(userService.getCart().contains(itemService.findById(id))){
            return new ResponseEntity<>( HttpStatus.NOT_ACCEPTABLE);
        }else{
            userService.addCart(itemService.findById(id));
            return new ResponseEntity<>(userService.getCart(), HttpStatus.ACCEPTED);
        }

    }

    @GetMapping("/seeCart") //see the cart
    public List<Item> seeCart(){
        /*if(!generalHolder.logedIn()){   //check if you're loged in
            return null;
        }else{
            return generalHolder.getCurrentUser().getCart();
        }*/

        return userService.getCart();

    }

    @GetMapping("/removeCart/{id}") //remove item from cart
    public ResponseEntity<List<Item>> removeCart(@PathVariable int id){
        userService.getCart().remove(itemService.findById(id));
        return new ResponseEntity<>(userService.getCart(),HttpStatus.ACCEPTED);
    }

    @GetMapping("/usr") //see your user
    public User seeUser(){

        if(userService.logedIn()){    //check if you're loged in
            return userService.getCurrentUser();
        }else{
            return null;
        }

    }
}