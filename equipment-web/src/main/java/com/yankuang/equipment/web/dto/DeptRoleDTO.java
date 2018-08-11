package com.yankuang.equipment.web.dto;

import java.io.Serializable;

public class DeptRoleDTO implements Serializable {

    private Long deptId;

    private String name;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
