package com.yankuang.equipment.equipment.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: xhh
 * @Date: 2018-09-14 16:17
 * @Version 综机折旧修理费累计
 */
@Data
public class ZjDepreciationCostReportItemAddUpSum implements Serializable {

    private Double moreDeptAddUpSum;//合计的累计

    private Double addUpSum01;//1月累计

    private Double addUpSum02;//2月累计

    private Double addUpSum03;//3月累计

    private Double addUpSum04;//4月累计

    private Double addUpSum05;//5月累计

    private Double addUpSum06;//6月累计

    private Double addUpSum07;//7月累计

    private Double addUpSum08;//8月累计

    private Double addUpSum09;//9月累计

    private Double addUpSum10;//10月累计

    private Double addUpSum11;//11月累计

    private Double addUpSum12;//12月累计



}
