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
    private String equipmentParamValue;

    private String itemRemarks;

    private String bigTypeCode;

    private String middleTypeCode;

    private String smallTypeCode;

    private String specificationCode;

}