package com.SafeWebDev.attempt.Controllers.Payloads;

public class MessageResponse {

    private String message;

    public MessageResponse(String message){
        this.message = message;
    }

    public String getMessage(){
        return this.message;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
