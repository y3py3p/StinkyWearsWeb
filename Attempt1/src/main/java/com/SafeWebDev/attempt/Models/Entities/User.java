package com.SafeWebDev.attempt.Models.Entities;

import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "usertable")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userID;

    @Column
    private String userName;
    private String email;
    private String userPass;
    private String personalName;
    private boolean premium;
    private String address;
    private String bankData;


    /*@ElementCollection
    private List<Long> cart = new ArrayList<>();

    public List<Long> getCart() {
        return cart;
    }

    public void setCart(List<Long> cart) {
        this.cart = cart;
    }*/


    public User(String userName, String correo, String userPass, String address, String personalName) {
        this.userName = userName;
        this.email = correo;
        this.userPass = userPass;
        this.address = address;
        this.personalName = personalName;
    }

    public User(String userName){
        this.userName = userName;
    }

    public User(){

    }

    /*public List<Item> getCart() {
        return cart;
    }*/

    public boolean sameUser(User userName){
        return this.userName.equals(userName.userName);
    }

    public boolean samePassword(User user){
        return this.userPass.equals(user.userPass);
    }

    public String getUser() {
        return userName;
    }

    public void setUser(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return userPass;
    }

    public void setPassword(String userPass) {
        this.userPass = userPass;
    }

    /*public boolean cartContains(Item item){
        if(cart.contains(item)){
            return true;
        }else{
            return false;
        }
    }*/

    /*public void addCart(Item item){
        cart.add(item);
    }

    public void delCart(Item item){
        cart.remove(item);
    }*/

    /*public long getId() {
        return id;
    }

    public void setId(long id){
        this.id=id;
    }*/

}
