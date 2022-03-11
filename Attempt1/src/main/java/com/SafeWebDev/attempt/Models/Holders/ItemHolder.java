package com.SafeWebDev.attempt.Models.Holders;


import com.SafeWebDev.attempt.Models.Item;
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
