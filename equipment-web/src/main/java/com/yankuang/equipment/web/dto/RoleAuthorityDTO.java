package com.yankuang.equipment.web.dto;

import java.io.Serializable;
import java.util.List;

public class RoleAuthorityDTO implements Serializable {

    private Long roleId;

    private List<Long> authorityIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getAuthorityIds() {
        return authorityIds;
    }

    public void setAuthorityIds(List<Long> authorityIds) {
        this.authorityIds = authorityIds;
    }

    @Override
    public String toString() {
        return "{RoleAuthorityDTO:{" +
                "roleId=" + roleId +
                ", authorityIds=" + authorityIds.toString() +
                '}' + '}';
    }
}
