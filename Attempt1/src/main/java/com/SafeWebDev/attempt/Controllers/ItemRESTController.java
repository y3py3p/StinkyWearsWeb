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

        if(generalHolder.containsItem(id)){
            return new ResponseEntity<Item>(generalHolder.getItemId(id), HttpStatus.ACCEPTED);
        }else{
            return new ResponseEntity<Item>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addItem")
    public ResponseEntity<Item> add(@RequestBody Item item){

        generalHolder.addItem(item);

        return new ResponseEntity<Item>(item, HttpStatus.CREATED);
    }

    @PostMapping("/editItem/{id}")
    public ResponseEntity<Item> edit(@RequestBody Item item, @PathVariable long id){

        if(!generalHolder.containsItem(id)){
            return new ResponseEntity<Item>((Item) null, HttpStatus.NOT_FOUND);
        }else{
            generalHolder.getItemId(id).editItem(item);
            return new ResponseEntity<Item>(generalHolder.getItemId(id), HttpStatus.CREATED);
        }

    }

    @GetMapping("/addCart/{id}")
    public ResponseEntity<List<Item>>/*ResponseEntity<Map<Long, Item>>*/ addCart(@PathVariable long id){

        if(!generalHolder.logedIn()){
            return new ResponseEntity<List<Item>>((List<Item>) null, HttpStatus.NOT_FOUND);
        }else if(!generalHolder.containsItem(id)){
            return new ResponseEntity<>((List<Item>) null, HttpStatus.NOT_FOUND);
        }else{
            generalHolder.getCurrentUser().addCart(generalHolder.getItemId(id));
            return new ResponseEntity<List<Item>>(generalHolder.getCurrentUser().getCart(), HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/seeCart")
    public List<Item> seeCart(){
        if(!generalHolder.logedIn()){
            return null;
        }else{
            return generalHolder.getCurrentUser().getCart();
        }

    }

    @GetMapping("/removeCart/{id}")
    public ResponseEntity<List<Item>>/*ResponseEntity<Map<Long, Item>>*/ removeCart(@PathVariable int id){

        if(!generalHolder.logedIn()){
            return new ResponseEntity<>((List<Item>) null, HttpStatus.NOT_FOUND);
        }else if(!generalHolder.containsItem(id)){
            return new ResponseEntity<>((List<Item>) null, HttpStatus.NOT_FOUND);
        }else{
            generalHolder.getCurrentUser().delCart(generalHolder.getItemId(id));
            return new ResponseEntity<List<Item>>(generalHolder.getCurrentUser().getCart(), HttpStatus.ACCEPTED);
        }
    }

    @GetMapping("/usr")
    public User seeUser(){

        if(generalHolder.logedIn()){
            return generalHolder.getCurrentUser();
        }else{
            return null;
        }

    }
}