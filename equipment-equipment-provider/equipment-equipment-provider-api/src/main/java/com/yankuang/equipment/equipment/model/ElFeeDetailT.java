package com.yankuang.equipment.equipment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "el_fee_detail_t")
public class ElFeeDetailT {
    private Long id;

    private String code;

    private Long status;

    private Date exportAt;

    private Long equipmentId;

    private String positionName;

    private String positionCode;

    private String middleTypeName;

    private String middleTypeCode;

    private String smallTypeName;

    private String smallTypeCode;

    private String equipmentCode;

    private String techCode;

    private String modelCode;

    private String modelName;

    private String effectName;

    private String effectCode;

    private Long elDays;

    private Double costA1;

    private Double costA3;

    private Double totalFee;

    private Long updateBy;

    private Long version;

    private Date updateAt;

    private Long createBy;

    private Date createAt;

    private String remarks;

    private Long sorting;

    private String reportYear;

    private String reportMonth;

}