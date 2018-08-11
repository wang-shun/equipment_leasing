package com.yankuang.equipment.equipment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name = "el_plan_item")
public class ElPlanItem implements Serializable {
    private String itemId;

    private String planId;

    private String backup1;

    private String equipmentName;

    @NotNull(message = "设备数量不得为空")
    private Integer equipmentNum;

    @NotNull(message = "单位不得为空")
    private String itemPosition;

    @NotNull(message = "功能位置不得为空")
    private String itemEffect;

    @NotNull(message = "设备大类不得为空")
    private String equipmentBigType;

    @NotNull(message = "设备中类不得为空")
    private String equipmentMiddleType;

    @NotNull(message = "设备小类不得为空")
    private String equipmentSmallType;

    private String equipmentSpecification;

    @NotNull(message = "主要技术参数名称不得为空")
    private String equipmentParamName;

    @NotNull(message = "主要技术参数值不得为空")
    private Integer equipmentParamValue;

    private String itemRemarks;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId == null ? null : itemId.trim();
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId == null ? null : planId.trim();
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName == null ? null : equipmentName.trim();
    }

    public Integer getEquipmentNum() {
        return equipmentNum;
    }

    public void setEquipmentNum(Integer equipmentNum) {
        this.equipmentNum = equipmentNum;
    }

    public String getItemPosition() {
        return itemPosition;
    }

    public void setItemPosition(String itemPosition) {
        this.itemPosition = itemPosition == null ? null : itemPosition.trim();
    }

    public String getItemEffect() {
        return itemEffect;
    }

    public void setItemEffect(String itemEffect) {
        this.itemEffect = itemEffect == null ? null : itemEffect.trim();
    }

    public String getEquipmentBigType() {
        return equipmentBigType;
    }

    public void setEquipmentBigType(String equipmentBigType) {
        this.equipmentBigType = equipmentBigType == null ? null : equipmentBigType.trim();
    }

    public String getEquipmentMiddleType() {
        return equipmentMiddleType;
    }

    public void setEquipmentMiddleType(String equipmentMiddleType) {
        this.equipmentMiddleType = equipmentMiddleType == null ? null : equipmentMiddleType.trim();
    }

    public String getEquipmentSmallType() {
        return equipmentSmallType;
    }

    public void setEquipmentSmallType(String equipmentSmallType) {
        this.equipmentSmallType = equipmentSmallType == null ? null : equipmentSmallType.trim();
    }

    public String getEquipmentSpecification() {
        return equipmentSpecification;
    }

    public void setEquipmentSpecification(String equipmentSpecification) {
        this.equipmentSpecification = equipmentSpecification == null ? null : equipmentSpecification.trim();
    }

    public String getEquipmentParamName() {
        return equipmentParamName;
    }

    public void setEquipmentParamName(String equipmentParamName) {
        this.equipmentParamName = equipmentParamName == null ? null : equipmentParamName.trim();
    }

    public Integer getEquipmentParamValue() {
        return equipmentParamValue;
    }

    public void setEquipmentParamValue(Integer equipmentParamValue) {
        this.equipmentParamValue = equipmentParamValue;
    }

    public String getItemRemarks() {
        return itemRemarks;
    }

    public void setItemRemarks(String itemRemarks) {
        this.itemRemarks = itemRemarks == null ? null : itemRemarks.trim();
    }
}