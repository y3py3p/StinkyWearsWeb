package com.SafeWebDev.attempt.Models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data

public class Item{
    private long id;
    private String name;
    private String size;
    private String state;
    private float price;
    private int stock=0;

    public Item(String name, String size, String state,float price) {
        this.name = name;
        this.size = size;
        this.state = state;
        this.price=price;
    }

    public Item(){

    }

    public void setId(long id){
        this.id=id;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getsize() {
        return size;
    }

    public void setsize(String size) {
        this.size = size;
    }

    public String getstate() {
        return state;
    }

    public float getprice() {
        return price;
    }

    public void setprice(float price) {
        this.price = price;
    }

    public void setstate(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", state='" + state + '\'' +
                ", stock='" + stock + '\'' +
                '}';
    }
}
