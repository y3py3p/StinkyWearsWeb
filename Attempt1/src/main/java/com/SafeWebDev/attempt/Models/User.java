package com.SafeWebDev.attempt.Models;

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

    @OneToMany
    private List<Item> cart = new ArrayList<Item>();




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

    public List<Item> getCart() {
        return cart;
    }

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

    public void addCart(Item item){
        this.cart.add(item);
    }

    public void delCart(int id){
        this.cart.remove(id);
    }

    /*public long getId() {
        return id;
    }

    public void setId(long id){
        this.id=id;
    }*/

}
