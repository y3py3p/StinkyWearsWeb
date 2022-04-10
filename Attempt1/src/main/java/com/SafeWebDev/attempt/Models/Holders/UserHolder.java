package com.SafeWebDev.attempt.Models.Holders;

import com.SafeWebDev.attempt.Models.Entities.User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class UserHolder {

    private Map<Long, User> users = new ConcurrentHashMap<Long,User>();
    private AtomicLong lastId=new AtomicLong();
    private User currentUser ;

    /*public void addUser(User user){
        long id = lastId.incrementAndGet();
        user.setId(id);
        users.put(id, user);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user){
        currentUser =user;
    }

    public User getUsrbyId(long id){
        return users.get(id);
    }*/


}
