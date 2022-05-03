package com.SafeWebDev.attempt.Controllers.Security;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@Order(1)
public class RestSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	
        http.antMatcher("/api/**");
        // Private endpoints
        http.authorizeRequests()
        .antMatchers("/api/see").authenticated()
        .antMatchers("/api/del/**").authenticated()
        .antMatchers("/api/addItem").authenticated()
        .antMatchers("/api/editItem/**").authenticated()
        .antMatchers("/api/addCart/**").authenticated()
        .antMatchers("/api/seeCart").authenticated()
        .antMatchers("/api/removeCart/**").authenticated()
        .antMatchers("/api/usr").authenticated()
        .antMatchers("/api/comments").permitAll()
        .antMatchers("/api/NewComment").authenticated()
        .antMatchers("/api/coupon/new").hasRole("ADMIN")
        .antMatchers("/api/coupons").permitAll()
        .antMatchers("/api/pay/**").authenticated()
        .antMatchers("/api/search/**").permitAll();
    		
        // Other endpoints are public
        http.authorizeRequests().anyRequest().authenticated();

        // Disable CSRF protection (it is difficult to implement in REST APIs)
        http.csrf().disable();

        // Enable Basic Authentication
        http.httpBasic();
    		
        // Disable Form login Authentication
        http.formLogin().disable();

        // Avoid creating session (because every request has credentials) 
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
    }

    
}
