package com.SafeWebDev.attempt.Models;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String user;
    private String email;
    private String password;
    private String name;
    private boolean premium;
    private String address;
    private String bankData;
    private List<Item> cart = new ArrayList<>();
    private long id;

    public List<Item> getCart() {
        return cart;
    }

    public User(String user, String correo, String password, String address, String name) {
        this.user = user;
        this.email = correo;
        this.password = password;
        this.address = address;
        this.name = name;
    }

    public User(String user, String password){
        this.user = user;
        this.password = password;
    }
    public User(){

    }

    public boolean sameUser(User user){
        return this.user.equals(user.user);
    }

    public boolean samePassword(User user){
        return this.password.equals(user.password);
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean carritoContains(Item item){
        if(cart.contains(item)){
            return true;
        }else{
            return false;
        }
    }

    public void addCart(Item item){
        cart.add(item);
    }

    public void delCart(int id){
        cart.remove(id-1);
    }

    public long getId() {
        return id;
    }

    public void setId(long id){
        this.id=id;
    }

}
