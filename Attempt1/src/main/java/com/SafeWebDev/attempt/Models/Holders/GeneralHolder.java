package com.SafeWebDev.attempt.Models.Holders;

import com.SafeWebDev.attempt.*;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GeneralHolder {

    private ItemHolder itemholder=new ItemHolder();
    private UserHolder userholder=new UserHolder();

    /*public GeneralHolder(){
        setCurrentUser(new User("Usuario temporal","deez"));
    }

    public User getCurrentUser(){
        return userholder.getCurrentUser();
    }

    public void setCurrentUser(User user){
        userholder.setCurrentUser(user);
    }

    public void addItem(Item item){
        itemholder.addItem(item);
    }

    public void addUsr(User user){
        userholder.addUser(user);
    }

    public User getUsrId(long id){
        return userholder.getUsrbyId(id);
    }

    public Item getItemId(long id){
        return itemholder.getById(id);
    }

    public Map<Long,Item> getItems(){
        return itemholder.getItems();
    }

    public boolean logedIn(){
        return userholder.getCurrentUser() != null;
    }

    public boolean containsItem(long id){
        return itemholder.containsItem(id);
    }
*/
}
