package com.yankuang.equipment.web.dto;

public class GenericDTO {
    public String getKey() {
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

    String key;
    Object value;

    public GenericDTO(String key, Object value) {
        this.key = key;
        this.value = value;
    }
}
