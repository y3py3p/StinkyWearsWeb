package com.SafeWebDev.attempt.Models;

public class User {

    private String usuario;
    private String correo;
    private String password;
    private String nombre;
    private boolean premium;
    private String direccion;
    private String datosBancarios;
    private Carrito carrito;
    private long id;
    private static long lastId = 1;

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

    public long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }
}
