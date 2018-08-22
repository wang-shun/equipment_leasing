package com.yankuang.equipment.equipment.model;

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

    private Integer centerYear;

    private Integer centerMonth;

    private String planType;

    private String planId;

    private String planItemId;

    private Long equipmentId;

    private String sorting;

    private String status;

    private Byte isDel;

    private String remarks;

    private Long createBy;

    private Date createAt;

    private Long updateBy;

    private Date updateAt;

    private Long version;

    private String equipmentType;

    private Long positionId;

    private String smallTypeCode;

    private String middleTypeCode;

    private String bigTypeCode;

    private String equipmentCode;

    private String equipmentName;

    private String equipmentModel;

    private String effectCode;

    private String equipmentFactory;
}