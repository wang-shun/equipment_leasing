package com.yankuang.equipment.authority.model;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "el_authority_group_mapping")
public class AuthorityGroupMapping implements Serializable {

    private Long id;

    private Long groupId;

    private Long authorityId;

    private Byte status;

    private String createBy;

    private Long createAt;

    private String updateBy;

    private Long updateAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Long authorityId) {
        this.authorityId = authorityId;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Long createAt) {
        this.createAt = createAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Long getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Long updateAt) {
        this.updateAt = updateAt;
    }

    @Override
    public String toString() {
        return "AuthorityGroupMapping{" +
                "id=" + id +
                ", groupId=" + groupId +
                ", authorityId=" + authorityId +
                ", status=" + status +
                ", createBy='" + createBy + '\'' +
                ", createAt=" + createAt +
                ", updateBy='" + updateBy + '\'' +
                ", updateAt=" + updateAt +
                '}';
    }
}
