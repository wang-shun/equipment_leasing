package com.yankuang.equipment.equipment.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class ReportEquipmentZMonthSum implements Serializable {
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

    private List<ReportEquipmentZMonthSumItem> list1;
    private List<ReportEquipmentZMonthSumItem> list2;
    private List<ReportEquipmentZMonthSumItem> list3;

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

    public List<ReportEquipmentZMonthSumItem> getList1() {
        return list1;
    }

    public void setList1(List<ReportEquipmentZMonthSumItem> list1) {
        this.list1 = list1;
    }

    public List<ReportEquipmentZMonthSumItem> getList2() {
        return list2;
    }

    public void setList2(List<ReportEquipmentZMonthSumItem> list2) {
        this.list2 = list2;
    }

    public List<ReportEquipmentZMonthSumItem> getList3() {
        return list3;
    }

    public void setList3(List<ReportEquipmentZMonthSumItem> list3) {
        this.list3 = list3;
    }
}