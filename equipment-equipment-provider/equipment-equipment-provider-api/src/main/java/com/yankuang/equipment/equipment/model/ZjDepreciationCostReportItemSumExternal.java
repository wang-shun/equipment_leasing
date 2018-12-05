package com.yankuang.equipment.equipment.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xhh
 * @Date: 2018-09-14 16:17
 * @Version 综机折旧修理费表
 */
@Data
public class ZjDepreciationCostReportItemSumExternal implements Serializable {

    private Double moreDeptSum;//小计

    private Double month01xiaoji;
    private Double month02xiaoji;
    private Double month03xiaoji;
    private Double month04xiaoji;
    private Double month05xiaoji;
    private Double month06xiaoji;
    private Double month07xiaoji;
    private Double month08xiaoji;
    private Double month09xiaoji;
    private Double month10xiaoji;
    private Double month11xiaoji;
    private Double month12xiaoji;


}
