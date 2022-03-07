package com.SafeWebDev.attempt.Models;

import java.util.*;

public class Carrito {
    private List<Item> items=new ArrayList<Item>();

    public void addItem(Item item){
        items.add(item);
    }
}
