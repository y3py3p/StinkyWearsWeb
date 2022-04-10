package com.SafeWebDev.attempt.Models.Holders;


import com.SafeWebDev.attempt.Models.Entities.Item;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public class ItemHolder {
    private Map<Long, Item> items = new ConcurrentHashMap<>();
    private AtomicLong lastID = new AtomicLong();

    public void addItem(Item item){
        long id = lastID.incrementAndGet();
        item.setId(id);
        items.put(id, item);
    }

    public Map<Long, Item> getItems() {
        return items;
    }


    public Item getById(long id){
        return items.get(id);
    }

    public boolean containsItem(long id){
        return items.containsKey(id);
    }
}
