package com.yankuang.equipment.equipment.model;

import java.io.Serializable;

public class ObjDTO implements Serializable {
    public Object getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    Object key;
    Object value;

    public ObjDTO(Object key, Object value) {
        this.key = key;
        this.value = value;
    }
}
