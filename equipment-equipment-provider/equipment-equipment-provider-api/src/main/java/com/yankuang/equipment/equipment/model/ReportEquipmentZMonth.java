package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class ReportEquipmentZMonth implements Serializable {
    private Byte id;

    private String year;

    private String reportName;

    private String remark;

    private Byte status;

    private String createBy;

    private Date createAt;

    private String updateBy;

    private Date updateAt;

    private Long version;

    private List<ReportEquipmentZMonthItem> list1;
    private List<ReportEquipmentZMonthItem> list2;
    private List<ReportEquipmentZMonthItem> list3;

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year == null ? null : year.trim();
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName == null ? null : reportName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

    public List<ReportEquipmentZMonthItem> getList1() {
        return list1;
    }

    public void setList1(List<ReportEquipmentZMonthItem> list1) {
        this.list1 = list1;
    }

    public List<ReportEquipmentZMonthItem> getList2() {
        return list2;
    }

    public void setList2(List<ReportEquipmentZMonthItem> list2) {
        this.list2 = list2;
    }

    public List<ReportEquipmentZMonthItem> getList3() {
        return list3;
    }

    public void setList3(List<ReportEquipmentZMonthItem> list3) {
        this.list3 = list3;
    }
}