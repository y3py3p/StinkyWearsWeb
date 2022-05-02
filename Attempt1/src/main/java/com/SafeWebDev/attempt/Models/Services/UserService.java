package com.SafeWebDev.attempt.Models.Services;

import com.SafeWebDev.attempt.Models.User;
import com.SafeWebDev.attempt.Models.Respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserService{

    @Autowired
    UserRepository userRepository;


    public void saveUser(User user){
        userRepository.save(user);
    }

    public User findByName(String name,int pass){



        return userRepository.findByName(name,pass);
    }


    public User findByOnlyName(String name){
        return userRepository.findyByOnlyName(name);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public void delete(User user){
        userRepository.delete(user);
    }

    public User findById(long id){
        return userRepository.findUserByUserID(id);
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
