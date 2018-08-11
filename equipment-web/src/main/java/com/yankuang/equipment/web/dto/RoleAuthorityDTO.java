package com.yankuang.equipment.web.dto;

import java.io.Serializable;
import java.util.List;

public class RoleAuthorityDTO implements Serializable {

    private Long roleId;

    private List<Long> groupIds;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getGroupIds() {
        return groupIds;
    }

    public void setGroupIds(List<Long> groupIds) {
        this.groupIds = groupIds;
    }

    @Override
    public String toString() {
        return "{RoleAuthorityDTO:{" +
                "roleId=" + roleId +
                ", groupIds=" + groupIds +
                '}' + '}';
    }
}
