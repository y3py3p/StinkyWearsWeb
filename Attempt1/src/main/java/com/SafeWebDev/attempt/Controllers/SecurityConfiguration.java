package com.SafeWebDev.attempt.Controllers;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{
    
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        
        //Setting public places
        http.authorizeRequests().antMatchers("/img/**").permitAll();
        http.authorizeRequests().antMatchers("/res/**").permitAll();
        http.authorizeRequests().antMatchers("/").permitAll(); 
        http.authorizeRequests().antMatchers("/items").permitAll();
        http.authorizeRequests().antMatchers("/item/new").permitAll();
        http.authorizeRequests().antMatchers("/item/**").permitAll();
        http.authorizeRequests().antMatchers("/login").permitAll();
        http.authorizeRequests().antMatchers("/logout").permitAll();
        http.authorizeRequests().antMatchers("/account/created").permitAll();
        http.authorizeRequests().antMatchers("/comments").permitAll();
        http.authorizeRequests().antMatchers("/coupons").permitAll();
        http.authorizeRequests().antMatchers("/search").permitAll();

        //Setting the rest of the pages as authenticated
        http.authorizeRequests().anyRequest().authenticated();

        //Setting the login page variables
        http.formLogin().loginPage("/login");


    }
}
