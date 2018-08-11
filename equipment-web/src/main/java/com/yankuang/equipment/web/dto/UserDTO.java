package com.yankuang.equipment.web.dto;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements Serializable {

    private String name;

    private List<Long> roleIds;

    private List<Long> authorityIds;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Long> roleIds) {
        this.roleIds = roleIds;
    }

    public List<Long> getAuthorityIds() {
        return authorityIds;
    }

    public void setAuthorityIds(List<Long> authorityIds) {
        this.authorityIds = authorityIds;
    }

    @Override
    public String toString() {
        return "{UserDTO{" +
                "name='" + name + '\'' +
                ", roleIds=" + roleIds +
                ", authorityIds=" + authorityIds +
                '}' + '}';
    }

}
