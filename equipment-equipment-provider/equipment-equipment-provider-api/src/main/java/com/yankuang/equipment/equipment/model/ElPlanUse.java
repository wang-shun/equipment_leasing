package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Data
@Entity
@Table(name = "el_plan_use")
public class ElPlanUse implements Serializable {
    private Long id;

    private String centerYear;

    private String centerMonth;

    private String planType;

    private String planId;

    private String planItemId;

    private Long equipmentId;

    private String sorting;

    private String status;

    private Byte isDel;

    private String remarks;

    private Long createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createAt;

    private Long updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateAt;

    private Long version;

    private String equipmentType;

    private String positionId;

    private String smallTypeCode;

    private String middleTypeCode;

    private String bigTypeCode;

    private String equipmentCode;

    private String equipmentName;

    private String equipmentModel;

    private String effectCode;

    private String equipmentFactory;

    private String smallTypeName;

    private String middleTypeName;

    private String bigTypeName;

    private String effectName;

    private Integer equipmentNum;

    private String equipmentSpecification;

    private String equipmentParamName;

    private String equipmentParamValue;

    private String techCode;

    private String assetCode;

    private String positionCode;
}