package com.yankuang.equipment.equipment.model;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

public class SbModel implements Serializable {
    private Long id;

    private String code;

    @NotEmpty(message = "设备型号名称不能为空!")
    @Length(min = 1,max = 200,message = "设备型号名称长度超出范围!")
    private String name;

    private String sbtypeOne;
    private String sbtypeOneName;

    private String sbtypeTwo;
    private String sbtypeTwoName;

    private String sbtypeThree;
    private String sbtypeThreeName;

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSbtypeOne() {
        return sbtypeOne;
    }

    public void setSbtypeOne(String sbtypeOne) {
        this.sbtypeOne = sbtypeOne == null ? null : sbtypeOne.trim();
    }

    public String getSbtypeTwo() {
        return sbtypeTwo;
    }

    public void setSbtypeTwo(String sbtypeTwo) {
        this.sbtypeTwo = sbtypeTwo == null ? null : sbtypeTwo.trim();
    }

    public String getSbtypeThree() {
        return sbtypeThree;
    }

    public void setSbtypeThree(String sbtypeThree) {
        this.sbtypeThree = sbtypeThree == null ? null : sbtypeThree.trim();
    }

    public String getSbtypeOneName() {
        return sbtypeOneName;
    }

    public void setSbtypeOneName(String sbtypeOneName) {
        this.sbtypeOneName = sbtypeOneName;
    }

    public String getSbtypeTwoName() {
        return sbtypeTwoName;
    }

    public void setSbtypeTwoName(String sbtypeTwoName) {
        this.sbtypeTwoName = sbtypeTwoName;
    }

    public String getSbtypeThreeName() {
        return sbtypeThreeName;
    }

    public void setSbtypeThreeName(String sbtypeThreeName) {
        this.sbtypeThreeName = sbtypeThreeName;
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