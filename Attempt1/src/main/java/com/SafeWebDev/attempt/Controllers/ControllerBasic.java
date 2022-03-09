package com.SafeWebDev.attempt.Controllers;

import com.SafeWebDev.attempt.Models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.GenericArrayType;
import java.util.*;

@Controller
public class ControllerBasic {

    private GeneralHolder generalHolder=new GeneralHolder();

    public ControllerBasic() {
        generalHolder.addItem(new Item("Boxers Hombre", "XXL", "Desgastado, dado de sí y manchado", 10));
        generalHolder.addItem(new Item("Bragas Mujer", "XL", "Desgastado, sucio", 15));
        generalHolder.addItem(new Item("Calcetin Blanco", "L", "Con agujeros, olor a esmegma", 35));
        generalHolder.addItem(new Item("Sujetador Mujer", "92B", "Hecho mierda", 25));
        generalHolder.addUsr(new User("No estas logueado, inicia sesión o registrate","neutro"));
        generalHolder.addUsr(new User("Usuario temporal","deez"));
        generalHolder.setCurrentUser(generalHolder.getUsrId(1));
    }


    @GetMapping("")
    public String homePage(Model model) {
        if(generalHolder.getCurrentUser() == null){
            model.addAttribute("login", "LogIn");
        }else{
            model.addAttribute("login", "");
        }
        return "StartPage";
    }

    @GetMapping("/item/{id}")
    public String itemPage(Model model, @PathVariable long id) {
        model.addAttribute("item", generalHolder.getItemId(id));
        return "ItemPage";
    }

    @GetMapping("/usr")
    public String usrPage(Model model) {
        if(generalHolder.getCurrentUser() == null){
            model.addAttribute("user", generalHolder.getUsrId(0));
            model.addAttribute("login", "LogIn");
        }else{
            model.addAttribute("login", "");
            model.addAttribute("user",generalHolder.getUsrId(1));
        }
        return "UsrPage";
    }

    @PostMapping("/item/new")
    public String addItem(Model model,Item item){
        generalHolder.addItem(item);
        return "ItemAdded";
    }
    @GetMapping("/items")
    public String listaItems(Model model){
        model.addAttribute("items", generalHolder.getItems().values());
        return "ListaItems";
    }

    @GetMapping("/cart")
    public String carrito(Model model){
        model.addAttribute("carrito", generalHolder.getCurrentUser().getCarrito());
        return "Carrito";
    }

    @GetMapping("/cart/{id}")
    public String addCarrito(Model model, @PathVariable long id){
        if(!generalHolder.getCurrentUser().carritoContains(generalHolder.getItemId(id))){
            generalHolder.getCurrentUser().addCarrito(generalHolder.getItemId(id));
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
