package com.drew.surfphotos.common.model;

import java.util.*;

public class ListMap<K, V> {
    private final Map<K, List<V>> map = new LinkedHashMap<>();

    public void add(K key, V value) {
        List<V> list = map.get(key);
        if(list == null) {
            list = new ArrayList<>();
            map.put(key, list);
        }
        list.add(value);
    }

    public Map<K, List<V>> toMap(){
        return Collections.unmodifiableMap(map);
    }
}
