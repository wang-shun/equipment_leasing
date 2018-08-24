package com.yankuang.equipment.web.dto;

import com.yankuang.equipment.authority.model.Dept;

public class DeptDTO extends Dept {

    private Long orgId;

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

}
