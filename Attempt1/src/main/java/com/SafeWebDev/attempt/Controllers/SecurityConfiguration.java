package com.SafeWebDev.attempt.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserDetailsService userDetailsService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{

        //http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //Setting public places
        http.authorizeRequests()
                .antMatchers("/img/**").permitAll()
                .antMatchers("/res/**").permitAll()
                .antMatchers("/").permitAll()
                .antMatchers("/items").permitAll()
                .antMatchers("/item/new").hasAnyRole("ADMIN", "USER")
                .antMatchers("/item/edit/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/editting/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/item/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/usr").hasAnyRole("ADMIN", "USER")
                .antMatchers("/item/del/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/cart").hasAnyRole("ADMIN", "USER")
                .antMatchers("/cart/**").hasAnyRole("ADMIN", "USER")
                .antMatchers("/login").permitAll()
                .antMatchers("/logout").hasAnyRole("ADMIN", "USER")
                .antMatchers("/account/created").permitAll()
                .antMatchers("/comments").permitAll()
                .antMatchers("/NewComment").hasAnyRole("ADMIN", "USER")
                .antMatchers("/createComment").hasAnyRole("ADMIN", "USER")
                .antMatchers("/payments").hasAnyRole("ADMIN", "USER")
                .antMatchers("/pay").hasAnyRole("ADMIN", "USER")
                .antMatchers("/price/final").hasAnyRole("ADMIN", "USER")
                .antMatchers("/coupons").hasAnyRole("ADMIN", "USER")
                .antMatchers("/coupons").hasAnyRole("ADMIN", "USER")
                .antMatchers("/coupon/new").hasAnyRole("ADMIN")
                .antMatchers("/searchsi").permitAll();


        //http.addFilter(new CustomAuthenticationFilter(authenticationManagerBean()));

        //Setting the rest of the pages as authenticated
        http.authorizeRequests().anyRequest().authenticated();

        //Setting the login page variables
        http.formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").defaultSuccessUrl("/usr").failureUrl("/login");

        http.csrf().disable();


    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return super.authenticationManagerBean();
    }


}
