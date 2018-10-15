package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Data
@Entity
@Table(name = "el_equipment_group")
public class ElEquipmentGroup implements Serializable {
    private Long id;

    private String code;

    private Byte status;

    private Long planId;

    private String planCode;

    private String surfaceCode;

    private Long surfaceId;

    private String sbTypeCode;

    private String sbTypeName;

    private String sbModelCode;

    private String sbModelName;

    private String paramName;

    private String paramValue;

    private Long sbNum;

    private String sbUnit;

    private String remarks;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;

    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;

    private Long sorting;

    private Long version;

}