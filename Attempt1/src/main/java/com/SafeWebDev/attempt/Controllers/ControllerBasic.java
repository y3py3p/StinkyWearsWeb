package com.SafeWebDev.attempt.Controllers;

import com.SafeWebDev.attempt.Models.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ControllerBasic {

    @GetMapping("")
    public String homePage(Model model){
        model.addAttribute("items",getItems());
        return "StartPage";
    }

    public List<Item> getItems(){
        List<Item> items=new ArrayList<Item>();
        items.add(new Item("Boxers Hombre","XXL","Desgastado, dado de sí y manchado"));
        items.add(new Item("Bragas Mujer","XL","Desgastado, sucio"));
        items.add(new Item("Calcetin Blanco","L","Con agujeros, olor a esmegma"));
        items.add(new Item("Sujetador Mujer","92B","Hecho mierda"));

        return items;
    }
}
