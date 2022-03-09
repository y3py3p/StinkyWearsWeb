package com.SafeWebDev.attempt.Models;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserHolder {

    private Map<Long, User> usuarios = new ConcurrentHashMap<Long,User>();
    private User usuarioActual = new User("Usuario temporal","deez");

    public User getUsuarioActual() {
        return usuarioActual;
    }
    public void setCurrentUser(User user){
        this.usuarioActual=user;
    }
}
