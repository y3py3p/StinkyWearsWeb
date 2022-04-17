package com.SafeWebDev.attempt.Models;

import javax.persistence.*;

@Entity
@Table(name="cupontable")
public class Cupon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long cuponID;

    @Column
    private int descuento;
    public Cupon(){

    }
    public Cupon(int descuento){
        this.descuento=descuento;
    }
    public void setDescuento(int descuento){
        this.descuento=descuento;
    }
}
