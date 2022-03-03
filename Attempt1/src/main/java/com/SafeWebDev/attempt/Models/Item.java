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
