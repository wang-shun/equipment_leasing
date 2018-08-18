package com.yankuang.equipment.equipment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Entity
@Table(name = "el_use_item")
public class ElUseItem implements Serializable{
    private Long itemId;

    private Long useId;

    private Long equipmentId;

    private String mainParamName;

    private String mainParamValue;

    private Integer equipmentNum;

    private String equipmentEffect;

    private String equipmentPosition;

    private String remarks;

    private String status;

    private String code;

    private String backup1;

    private String backup2;

    private String backup3;

    private SbEquipmentT sbEquipmentT;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getUseId() {
        return useId;
    }

    public void setUseId(Long useId) {
        this.useId = useId;
    }

    public Long getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Long equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getMainParamName() {
        return mainParamName;
    }

    public void setMainParamName(String mainParamName) {
        this.mainParamName = mainParamName == null ? null : mainParamName.trim();
    }

    public String getMainParamValue() {
        return mainParamValue;
    }

    public void setMainParamValue(String mainParamValue) {
        this.mainParamValue = mainParamValue == null ? null : mainParamValue.trim();
    }

    public Integer getEquipmentNum() {
        return equipmentNum;
    }

    public void setEquipmentNum(Integer equipmentNum) {
        this.equipmentNum = equipmentNum;
    }

    public String getEquipmentEffect() {
        return equipmentEffect;
    }

    public void setEquipmentEffect(String equipmentEffect) {
        this.equipmentEffect = equipmentEffect == null ? null : equipmentEffect.trim();
    }

    public String getEquipmentPosition() {
        return equipmentPosition;
    }

    public void setEquipmentPosition(String equipmentPosition) {
        this.equipmentPosition = equipmentPosition == null ? null : equipmentPosition.trim();
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getBackup1() {
        return backup1;
    }

    public void setBackup1(String backup1) {
        this.backup1 = backup1 == null ? null : backup1.trim();
    }

    public String getBackup2() {
        return backup2;
    }

    public void setBackup2(String backup2) {
        this.backup2 = backup2 == null ? null : backup2.trim();
    }

    public String getBackup3() {
        return backup3;
    }

    public void setBackup3(String backup3) {
        this.backup3 = backup3 == null ? null : backup3.trim();
    }

    public SbEquipmentT getSbEquipmentT() {
        return sbEquipmentT;
    }

    public void setSbEquipmentT(SbEquipmentT sbEquipmentT) {
        this.sbEquipmentT = sbEquipmentT;
    }
}