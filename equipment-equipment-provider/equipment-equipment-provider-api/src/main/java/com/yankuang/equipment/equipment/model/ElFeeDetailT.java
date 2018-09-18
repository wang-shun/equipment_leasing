package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "el_fee_detail_t")
public class ElFeeDetailT implements Serializable {
    private Long id;

    private String code;

    private Long status;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;

    private Long createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;

    private String remarks;

    private Long sorting;

    private String reportYear;

    private String reportMonth;

    private Integer pageNum;

    private Integer pageSize;

    private String exportAtStr;

}