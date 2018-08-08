package com.yankuang.equipment.web.dto;

import java.io.Serializable;

public class TreeDTO implements Serializable {

    private Long id;
    private Long pId;
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "{TreeDTO:{" +
                "id='" + id + '\'' +
                ", pId='" + pId + '\'' +
                ", name='" + name + '\'' +
                '}' + '}';
    }
}
