package com.wellsfargo.coms.translationAdder.Misc;

import java.util.*;

public class OrderedProperties extends Properties {
    private final LinkedHashSet<Object> keyOrder = new LinkedHashSet<>();

    @Override
    public synchronized Enumeration<Object> keys() {
        return Collections.enumeration(keyOrder);
    }

    @Override
    public synchronized Object put(Object key, Object value) {
        keyOrder.add(key);
        return super.put(key, value);
    }
}