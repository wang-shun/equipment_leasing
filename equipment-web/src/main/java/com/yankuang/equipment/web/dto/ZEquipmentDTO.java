package com.yankuang.equipment.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.yankuang.equipment.equipment.model.ListZReportItem;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ZEquipmentDTO implements Serializable {
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

    private String useAtString;

    private List<ListZReportItem> listZReportItems;

    private ListZReportItem listZReportItem;

    private String zc;

    private Byte status;
}
