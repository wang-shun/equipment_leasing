package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Data
@Entity
@Table(name = "el_fee_position_t")
public class ElFeePositionT implements Serializable {
    private Long id;

    private Byte status; // 状态

    private String reportYear;

    private String reportMonth;

    private String middleName;

    private String middleCode;

    private String positionMap;

    private Double totalFee;

    private Long version;

    private String remarks;

    //@JsonIgnore
    private Map<String, Double> depositMap;

    private Long createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;

    private Long updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date exportAt;

    @JsonIgnore
    private String code;

    @JsonIgnore
    private Long sorting;

    @JsonIgnore
    private String exportAtStr;

    @JsonIgnore
    private Integer pageNum; // 页码

    @JsonIgnore
    private Integer pageSize; // 每页记录数
}