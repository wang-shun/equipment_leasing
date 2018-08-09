package com.yankuang.equipment.web.dto;

public class OrgRoleDTO {
    private String account;
    private String name;
    private Byte sex;
    private Long orgId;
    private Long deptId;
    private Long roleId;
    private Long userId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public Long getOrgId() {
        return orgId;
    }

    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OrgRoleDTO{" + "account='" + account + '\'' + ", name='" + name + '\'' + ", sex=" + sex + ", orgId=" + orgId + ", deptId=" + deptId + ", roleId=" + roleId + ", userId=" + userId + '}';
    }
}
