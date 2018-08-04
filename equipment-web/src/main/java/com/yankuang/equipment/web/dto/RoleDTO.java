package com.yankuang.equipment.web.dto;

import com.yankuang.equipment.authority.model.Authority;

import java.io.Serializable;
import java.util.List;

public class RoleDTO implements Serializable {
    private Long id;

    private String name;

    List<Authority> authorities;

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

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}
