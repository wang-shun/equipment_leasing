package com.yankuang.equipment.web.dto;

import com.yankuang.equipment.equipment.model.ElPlanItem;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class ElPlanDTO implements Serializable {
    private String planId;

    @NotNull(message = "需求年度不得为空")
    private String planYear;

    private String planMonth;

    private String planQuarter;

    @NotNull(message = "提出单位不得为空")
    private String planPosition;

    private String planPositionName;

    private String planRemarks;

    private String planOpinion;

    @NotNull(message = "提出人ID不得为空")
    private String planCreatorId;

    private String planStatus;

    private Long planApproveTime;
    private String approveTimeStr;

    private String planApproverId;

    private String planApproverName;

    private String planEquipmentType;

    private String planType;

    private Boolean planIsDel;

    private Long planCreateTime;
    private String createTimeStr;

    private Long planUpdateTime;
    private String updateTimeStr;

    private String planCode;

    private String planUpdatorId;

    private String planUpdatorName;

    private String planSorting;

    private String planVersion;

    @NotNull(message = "提出人姓名不得为空")
    private String planCreatorName;

    private List<ElPlanItem> elPlanItemList;

    private Integer page;

    private Integer size;

    private String smallTypeCode;

    private String middleTypeCode;

    private String bigTypeCode;

    private String equipmentCode;

    private String effectCode;

    private String equipmentModel;

    private String equipmentFactory;

    private String equipmentName;

    private String positionId;

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanYear() {
        return planYear;
    }

    public void setPlanYear(String planYear) {
        this.planYear = planYear;
    }

    public String getPlanMonth() {
        return planMonth;
    }

    public void setPlanMonth(String planMonth) {
        this.planMonth = planMonth;
    }

    public String getPlanQuarter() {
        return planQuarter;
    }

    public void setPlanQuarter(String planQuarter) {
        this.planQuarter = planQuarter;
    }

    public String getPlanPosition() {
        return planPosition;
    }

    public void setPlanPosition(String planPosition) {
        this.planPosition = planPosition;
    }

    public String getPlanPositionName() {
        return planPositionName;
    }

    public void setPlanPositionName(String planPositionName) {
        this.planPositionName = planPositionName;
    }

    public String getPlanRemarks() {
        return planRemarks;
    }

    public void setPlanRemarks(String planRemarks) {
        this.planRemarks = planRemarks;
    }

    public String getPlanOpinion() {
        return planOpinion;
    }

    public void setPlanOpinion(String planOpinion) {
        this.planOpinion = planOpinion;
    }

    public String getPlanCreatorId() {
        return planCreatorId;
    }

    public void setPlanCreatorId(String planCreatorId) {
        this.planCreatorId = planCreatorId;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public Long getPlanApproveTime() {
        return planApproveTime;
    }

    public void setPlanApproveTime(Long planApproveTime) {
        this.planApproveTime = planApproveTime;
    }

    public String getApproveTimeStr() {
        return approveTimeStr;
    }

    public void setApproveTimeStr(String approveTimeStr) {
        this.approveTimeStr = approveTimeStr;
    }

    public String getPlanApproverId() {
        return planApproverId;
    }

    public void setPlanApproverId(String planApproverId) {
        this.planApproverId = planApproverId;
    }

    public String getPlanApproverName() {
        return planApproverName;
    }

    public void setPlanApproverName(String planApproverName) {
        this.planApproverName = planApproverName;
    }

    public String getPlanEquipmentType() {
        return planEquipmentType;
    }

    public void setPlanEquipmentType(String planEquipmentType) {
        this.planEquipmentType = planEquipmentType;
    }

    public String getPlanType() {
        return planType;
    }

    public void setPlanType(String planType) {
        this.planType = planType;
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

    public String getCreateTimeStr() {
        return createTimeStr;
    }

    public void setCreateTimeStr(String createTimeStr) {
        this.createTimeStr = createTimeStr;
    }

    public Long getPlanUpdateTime() {
        return planUpdateTime;
    }

    public void setPlanUpdateTime(Long planUpdateTime) {
        this.planUpdateTime = planUpdateTime;
    }

    public String getUpdateTimeStr() {
        return updateTimeStr;
    }

    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanUpdatorId() {
        return planUpdatorId;
    }

    public void setPlanUpdatorId(String planUpdatorId) {
        this.planUpdatorId = planUpdatorId;
    }

    public String getPlanUpdatorName() {
        return planUpdatorName;
    }

    public void setPlanUpdatorName(String planUpdatorName) {
        this.planUpdatorName = planUpdatorName;
    }

    public String getPlanSorting() {
        return planSorting;
    }

    public void setPlanSorting(String planSorting) {
        this.planSorting = planSorting;
    }

    public String getPlanVersion() {
        return planVersion;
    }

    public void setPlanVersion(String planVersion) {
        this.planVersion = planVersion;
    }

    public String getPlanCreatorName() {
        return planCreatorName;
    }

    public void setPlanCreatorName(String planCreatorName) {
        this.planCreatorName = planCreatorName;
    }

    public List<ElPlanItem> getElPlanItemList() {
        return elPlanItemList;
    }

    public void setElPlanItemList(List<ElPlanItem> elPlanItemList) {
        this.elPlanItemList = elPlanItemList;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getSmallTypeCode() {
        return smallTypeCode;
    }

    public void setSmallTypeCode(String smallTypeCode) {
        this.smallTypeCode = smallTypeCode;
    }

    public String getMiddleTypeCode() {
        return middleTypeCode;
    }

    public void setMiddleTypeCode(String middleTypeCode) {
        this.middleTypeCode = middleTypeCode;
    }

    public String getBigTypeCode() {
        return bigTypeCode;
    }

    public void setBigTypeCode(String bigTypeCode) {
        this.bigTypeCode = bigTypeCode;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getEffectCode() {
        return effectCode;
    }

    public void setEffectCode(String effectCode) {
        this.effectCode = effectCode;
    }

    public String getEquipmentModel() {
        return equipmentModel;
    }

    public void setEquipmentModel(String equipmentModel) {
        this.equipmentModel = equipmentModel;
    }

    public String getEquipmentFactory() {
        return equipmentFactory;
    }

    public void setEquipmentFactory(String equipmentFactory) {
        this.equipmentFactory = equipmentFactory;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }

    @Override
    public String toString() {
        return "ElPlanDTO{" +
                "planId='" + planId + '\'' +
                ", planYear='" + planYear + '\'' +
                ", planMonth='" + planMonth + '\'' +
                ", planQuarter='" + planQuarter + '\'' +
                ", planPosition='" + planPosition + '\'' +
                ", planPositionName='" + planPositionName + '\'' +
                ", planRemarks='" + planRemarks + '\'' +
                ", planOpinion='" + planOpinion + '\'' +
                ", planCreatorId='" + planCreatorId + '\'' +
                ", planStatus='" + planStatus + '\'' +
                ", planApproveTime=" + planApproveTime +
                ", approveTimeStr='" + approveTimeStr + '\'' +
                ", planApproverId='" + planApproverId + '\'' +
                ", planApproverName='" + planApproverName + '\'' +
                ", planEquipmentType='" + planEquipmentType + '\'' +
                ", planType='" + planType + '\'' +
                ", planIsDel=" + planIsDel +
                ", planCreateTime=" + planCreateTime +
                ", createTimeStr='" + createTimeStr + '\'' +
                ", planUpdateTime=" + planUpdateTime +
                ", updateTimeStr='" + updateTimeStr + '\'' +
                ", planCode='" + planCode + '\'' +
                ", planUpdatorId='" + planUpdatorId + '\'' +
                ", planUpdatorName='" + planUpdatorName + '\'' +
                ", planSorting='" + planSorting + '\'' +
                ", planVersion='" + planVersion + '\'' +
                ", planCreatorName='" + planCreatorName + '\'' +
                ", elPlanItemList=" + elPlanItemList +
                ", page=" + page +
                ", size=" + size +
                ", smallTypeCode='" + smallTypeCode + '\'' +
                ", middleTypeCode='" + middleTypeCode + '\'' +
                ", bigTypeCode='" + bigTypeCode + '\'' +
                ", equipmentCode='" + equipmentCode + '\'' +
                ", effectCode='" + effectCode + '\'' +
                ", equipmentModel='" + equipmentModel + '\'' +
                ", equipmentFactory='" + equipmentFactory + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", positionId='" + positionId + '\'' +
                '}';
    }
}
