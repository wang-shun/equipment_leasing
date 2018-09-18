package com.yankuang.equipment.equipment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
@Data
@Entity
@Table(name = "el_fee_quarter_t")
public class ElFeeQuarterT {
    private Long id;

    private String code;

    private Long sorting;

    private Long status;

    private Long version;

    private String remarks;

    private Long createBy;

    private Date createAt;

    private Long updateBy;

    private Date updateAt;

    private Date exportAt;

    private String positionName;

    private String positionCode;

    private Long centerLeaderBy;

    private Long centerOfficeBy;

    private Long centerHandleBy;

    private Long positionBy;

    private String reportYear;

    private String reportQuarter;

    private String reportMonth;

    private Long useNum;

    private Double totalCostA1;

    private Double totalCostA2;

    private Double totalCostA3;

    private Double totalFee;

    private Long totalDay;

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

    public Long getSorting() {
        return sorting;
    }

    public void setSorting(Long sorting) {
        this.sorting = sorting;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Long getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Long createBy) {
        this.createBy = createBy;
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

    public Date getExportAt() {
        return exportAt;
    }

    public void setExportAt(Date exportAt) {
        this.exportAt = exportAt;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName == null ? null : positionName.trim();
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode == null ? null : positionCode.trim();
    }

    public Long getCenterLeaderBy() {
        return centerLeaderBy;
    }

    public void setCenterLeaderBy(Long centerLeaderBy) {
        this.centerLeaderBy = centerLeaderBy;
    }

    public Long getCenterOfficeBy() {
        return centerOfficeBy;
    }

    public void setCenterOfficeBy(Long centerOfficeBy) {
        this.centerOfficeBy = centerOfficeBy;
    }

    public Long getCenterHandleBy() {
        return centerHandleBy;
    }

    public void setCenterHandleBy(Long centerHandleBy) {
        this.centerHandleBy = centerHandleBy;
    }

    public Long getPositionBy() {
        return positionBy;
    }

    public void setPositionBy(Long positionBy) {
        this.positionBy = positionBy;
    }

    public String getReportYear() {
        return reportYear;
    }

    public void setReportYear(String reportYear) {
        this.reportYear = reportYear == null ? null : reportYear.trim();
    }

    public String getReportQuarter() {
        return reportQuarter;
    }

    public void setReportQuarter(String reportQuarter) {
        this.reportQuarter = reportQuarter == null ? null : reportQuarter.trim();
    }

    public String getReportMonth() {
        return reportMonth;
    }

    public void setReportMonth(String reportMonth) {
        this.reportMonth = reportMonth == null ? null : reportMonth.trim();
    }

    public Long getUseNum() {
        return useNum;
    }

    public void setUseNum(Long useNum) {
        this.useNum = useNum;
    }

    public Double getTotalCostA1() {
        return totalCostA1;
    }

    public void setTotalCostA1(Double totalCostA1) {
        this.totalCostA1 = totalCostA1;
    }

    public Double getTotalCostA2() {
        return totalCostA2;
    }

    public void setTotalCostA2(Double totalCostA2) {
        this.totalCostA2 = totalCostA2;
    }

    public Double getTotalCostA3() {
        return totalCostA3;
    }

    public void setTotalCostA3(Double totalCostA3) {
        this.totalCostA3 = totalCostA3;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public Long getTotalDay() {
        return totalDay;
    }

    public void setTotalDay(Long totalDay) {
        this.totalDay = totalDay;
    }
}