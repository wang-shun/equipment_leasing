package com.yankuang.equipment.equipment.model;

import java.io.Serializable;

public class ReportEquipmentZMonthItem implements Serializable {
    private Byte id;

    private String deptCode;

    private String deptName;

    private String deptType;

    private Double yearPlanVal;

    private Double month01;

    private Double month02;

    private Double month03;

    private Double month04;

    private Double month05;

    private Double month06;

    private Double month07;

    private Double month08;

    private Double month09;

    private Double month10;

    private Double month11;

    private Double month12;

    private Byte reportId;

    private String rowno;//行号
    private Double monthPlanVal;//每月计划应收
    private Double planVal;//1-12月计划应收
    private Double sumVal;//合计
    private Double sumPlanVal;//合计-总计划

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
        this.deptCode = deptCode == null ? null : deptCode.trim();
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getDeptType() {
        return deptType;
    }

    public void setDeptType(String deptType) {
        this.deptType = deptType == null ? null : deptType.trim();
    }

    public Double getYearPlanVal() {
        return yearPlanVal;
    }

    public void setYearPlanVal(Double yearPlanVal) {
        this.yearPlanVal = yearPlanVal;
    }

    public Double getMonth01() {
        return month01;
    }

    public void setMonth01(Double month01) {
        this.month01 = month01;
    }

    public Double getMonth02() {
        return month02;
    }

    public void setMonth02(Double month02) {
        this.month02 = month02;
    }

    public Double getMonth03() {
        return month03;
    }

    public void setMonth03(Double month03) {
        this.month03 = month03;
    }

    public Double getMonth04() {
        return month04;
    }

    public void setMonth04(Double month04) {
        this.month04 = month04;
    }

    public Double getMonth05() {
        return month05;
    }

    public void setMonth05(Double month05) {
        this.month05 = month05;
    }

    public Double getMonth06() {
        return month06;
    }

    public void setMonth06(Double month06) {
        this.month06 = month06;
    }

    public Double getMonth07() {
        return month07;
    }

    public void setMonth07(Double month07) {
        this.month07 = month07;
    }

    public Double getMonth08() {
        return month08;
    }

    public void setMonth08(Double month08) {
        this.month08 = month08;
    }

    public Double getMonth09() {
        return month09;
    }

    public void setMonth09(Double month09) {
        this.month09 = month09;
    }

    public Double getMonth10() {
        return month10;
    }

    public void setMonth10(Double month10) {
        this.month10 = month10;
    }

    public Double getMonth11() {
        return month11;
    }

    public void setMonth11(Double month11) {
        this.month11 = month11;
    }

    public Double getMonth12() {
        return month12;
    }

    public void setMonth12(Double month12) {
        this.month12 = month12;
    }

    public Byte getReportId() {
        return reportId;
    }

    public void setReportId(Byte reportId) {
        this.reportId = reportId;
    }

    public String getRowno() {
        return rowno;
    }

    public void setRowno(String rowno) {
        this.rowno = rowno;
    }

    public Double getSumVal() {
        return sumVal;
    }

    public void setSumVal(Double sumVal) {
        this.sumVal = sumVal;
    }

    public Double getMonthPlanVal() {
        return monthPlanVal;
    }

    public void setMonthPlanVal(Double monthPlanVal) {
        this.monthPlanVal = monthPlanVal;
    }

    public Double getPlanVal() {
        return planVal;
    }

    public void setPlanVal(Double planVal) {
        this.planVal = planVal;
    }

    public Double getSumPlanVal() {
        return sumPlanVal;
    }

    public void setSumPlanVal(Double sumPlanVal) {
        this.sumPlanVal = sumPlanVal;
    }
}