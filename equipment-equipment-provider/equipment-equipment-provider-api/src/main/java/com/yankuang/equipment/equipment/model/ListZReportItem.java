package com.yankuang.equipment.equipment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Data
@Table(name = "el_zEquipmentList_report_item")
public class ListZReportItem implements Serializable {
    private Long id;

    private String name;

    private String equipmentModel;

    private String equipmentUnit;

    private Integer equipmentNum;

    private Double costA1;

    private Long day;

    private Double sum;

    private String remark;

    private Long reportId;

    private String equipmentCode;
}
