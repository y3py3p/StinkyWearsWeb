package com.SafeWebDev.attempt.Models;


import org.assertj.core.internal.bytebuddy.dynamic.DynamicType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ItemHolder {
    private Map<Long, Item> items = new ConcurrentHashMap<>();
    //private List<Item> items = new ArrayList<>();
    private AtomicLong lastID = new AtomicLong();

    public Item addItem(Item item){
        long id = lastID.incrementAndGet();
        item.setId(id);
        items.put(id, item);
        //items.add(item);
        return item;
    }

    public Map<Long, Item> getItems() {
        return items;
    }


    public Item getById(long id){
        return items.get(id);
    }
}
