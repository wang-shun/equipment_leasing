package com.yankuang.equipment.web.dto;

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

    private Date useAt;

    private String equipmentPosition;

    private String useYear;

    private String useMonth;

    private Byte type;

    private String useAtString;

    private List<ListZReportItem> listZReportItems;
}
