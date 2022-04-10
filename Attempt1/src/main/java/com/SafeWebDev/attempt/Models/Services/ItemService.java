package com.SafeWebDev.attempt.Models.Services;

import com.SafeWebDev.attempt.Models.Entities.Item;
import com.SafeWebDev.attempt.Models.Respositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Component
public class ItemService {

    @Autowired
    ItemRepository itemRepository;

    public ItemService(){

    }

    public List<Item> getAll(){
        return itemRepository.findAll();
    }

    public Item findById(long id){
        return itemRepository.findById(id);
    }

    public void add(Item item){
        itemRepository.save(item);
    }

    public boolean exists(long id){
        if(itemRepository.findById(id) != null){
            return true;
        }else {
            return false;
        }
    }

    public void updateItem(long id, Item item){

        Item updated = findById(id);
        updated.update(item);
        itemRepository.save(updated);
    }
}
