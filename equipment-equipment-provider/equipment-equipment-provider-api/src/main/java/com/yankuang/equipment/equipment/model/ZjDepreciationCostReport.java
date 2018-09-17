package com.yankuang.equipment.equipment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author: xhh
 * @Date: 2018-09-14 16:17
 * @Version 综机折旧修理费表
 */
@Data
@Entity
@Table(name = "report_zj_depreciation_cost")
public class ZjDepreciationCostReport {

    private Long id;

    private String deptName;//部门名称

    private String deptCode;//部门编码

    private String deptType;//部门类型，分本部和外部

    private String assetComp;//资产公司

    private String yearMonth;//月份（时间年月）

    private Double subtotal;//小计

    private Double accumulativeTotal;//累计

    private Double total;//合计

    private Double januaryRepairsCost;//一月修理费

    private Double  februaryRepairsCost;//二月修理费

    private Double  marchRepairsCost;//三月修理费

    private Double  aprilRepairsCost;//四月修理费

    private Double  mayRepairsCost;//五月修理费

    private Double  juneRepairsCost;//六月修理费

    private Double  julyRepairsCost;//七月修理费

    private Double  augustRepairsCost;//八月修理费

    private Double  sepRepairsCost;//九月修理费

    private Double  octRepairsCost;//十月修理费

    private Double  novRepairsCost;//十一月修理费

    private Double  decRepairsCost;//十二月修理费

    private String createAt;

    private String updateAt;

}
