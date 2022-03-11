package com.SafeWebDev.attempt.Models;

import lombok.Data;

@Data

public class Item{
    private long id;
    private String name;
    private String size;
    private String condition;
    private float price;
    private int stock=0;

    public Item(String name, String size, String state,float price) {
        this.name = name;
        this.size = size;
        this.condition = state;
        this.price=price;
    }

    public Item(){

    }

    public void update(Item item){
        this.name=item.name;
        this.size=item.size;
        this.condition=item.condition;
        this.price=item.price;
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
        return condition;
    }

    public float getprice() {
        return price;
    }

    public void setprice(float price) {
        this.price = price;
    }

    public void setstate(String state) {
        this.condition = state;
    }

    public void editItem(Item item){
        setname(item.name);
        setsize(item.size);
        setCondition(item.condition);
        setprice(item.price);
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", size='" + size + '\'' +
                ", state='" + condition + '\'' +
                ", stock='" + stock + '\'' +
                '}';
    }
}
