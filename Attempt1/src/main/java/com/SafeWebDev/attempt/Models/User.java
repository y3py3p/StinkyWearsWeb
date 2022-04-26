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

    @ManyToMany
    private List<Cupon> cupones=new ArrayList<Cupon>();

    @OneToMany
    private List<Item> cart = new ArrayList<Item>();




    public User(String userName, String correo, String userPass, String address/*, String personalName*/) {
        this.userName = userName;
        this.email = correo;
        this.userPass = userPass;
        this.address = address;
        //this.personalName = personalName;
    }

    public User(String userName){
        this.userName = userName;
    }

    public User(){

    }

    public void setUserName(String user){
        this.userName=user;
    }

    public void setUserPass(String pass){
        this.userPass=pass;
    }

    public void setAddress(String address){
        this.address=address;
    }

    public void setEmail(String email){
        this.email=email;
    }

    public void addCupon(Cupon cupon){
        this.cupones.add(cupon);
    }

    public List<Cupon> getCupones(){
        return cupones;
    }

    public void emptyCart(){
        this.cart.clear();
    }


    public List<Item> getCart() {
        return cart;
    }

    public float getPrice(){
        float precio=0;
        for(Item item:cart){
            precio+=item.getprice();
        }
        return precio;
    }

    public float priceCupon(Cupon cupon){

        float descuento = cupon.getDescuento();

        float precio = getPrice();

        return precio - ((descuento/100) * precio);
    }

    public boolean sameCupon(Cupon cupon){

        if (cupones.contains(cupon)){
            return true;
        }else {
            return false;
        }
    }

    public boolean sameUser(User userName){
        return this.userName.equals(userName.userName);
    }

    public boolean samePassword(User user){
        return this.userPass.equals(user.userPass);
    }


    @Override
    public String toString() {
        return "userName=" + userName;
    }

    public void addCart(Item item){
        this.cart.add(item);
    }

    public void delCart(int id){
        this.cart.remove(id);
    }


}
