package com.SafeWebDev.attempt.Models;

import java.util.concurrent.atomic.AtomicLong;

public class Item{
    private long id;
    private String nombre;
    private String talla;
    private String estado;
    private float precio;
    private int stock=0;
    private static long lastId=1;

    public Item(String nombre, String talla, String estado,float precio) {
        this.nombre = nombre;
        this.talla = talla;
        this.estado = estado;
        this.precio=precio;
        id=lastId;
        lastId++;
    }
    public Item(){

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTalla() {
        return talla;
    }

    public void setTalla(String talla) {
        this.talla = talla;
    }

    public String getEstado() {
        return estado;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", talla='" + talla + '\'' +
                ", estado='" + estado + '\'' +
                ", stock='" + stock + '\'' +
                '}';
    }
}
