package com.yankuang.equipment.equipment.model;

import lombok.Data;

/**
 * @Author: xhh
 * @Date: 2018-09-14 16:17
 * @Version 综机折旧修理费表
 */
@Data
public class ZjDeprecitionReport {

    private String orgName;//矿别

    private double subtotal;//小计

    private double accumulativeTotal;//累计

    private double total;//合计

    private double januaryRepairsCost;//一月修理费

    private double  februaryRepairsCost;//二月修理费

    private double  marchRepairsCost;//三月修理费

    private double  aprilRepairsCost;//四月修理费

    private double  mayRepairsCost;//五月修理费

    private double  juneRepairsCost;//六月修理费

    private double  julyRepairsCost;//七月修理费

    private double  augustRepairsCost;//八月修理费

    private double  sepRepairsCost;//九月修理费

    private double  octRepairsCost;//十月修理费

    private double  novRepairsCost;//十一月修理费

    private double  decRepairsCost;//十二月修理费

}
