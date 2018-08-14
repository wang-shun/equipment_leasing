package com.yankuang.equipment.equipment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "el_plan")
public class ElPlan implements Serializable {
    private String planId;

    @NotNull(message = "需求年度不得为空")
    private Integer planYear;

    private Integer planMonth;

    private Integer planQuarter;

    @NotNull(message = "提出单位不得为空")
    private String planPosition;

    private String planRemarks;

    private String planOpinion;

    @NotNull(message = "提出人ID不得为空")
    private String planCreatorId;

    private String planStatus;

    private Long planApproveTime;

    private String planApproverId;

    private String planApproverName;

    private String planEquipmentType;

    private String planType;

    private Boolean planIsDel;

    private Long planCreateTime;

    private Long planUpdateTime;

    private String planCode;

    private String planUpdatorId;

    private String planUpdatorName;

    private String planSorting;

    private String planVersion;

    @NotNull(message = "提出人姓名不得为空")
    private String planCreatorName;

    private List<ElPlanItem> elPlanItemList;

    public String getPlanId() {
        return planId;
    }

    public List<ElPlanItem> getElPlanItemList() {
        return elPlanItemList;
    }

    public void setElPlanItemList(List<ElPlanItem> elPlanItemList) {
        this.elPlanItemList = elPlanItemList;
    }

    public void setPlanId(String planId) {
        this.planId = planId == null ? null : planId.trim();
    }

    public Integer getPlanYear() {
        return planYear;
    }

    public void setPlanYear(Integer planYear) {
        this.planYear = planYear;
    }

    public Integer getPlanMonth() {
        return planMonth;
    }

    public void setPlanMonth(Integer planMonth) {
        this.planMonth = planMonth;
    }

    public Integer getPlanQuarter() {
        return planQuarter;
    }

    public void setPlanQuarter(Integer planQuarter) {
        this.planQuarter = planQuarter;
    }

    public String getPlanPosition() {
        return planPosition;
    }

    public void setPlanPosition(String planPosition) {
        this.planPosition = planPosition == null ? null : planPosition.trim();
    }

    public String getPlanRemarks() {
        return planRemarks;
    }

    public void setPlanRemarks(String planRemarks) {
        this.planRemarks = planRemarks == null ? null : planRemarks.trim();
    }

    public String getPlanOpinion() {
        return planOpinion;
    }

    public void setPlanOpinion(String planOpinion) {
        this.planOpinion = planOpinion == null ? null : planOpinion.trim();
    }

    public String getPlanCreatorId() {
        return planCreatorId;
    }

    public void setPlanCreatorId(String planCreatorId) {
        this.planCreatorId = planCreatorId == null ? null : planCreatorId.trim();
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus == null ? null : planStatus.trim();
    }

    public Long getPlanApproveTime() {
        return planApproveTime;
    }

    public void setPlanApproveTime(Long planApproveTime) {
        this.planApproveTime = planApproveTime;
    }

    public String getPlanApproverId() {
        return planApproverId;
    }

    public void setPlanApproverId(String planApproverId) {
        this.planApproverId = planApproverId == null ? null : planApproverId.trim();
    }

    public String getPlanEquipmentType() {
        return planEquipmentType;
    }

    public void setPlanEquipmentType(String planEquipmentType) {
        this.planEquipmentType = planEquipmentType == null ? null : planEquipmentType.trim();
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType == null ? null : planType.trim();
    }

    public Boolean getPlanIsDel() {
        return planIsDel;
    }

    public void setPlanIsDel(Boolean planIsDel) {
        this.planIsDel = planIsDel;
    }

    public Long getPlanCreateTime() {
        return planCreateTime;
    }

    public void setPlanCreateTime(Long planCreateTime) {
        this.planCreateTime = planCreateTime;
    }

    public Long getPlanUpdateTime() {
        return planUpdateTime;
    }

    public void setPlanUpdateTime(Long planUpdateTime) {
        this.planUpdateTime = planUpdateTime;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode == null ? null : planCode.trim();
    }

    public String getPlanUpdatorId() {
        return planUpdatorId;
    }

    public void setPlanUpdatorId(String planUpdatorId) {
        this.planUpdatorId = planUpdatorId == null ? null : planUpdatorId.trim();
    }

    public String getPlanSorting() {
        return planSorting;
    }

    public void setPlanSorting(String planSorting) {
        this.planSorting = planSorting == null ? null : planSorting.trim();
    }

    public String getPlanVersion() {
        return planVersion;
    }

    public void setPlanVersion(String planVersion) {
        this.planVersion = planVersion == null ? null : planVersion.trim();
    }

    public @NotNull( message = "提出人姓名不得为空" ) String getPlanCreatorName() {
        return planCreatorName;
    }

    public String getPlanApproverName() {
        return planApproverName;
    }

    public String getPlanUpdatorName() {
        return planUpdatorName;
    }
}