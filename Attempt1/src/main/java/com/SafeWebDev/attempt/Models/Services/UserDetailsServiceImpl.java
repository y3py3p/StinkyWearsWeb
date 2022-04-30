package com.SafeWebDev.attempt.Models.Services;

import com.SafeWebDev.attempt.Models.RoleName;
import com.SafeWebDev.attempt.Models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;


@Service
@Transactional
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.findByOnlyName(username);

        if(user == null){
            log.error("User not found");
            throw new UsernameNotFoundException("User not found");
        }else{
            log.info("User found: {}", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().toString()));

        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPass(), authorities);
    }

    public void saveUser(User user){
        log.info("Saving new user {} to the database", user.getUserName());
        user.addRole(RoleName.USER);              //We assign the roles so that every user created through the app is a common user
        user.setUserPass(passwordEncoder.encode(user.getUserPass()));
        userService.saveUser(user);
    }

    public String login(String password){

        return password;
    }
}
