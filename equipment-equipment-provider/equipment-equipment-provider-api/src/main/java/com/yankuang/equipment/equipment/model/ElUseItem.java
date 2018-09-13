package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

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

    private Long equipmentPosition;

    private String remarks;

    private String status;

    private String code;

    private Long planUseId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date useAt;

    private Byte isUse;

    private SbEquipmentT sbEquipmentT;

    private SbEquipmentZ sbEquipmentZ;

    private String sbName;

    private String name;

    private Double costA1;

    private Long sign;

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

    public Long getEquipmentPosition() {
        return equipmentPosition;
    }

    public void setEquipmentPosition(Long equipmentPosition) {
        this.equipmentPosition = equipmentPosition;
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

    public SbEquipmentT getSbEquipmentT() {
        return sbEquipmentT;
    }

    public void setSbEquipmentT(SbEquipmentT sbEquipmentT) {
        this.sbEquipmentT = sbEquipmentT;
    }

    public Long getPlanUseId() {
        return planUseId;
    }

    public void setPlanUseId(Long planUseId) {
        this.planUseId = planUseId;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    public Date getUseAt() {
        return useAt;
    }

    public void setUseAt(Date useAt) {
        this.useAt = useAt;
    }

    public Byte getIsUse() {
        return isUse;
    }

    public void setIsUse(Byte isUse) {
        this.isUse = isUse;
    }

    public Long getSign() {
        return sign;
    }

    public void setSign(Long sign) {
        this.sign = sign;
    }
}