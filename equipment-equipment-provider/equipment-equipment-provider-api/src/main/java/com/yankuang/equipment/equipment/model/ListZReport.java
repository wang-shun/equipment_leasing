package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "el_zEquipmentList_report")
public class ListZReport implements Serializable {
    private Long id;

    private String useDeptName;

    private String number;

    private String createExcelName;

    private String statusName;

    private String sureName;

    private Double sum;

    private String useDeptCode;

    private String createExcelCode;

    private String statusCode;

    private String sureCode;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date useAt;

    private String equipmentPosition;

    private String useYear;

    private String useMonth;

    private Byte type;

    private String zc;
}
