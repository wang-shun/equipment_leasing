package com.yankuang.equipment.equipment.model;

import java.io.Serializable;

public class ReportEquipmentZMonthSumItem implements Serializable {
    private Byte id;

    private String deptCode;

    private String deptName;

    private String deptType;

    private Double yearPlanVal;

    private Double curMonthsPlanVal;

    private Double curMonthsVal;

    private Double preMonthsVal;

    private Double curMonthVal;

    public Byte getId() {
        return id;
    }

    public void setId(Byte id) {
        this.id = id;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public void setDeptCode(String deptCode) {
        this.deptCode = deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType;
    }

    public Double getYearPlanVal() {
        return yearPlanVal;
    }

    public void setYearPlanVal(Double yearPlanVal) {
        this.yearPlanVal = yearPlanVal;
    }

    public Double getCurMonthsPlanVal() {
        return curMonthsPlanVal;
    }

    public void setCurMonthsPlanVal(Double curMonthsPlanVal) {
        this.curMonthsPlanVal = curMonthsPlanVal;
    }

    public Double getCurMonthsVal() {
        return curMonthsVal;
    }

    public void setCurMonthsVal(Double curMonthsVal) {
        this.curMonthsVal = curMonthsVal;
    }

    public Double getPreMonthsVal() {
        return preMonthsVal;
    }

    public void setPreMonthsVal(Double preMonthsVal) {
        this.preMonthsVal = preMonthsVal;
    }

    public Double getCurMonthVal() {
        return curMonthVal;
    }

    public void setCurMonthVal(Double curMonthVal) {
        this.curMonthVal = curMonthVal;
    }
}
