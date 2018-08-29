package com.yankuang.equipment.excel.model;

import lombok.Data;

/**
 * @author xhh
 * @date 2018/8/16 16:49
 * 通用设备折旧修理费确认单。
 */
@Data
public class CommonDepreciationCost {

    private String item_position;//使用单位

    private String quantity_used;//在用数量

    private double monthly_fee;//月度费用

    private double adjustment_expenses;//费用调整

    private double month_total;//月度合计

    private String usage_days;//使用天数

    private String remark;//备注

    private String month;//月份

}
