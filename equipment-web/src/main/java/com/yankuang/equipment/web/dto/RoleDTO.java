package com.yankuang.equipment.web.dto;

import java.io.Serializable;

public class RoleDTO implements Serializable {

    private Long id;

    private String name;

    public String toJsonString() {
        return "{RoleDTO:{" +
                "id:" + id +
                ", name:'" + name + '\'' +
                '}'+"}";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
