package com.SafeWebDev.attempt.Controllers;

import com.SafeWebDev.attempt.Models.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        /*//Setting public places
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
        http.authorizeRequests().antMatchers("/res/CreateAccount.html").permitAll();

        //Setting the rest of the pages as authenticated
        //http.authorizeRequests().anyRequest().authenticated();

        //Setting the login page variables
        http.formLogin().loginPage("/login");*/

        http.authorizeRequests()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/res/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/items").permitAll()
                .antMatchers("/item/new").permitAll()
                .antMatchers("/item/**").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").permitAll()
                .antMatchers("/account/created").permitAll()
                .antMatchers("/comments").permitAll()
                .antMatchers("/coupons").permitAll()
                .antMatchers("/search").permitAll();


    }


}
