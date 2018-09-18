package com.yankuang.equipment.equipment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Data
@Entity
@Table(name = "el_fee_middle_t")
public class ElFeeMiddleT {
    private Long id;

    private String code;

    private Byte status;

    private String positionName;

    private String positionCode;

    private Date exportAt;

    private String middleCode;

    private String middleName;

    private Long equipmentNum;

    private Long useNum;

    private Double costA1;

    private Double costA2;

    private Double costA3;

    private Double totalFee;

    private String remarks;

    private Long approveBy;

    private Long positionBy;

    private Date updateAt;

    private Long updateBy;

    private Date createAt;

    private Long createBy;

    private Long sorting;

    private Long version;

    private String reportYear;

    private String reportMonth;

}