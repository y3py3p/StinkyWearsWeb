package com.SafeWebDev.attempt.Models;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserHolder {

    private Map<Long, User> usuarios = new ConcurrentHashMap<>();
    private List<Item> carrito = new ArrayList<>();
    private User usuarioActual = new User("Usuario temporal","deez");

    public List<Item> getCarrito(){
        return carrito;
    }

    public User getUsuarioActual() {
        return usuarioActual;
    }

    public void setCarrito(Item item){
        carrito.add(item);
    }
}
