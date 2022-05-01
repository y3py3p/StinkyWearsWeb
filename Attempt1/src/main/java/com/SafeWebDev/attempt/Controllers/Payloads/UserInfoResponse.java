package com.SafeWebDev.attempt.Controllers.Payloads;

import com.SafeWebDev.attempt.Models.RoleName;

public class UserInfoResponse {

    private Long id;
    private String username;
    private String email;
    private RoleName roleName;

    public UserInfoResponse(Long id, String username, String email, RoleName roleName){
        this.id=id;
        this.username=username;
        this.email=email;
        this.roleName=roleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public RoleName getRoles() {
        return roleName;
    }
}
