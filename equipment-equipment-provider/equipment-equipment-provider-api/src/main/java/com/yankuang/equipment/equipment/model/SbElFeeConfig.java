package com.yankuang.equipment.equipment.model;

import java.io.Serializable;
import java.util.Date;

public class SbElFeeConfig implements Serializable {
    private Long id;

    private String year;

    private Double oneIncreRate;

    private Double oneElRate;

    private Double oneRepairRate;

    private Double oneManageRate;

    private Double oneUseRate;

    private Double twoRepairRate;

    private Double twoManageRate;

    private Byte status;

    private String createBy;

    private Date createAt;

    private String updateBy;

    private Date updateAt;

    private Long version;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public Double getOneIncreRate() {
        return oneIncreRate;
    }

    public void setOneIncreRate(Double oneIncreRate) {
        this.oneIncreRate = oneIncreRate;
    }

    public Double getOneElRate() {
        return oneElRate;
    }

    public void setOneElRate(Double oneElRate) {
        this.oneElRate = oneElRate;
    }

    public Double getOneRepairRate() {
        return oneRepairRate;
    }

    public void setOneRepairRate(Double oneRepairRate) {
        this.oneRepairRate = oneRepairRate;
    }

    public Double getOneManageRate() {
        return oneManageRate;
    }

    public void setOneManageRate(Double oneManageRate) {
        this.oneManageRate = oneManageRate;
    }

    public Double getOneUseRate() {
        return oneUseRate;
    }

    public void setOneUseRate(Double oneUseRate) {
        this.oneUseRate = oneUseRate;
    }

    public Double getTwoRepairRate() {
        return twoRepairRate;
    }

    public void setTwoRepairRate(Double twoRepairRate) {
        this.twoRepairRate = twoRepairRate;
    }

    public Double getTwoManageRate() {
        return twoManageRate;
    }

    public void setTwoManageRate(Double twoManageRate) {
        this.twoManageRate = twoManageRate;
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

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}