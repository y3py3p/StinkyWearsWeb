package com.SafeWebDev.attempt.Models.Services;

import com.SafeWebDev.attempt.Models.Item;
import com.SafeWebDev.attempt.Models.User;
import com.SafeWebDev.attempt.Models.Respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class UserService {

    @Autowired
    UserRepository userRepository;

    public void saveUser(User user){
        userRepository.save(user);
    }

    public User findByName(String name,String pass){
        return userRepository.findByName(name,pass);
    }

    /*public boolean logedIn(){

        if(currentUser != null){
            return true;
        }else{
            return false;
        }
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<Item> getCart(){
        return currentUser.getCart();
    }

    public void addCart(Item item){
        currentUser.getCart().add(item);
    }

    public void deleteCart(Item item){
        currentUser.getCart().remove(item);
    }
public List<Item> getCart(User user){
        userRepository.
    }*/
}
