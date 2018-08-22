package com.yankuang.equipment.web.dto;

public class ElPlanUseDTO {

    private String planUseId;

    private String teachCode;

    private String equipmentName;

    private String modelName;

    private String factoryName;

    private String mainParamName;

    private String mainParamValue;

    private String equipmentCode;

    private String assetCode;

    private String bigTypeName;

    private String middleTypeName;

    private String smallTypeName;

    private String effectName;

    private String status;

    public String getTeachCode() {
        return teachCode;
    }

    public void setTeachCode(String teachCode) {
        this.teachCode = teachCode;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getFactoryName() {
        return factoryName;
    }

    public void setFactoryName(String factoryName) {
        this.factoryName = factoryName;
    }

    public String getMainParamName() {
        return mainParamName;
    }

    public void setMainParamName(String mainParamName) {
        this.mainParamName = mainParamName;
    }

    public String getMainParamValue() {
        return mainParamValue;
    }

    public void setMainParamValue(String mainParamValue) {
        this.mainParamValue = mainParamValue;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getAssetCode() {
        return assetCode;
    }

    public void setAssetCode(String assetCode) {
        this.assetCode = assetCode;
    }

    public String getBigTypeName() {
        return bigTypeName;
    }

    public void setBigTypeName(String bigTypeName) {
        this.bigTypeName = bigTypeName;
    }

    public String getMiddleTypeName() {
        return middleTypeName;
    }

    public void setMiddleTypeName(String middleTypeName) {
        this.middleTypeName = middleTypeName;
    }

    public String getSmallTypeName() {
        return smallTypeName;
    }

    public void setSmallTypeName(String smallTypeName) {
        this.smallTypeName = smallTypeName;
    }

    public String getEffectName() {
        return effectName;
    }

    public void setEffectName(String effectName) {
        this.effectName = effectName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlanUseId() {
        return planUseId;
    }

    public void setPlanUseId(String planUseId) {
        this.planUseId = planUseId;
    }

    @Override
    public String toString() {
        return "ElPlanUseDTO{" +
                "planUseId='" + planUseId + '\'' +
                ", teachCode='" + teachCode + '\'' +
                ", equipmentName='" + equipmentName + '\'' +
                ", modelName='" + modelName + '\'' +
                ", factoryName='" + factoryName + '\'' +
                ", mainParamName='" + mainParamName + '\'' +
                ", mainParamValue='" + mainParamValue + '\'' +
                ", equipmentCode='" + equipmentCode + '\'' +
                ", assetCode='" + assetCode + '\'' +
                ", bigTypeName='" + bigTypeName + '\'' +
                ", middleTypeName='" + middleTypeName + '\'' +
                ", smallTypeName='" + smallTypeName + '\'' +
                ", effectName='" + effectName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
