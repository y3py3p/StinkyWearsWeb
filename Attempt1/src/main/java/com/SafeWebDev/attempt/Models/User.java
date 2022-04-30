package com.SafeWebDev.attempt.Models;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "usertable")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userID;

    @Column
    private String userName;
    private String email;
    private String userPass;
    private String personalName;
    private String address;

    @ManyToMany
    private List<Cupon> cupones=new ArrayList<Cupon>();

    @OneToMany
    private List<Item> cart = new ArrayList<Item>();

    @Enumerated(EnumType.STRING)
    private RoleName role;


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

    public RoleName getRole() {
        return role;
    }

    public String getPersonalName() {
        return personalName;
    }

    public String getEmail(){
        return email;
    }

    /*public String getUserPass() {
        return userPass;
    }*/

    public String getUserName(){
        return this.userName;
    }

    public String getUserPass(){
        return this.userPass;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public void addRole(RoleName role){
        this.role = role;
    }


}
