package com.SafeWebDev.attempt.Controllers;


import com.SafeWebDev.attempt.Models.*;
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
    private ItemHolder itemHolder=new ItemHolder();
    @Autowired
    private UserHolder userHolder = new UserHolder();

    private GeneralHolder generalHolder=new GeneralHolder();


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

    @PostMapping("/api/addCarrito/{id}")
    public ResponseEntity<Item> addCarrito(@PathVariable long id){

        User user = userHolder.getUsuarioActual();
        user.addCarrito(itemHolder.getById(id));
        return /*itemHolder.getById(id)*/new ResponseEntity<Item>(itemHolder.getById(id), HttpStatus.ACCEPTED);
    }

    @GetMapping("/api/seeCarrito")
    public List<Item> seeCarrito(){
        return userHolder.getUsuarioActual().getCarrito();
    }
}
