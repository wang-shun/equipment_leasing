package com.yankuang.equipment.web.dto;

import java.io.Serializable;

public class AuthorityDTO implements Serializable {
    private Long id;

    private String name;

    private String code;

    private Long pId;

    private Long type;

    private Long sorting;

    public String toJsonString() {
        return "{AuthorityDTO:{" +
                "id:" + id +
                ", name:'" + name + '\'' +
                ", code:'" + code + '\'' +
                ", pId:" + pId +
                ", type:" + type +
                ", sorting:" + sorting +
                '}' + "}";
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public Long getSorting() {
        return sorting;
    }

    public void setSorting(Long sorting) {
        this.sorting = sorting;
    }
}
