package com.yankuang.equipment.web.dto;

public class OrgDeptDTO {

    private Long organizationId;

    private String name;

    private Long id;

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OrgDeptDTO{" + "organizationId=" + organizationId + ", name='" + name + '\'' + ", id=" + id + '}';
    }
}
