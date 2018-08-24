package com.yankuang.equipment.excel.model;

import lombok.Data;

/**
 * @author xhh
 * @date 2018/8/20 13:58
 * 综机折旧修理费表
 */
@Data
public class ComprehensiveMachineryDRCost {

    private String item_position;//使用单位

    private String using_site;//使用地点

    private String form_number;//申请单编号

    private int detailed_list;//附清单（张）

    private double depreciation_repair_charge;//综机折旧大修费

    private String remark;//备注

    private String total;//总计

}
