package com.yankuang.equipment.equipment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Data
@Entity
@Table(name = "el_equipment_group")
public class ElEquipmentGroup implements Serializable {
    private Long id;

    private String code;

    private Byte status;

    private Long planId;

    private Long planCode;

    private String surfaceCode;

    private Long surfaceId;

    private String sbTypeCode;

    private String sbModelCode;

    private String paramName;

    private String paramValue;

    private Long sbNum;

    private String sbUnit;

    private String remarks;

    private String createBy;

    private Date createAt;

    private Long updateBy;

    private Date updateAt;

    private Long sorting;

    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getPlanCode() {
        return planCode;
    }

    public void setPlanCode(Long planCode) {
        this.planCode = planCode;
    }

    public String getSurfaceCode() {
        return surfaceCode;
    }

    public void setSurfaceCode(String surfaceCode) {
        this.surfaceCode = surfaceCode == null ? null : surfaceCode.trim();
    }

    public Long getSurfaceId() {
        return surfaceId;
    }

    public void setSurfaceId(Long surfaceId) {
        this.surfaceId = surfaceId;
    }

    public String getSbTypeCode() {
        return sbTypeCode;
    }

    public void setSbTypeCode(String sbTypeCode) {
        this.sbTypeCode = sbTypeCode == null ? null : sbTypeCode.trim();
    }

    public String getSbModelCode() {
        return sbModelCode;
    }

    public void setSbModelCode(String sbModelCode) {
        this.sbModelCode = sbModelCode == null ? null : sbModelCode.trim();
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName == null ? null : paramName.trim();
    }

    public String getParamValue() {
        return paramValue;
    }

    public void setParamValue(String paramValue) {
        this.paramValue = paramValue == null ? null : paramValue.trim();
    }

    public Long getSbNum() {
        return sbNum;
    }

    public void setSbNum(Long sbNum) {
        this.sbNum = sbNum;
    }

    public String getSbUnit() {
        return sbUnit;
    }

    public void setSbUnit(String sbUnit) {
        this.sbUnit = sbUnit == null ? null : sbUnit.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Long getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Long updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Long getSorting() {
        return sorting;
    }

    public void setSorting(Long sorting) {
        this.sorting = sorting;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}