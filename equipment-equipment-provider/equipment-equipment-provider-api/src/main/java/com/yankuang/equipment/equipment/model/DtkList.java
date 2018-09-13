package com.yankuang.equipment.equipment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "el_use_item")
public class DtkList implements Serializable {

    private String sbName;

    private Integer equipmentNum;

    private Double costA1;

    private String remarks;

    private String equipmentModel;

    private Date useAt;

    private String deptName;

    private String equipmentEffect;

    private Double costA1Fee;

    private Long equipmentId;

    private Long sign;

    private Long day;

    private String unit;
}
