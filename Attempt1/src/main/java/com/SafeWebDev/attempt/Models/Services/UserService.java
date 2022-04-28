package com.SafeWebDev.attempt.Models.Services;

import com.SafeWebDev.attempt.Models.Item;
import com.SafeWebDev.attempt.Models.MyUserDetails;
import com.SafeWebDev.attempt.Models.Role;
import com.SafeWebDev.attempt.Models.User;
import com.SafeWebDev.attempt.Models.Respositories.UserRepository;
import com.mysql.cj.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@Component
public class UserService implements UserDetailsService{

    @Autowired
    UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByOnlyName(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find user");
        }

        return new MyUserDetails(user);
    }


    public void saveUser(User user){
        userRepository.save(user);
    }

    public User findByName(String name,String pass){
        return userRepository.findByName(name,pass);
    }

    public User findByOnlyName(String name){
        return userRepository.findyByOnlyName(name);
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
