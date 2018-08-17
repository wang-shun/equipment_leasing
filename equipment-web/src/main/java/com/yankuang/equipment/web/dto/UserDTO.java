package com.yankuang.equipment.web.dto;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable {

    private Long id;

    private String name;

    private String token;

    private List<RoleDTO> roles;

    private List<AuthorityDTO> authoritys;

    public String toJsonString() {
        return "{UserDTO:{" +
                "id:" + id +
                ", name:'" + name + '\'' +
                ", token:'" + token + '\'' +
                ", roles:" + roles.toString() +
                ", authoritys:" + authoritys.toString() +
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public List<AuthorityDTO> getAuthoritys() {
        return authoritys;
    }

    public void setAuthoritys(List<AuthorityDTO> authoritys) {
        this.authoritys = authoritys;
    }
}
