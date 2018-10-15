package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "el_produce_surface")
public class ElProduceSurface implements Serializable {
    private Long id;

    private String code;

    private Byte status;

    private Long planId;

    private String planCode;

    private String positionCode;

    private String partitionName;

    private String effectCode;

    private String effectName;

    private String readyTime;

    private String produceTime;

    private String revokeTime;

    private String bankTime;

    private String switchSurfaceCode;

    private String sbSource;

    private String remarks;

    private Long version;

    private Long sorting;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;

    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;

    private List<ElEquipmentGroup> elEquipmentGroups;

}