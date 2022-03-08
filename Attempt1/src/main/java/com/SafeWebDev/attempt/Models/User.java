package com.SafeWebDev.attempt.Models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String usuario;
    private String correo;
    private String password;
    private String nombre;
    private boolean premium;
    private String direccion;
    private String datosBancarios;
    private List<Item> carrito = new ArrayList<>();
    private long id;
    private static long lastId = 1;

    public List<Item> getCarrito() {
        return carrito;
    }

    public User(String usuario, String correo, String password, String direccion, String nombre) {
        this.usuario = usuario;
        this.correo = correo;
        this.password = password;
        this.direccion = direccion;
        this.nombre = nombre;
        this.id = lastId;
        lastId++;
    }

    public User(String usuario, String password){
        this.usuario = usuario;
        this.password = password;
    }
    public User(){

    }

    public boolean sameUser(User user){
        return this.usuario.equals(user.usuario);
    }
    public boolean samePassword(User user){
        return this.password.equals(user.password);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean carritoContains(Item item){
        if(carrito.contains(item)){
            return true;
        }else{
            return false;
        }
    }

    public void addCarrito(Item item){
        carrito.add(item);
    }

    public long getId() {
        return id;
    }
}
