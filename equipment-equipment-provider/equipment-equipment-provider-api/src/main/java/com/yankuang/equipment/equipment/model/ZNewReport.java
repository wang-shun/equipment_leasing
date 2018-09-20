package com.yankuang.equipment.equipment.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table()
@Data
@Entity
public class ZNewReport implements Serializable {
    private Long id;

    private String name;//设备名称

    private String equipmentModel;//设备规格型号

    private String equipmentUnit;//设备计量单位

    private Integer equipmentNum;//设备数量

    private Double costA1;//设备日租赁费

    private Long day;//租赁天数

    private Double sum;//金额

    private String remark;//备注

    private String equipmentCode;//设备code

    private String feeDay;//收费期限

    private String useDeptName;//使用单位名称

    private String number;//编号

    private String createExcelName;//指标人

    private String statusName;//审核人

    private String sureName;//矿方确认人

    private Double feeSum;//合计金额

    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private Date useAt;//领用时间

    private String equipmentPosition;//功能位置

    private String useYear;//领用年份

    private String useMonth;//领用月份

    private Byte type;//矿别

    private String useDeptCode;//使用单位Code
}
