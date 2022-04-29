package com.SafeWebDev.attempt.Models;

import com.sun.istack.NotNull;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated
    private RoleName roleName;

    public Role(){

    }

    public void setUser(){
        this.roleName = RoleName.USER;
    }

    public Role(@NotNull RoleName roleName){
        this.roleName = roleName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public int getId() {
        return id;
    }

    public RoleName getRoleName() {
        return roleName;
    }
}
