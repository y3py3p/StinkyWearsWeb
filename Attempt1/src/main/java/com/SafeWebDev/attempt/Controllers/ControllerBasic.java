package com.SafeWebDev.attempt.Controllers;

import com.SafeWebDev.attempt.Models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.*;

@Controller
public class ControllerBasic {

    private List<Item> items=new ArrayList<Item>();
    private List<User> usuarios = new ArrayList<User>();
    User usuarioActual;

    public ControllerBasic() {
        items.add(new Item("Boxers Hombre", "XXL", "Desgastado, dado de sí y manchado", 10));
        items.add(new Item("Bragas Mujer", "XL", "Desgastado, sucio", 15));
        items.add(new Item("Calcetin Blanco", "L", "Con agujeros, olor a esmegma", 35));
        items.add(new Item("Sujetador Mujer", "92B", "Hecho mierda", 25));
        usuarios.add(new User("No estas logueado, inicia sesión o registrate","neutro"));
        usuarios.add(new User("Usuario temporal","deez"));
        //usuarioActual = usuarios.get(1);
    }


    @GetMapping("")
    public String homePage(Model model) {
        if(usuarioActual == null){
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
        //model.addAttribute("user",usuarios.get(0));
        if(usuarioActual == null){
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

    @GetMapping("/carrito")
    public String carrito(Model model){
        model.addAttribute("carrito", usuarioActual.getCarrito());
        return "Carrito";
    }

    @GetMapping("/carrito/{id}")
    public String addCarrito(Model model, @PathVariable int id){
        if(!usuarioActual.carritoContains(items.get(id-1))){
            usuarioActual.addCarrito(items.get(id-1));
            return "CarritoAdded";
        }else{
            return "CarritoAlreadyContains";
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
