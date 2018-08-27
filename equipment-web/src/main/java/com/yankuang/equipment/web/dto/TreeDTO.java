package com.yankuang.equipment.web.dto;

import java.io.Serializable;

public class TreeDTO implements Serializable {

    private Long id;
    private Long pId;
    private String name;
    private Long level;
    private String url;
    private Long type;


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

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TreeDTO{" +
                "id=" + id +
                ", pId=" + pId +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", url='" + url + '\'' +
                ", type=" + type +
                '}';
    }
}
