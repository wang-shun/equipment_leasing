package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Data
@Entity
@Table(name = "el_surface_param")
public class ElSurfaceParam implements Serializable {
    private Long id;

    private String code;

    private String sbPositionCode;
    private String sbPositionName;

    private Double surfaceLength;

    private Double surfaceTrend;

    private Double surfaceHigh;

    private Double surfaceReserves;

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