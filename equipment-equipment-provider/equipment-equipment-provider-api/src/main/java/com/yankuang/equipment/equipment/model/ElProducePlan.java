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
@Table(name = "el_produce_plan")
public class ElProducePlan implements Serializable {
    private Long id;

    private String planYear;

    private String code;

    private Byte status;

    private String assetCode;

    private String startTime;

    private String endTime;

    private String planPosition;

    private String planPositionName;

    private String remarks;

    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;

    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;

    private Long sorting;

    private Long version;

    private List<ElProduceSurface> elProduceSurfaces;

}