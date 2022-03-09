package com.SafeWebDev.attempt.Models;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserHolder {

    private Map<Long, User> usuarios = new ConcurrentHashMap<Long,User>();
    private AtomicLong lastId=new AtomicLong();
    private User usuarioActual = new User("Usuario temporal","deez");

    public void addUser(User user){
        long id = lastId.incrementAndGet();
        user.setId(id);
        usuarios.put(id, user);
    }
    public User getUsuarioActual() {
        return usuarioActual;
    }
    public void setCurrentUser(User user){
        this.usuarioActual=user;
    }
    public User getUsrbyId(long id){
        return usuarios.get(id);
    }
}
