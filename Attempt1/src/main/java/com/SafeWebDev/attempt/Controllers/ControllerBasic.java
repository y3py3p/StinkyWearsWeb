package com.SafeWebDev.attempt.Controllers;

import com.SafeWebDev.attempt.Models.*;
import com.SafeWebDev.attempt.Models.Holders.GeneralHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ControllerBasic {

    private GeneralHolder generalHolder=new GeneralHolder();

    public ControllerBasic() {  //initializing the default products
        generalHolder.addItem(new Item("Boxers Hombre", "XXL", "Desgastado, dado de sí y manchado", 10));
        generalHolder.addItem(new Item("Bragas Mujer", "XL", "Desgastado, sucio", 15));
        generalHolder.addItem(new Item("Calcetin Blanco", "L", "Con agujeros, olor a esmegma", 35));
        generalHolder.addItem(new Item("Sujetador Mujer", "92B", "Hecho mierda", 25));
        generalHolder.addUsr(new User("hola","deez"));
        generalHolder.addUsr(new User("Usuario temporal","deez"));
        generalHolder.setCurrentUser(generalHolder.getUsrId(2));
    }


    @GetMapping("")     //it'll redirect you to StartPage.html, the main page
    public String homePage(Model model) {
        if(generalHolder.getCurrentUser() == null){
            model.addAttribute("login", "LogIn");   //login link will show up only if you're not loged in
        }else{
            model.addAttribute("login", "");
        }
        return "StartPage";
    }

    @GetMapping("/item/{id}")   //it'll redirect you to ItemPage.html, where you can see the info of one item
    public String itemPage(Model model, @PathVariable long id) {
        model.addAttribute("item", generalHolder.getItemId(id));
        return "ItemPage";
    }

    @GetMapping("/usr") //it'll redirect you to UsrPage.html with your usr info (right now just the admin user)
    public String usrPage(Model model) {
        model.addAttribute("user", generalHolder.getCurrentUser());
        return "UsrPage";
    }

    @PostMapping("/item/new")   //it'll redirect you to ItemAdded.html after adding an item to our general List
    public String addItem(Model model,Item item){
        generalHolder.addItem(item);
        return "ItemAdded";
    }
    @GetMapping("/items")   //it'll redirect you to ItemsList.html, where you can see every product aviable
    public String listaItems(Model model){
        model.addAttribute("items", generalHolder.getItems().values());
        return "ItemsList";
    }

    @GetMapping("/cart")    //it'll redirect you to Cart.html, with your cart info
    public String carrito(Model model){
        model.addAttribute("cart", generalHolder.getCurrentUser().getCart());
        return "Cart";
    }

    @GetMapping("/cart/{id}")   //it'll redirect you to CartAdded.html or CartAlreadyContains.html
    public String addCarrito(Model model, @PathVariable long id){
        if(!generalHolder.getCurrentUser().carritoContains(generalHolder.getItemId(id))){
            generalHolder.getCurrentUser().addCarrito(generalHolder.getItemId(id));
            return "CartAdded"; //you added the item to your cart
        }else{
            return "CartAlreadyContains";   //you already have the item in your cart
        }
    }


    @GetMapping("/login")   //it'll redirect you to LogIn.html, where you'll be able to log in
    public String logIn(){
        return "LogIn";
    }

    @GetMapping("/createAccount")   //it'll redirect you to CreateAccount.html, to sign up
    public String createAccount(){
        return "CreateAccount";

    }
}
