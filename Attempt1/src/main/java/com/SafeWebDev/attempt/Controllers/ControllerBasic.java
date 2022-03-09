package com.SafeWebDev.attempt.Controllers;

import com.SafeWebDev.attempt.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class ControllerBasic {

    private List<Item> items=new ArrayList<Item>();
    private List<User> usuarios = new ArrayList<User>();
    @Autowired
    ItemHolder itemHolder=new ItemHolder();
    @Autowired
    UserHolder userHolder=new UserHolder();

    public ControllerBasic() {
        items.add(new Item("Boxers Hombre", "XXL", "Desgastado, dado de sí y manchado", 10));
        items.add(new Item("Bragas Mujer", "XL", "Desgastado, sucio", 15));
        items.add(new Item("Calcetin Blanco", "L", "Con agujeros, olor a esmegma", 35));
        items.add(new Item("Sujetador Mujer", "92B", "Hecho mierda", 25));
        usuarios.add(new User("No estas logueado, inicia sesión o registrate","neutro"));
        usuarios.add(new User("Usuario temporal","deez"));
        userHolder.setCurrentUser(usuarios.get(1));
    }


    @GetMapping("")
    public String homePage(Model model) {
        if(userHolder.getUsuarioActual() == null){
            model.addAttribute("login", "LogIn");
        }else{
            model.addAttribute("login", "");
        }
        return "StartPage";
    }

    @GetMapping("/item/{id}")
    public String itemPage(Model model, @PathVariable int id) {
        model.addAttribute("item", items.get(id - 1));
        return "ItemPage";
    }

    @GetMapping("/usr")
    public String usrPage(Model model) {
        if(userHolder.getUsuarioActual() == null){
            model.addAttribute("user", usuarios.get(0));
            model.addAttribute("login", "LogIn");
        }else{
            model.addAttribute("login", "");
            model.addAttribute("user", usuarios.get(1));
        }
        return "UsrPage";
    }

    @PostMapping("/item/new")
    public String addItem(Model model,Item item){
        items.add(item);
        return "ItemAdded";
    }
    @GetMapping("/items")
    public String listaItems(Model model){
        model.addAttribute("items", items);
        return "ListaItems";
    }

    @GetMapping("/cart")
    public String carrito(Model model){
        model.addAttribute("carrito", userHolder.getUsuarioActual().getCarrito());
        return "Carrito";
    }

    @GetMapping("/cart/{id}")
    public String addCarrito(Model model, @PathVariable int id){
        if(!userHolder.getUsuarioActual().carritoContains(items.get(id-1))){
            userHolder.getUsuarioActual().addCarrito(items.get(id-1));
            return "CartAdded";
        }else{
            return "CartAlreadyContains";
        }
    }


    @GetMapping("/login")
    public String logIn(){
        return "LogIn";
    }

    @GetMapping("/createAccount")
    public String createAccount(){
        return "CreateAccount";

    }
}
