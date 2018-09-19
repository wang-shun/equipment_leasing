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
@Table(name = "el_use_item")
public class DtkList implements Serializable {

    private Long id;

    private String sbName;

    private Integer equipmentNum;

    private Double costA1;

    private String remarks;

    private String equipmentModel;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date useAt;

    private String deptName;

    private String equipmentEffect;

    private Double costA1Fee;

    private Long equipmentId;

    private Long sign;

    private Long day;

    private String unit;

    private Integer centerMonth;

    private Integer centerYear;

    private Long useId;

    private String assetUnit;

    private String sbCode;

    private String deptCode;

    private String useDeptName;

    private String number;

    private String createExcelName;

    private String statusName;

    private String sureName;

    private String useDeptCode;

    private String createExcelCode;

    private String statusCode;

    private String sureCode;

    private String equipmentPosition;

    private String useYear;

    private String useMonth;

    private Byte type;

    private String useAtString;

    private Double sum;//求总和

    private Byte kb;//用来判断矿别

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;

    private List<ListZReportItem> listZReportItems;

    private ListZReportItem listZReportItem;
}
