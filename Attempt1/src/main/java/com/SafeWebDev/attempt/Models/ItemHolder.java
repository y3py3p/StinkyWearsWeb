package com.SafeWebDev.attempt.Models;


import org.assertj.core.internal.bytebuddy.dynamic.DynamicType;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ItemHolder {
    private Map<Long, Item> items = new ConcurrentHashMap<>();
    private AtomicLong lastID = new AtomicLong();

    public Item addItem(Item item){
        long id = lastID.incrementAndGet();
        item.setId(id);
        items.put(id, item);
        return item;
    }

    public Map<Long, Item> getItems() {
        return items;
    }

    public org.assertj.core.internal.bytebuddy.dynamic.DynamicType.Builder.RecordComponentDefinition.Optional<Item> getById(long id){
        return (DynamicType.Builder.RecordComponentDefinition.Optional<Item>) items.get(id);
    }
}
