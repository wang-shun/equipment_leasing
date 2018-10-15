package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Data
@Entity
@Table(name = "el_equipment_group_config")
public class ElEquipmentGroupConfig implements Serializable {
    private Long id;

    private String configYear;

    private String assetCode;

    private String bigTypeCode;
    private String bigTypeName;

    private String middleTypeCode;
    private String middleTypeName;

    private String smallTypeCode;
    private String smallTypeName;

    private String code;

    private Byte status;

    private Long version;

    private Long sorting;

    private String remarks;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;

    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;

}