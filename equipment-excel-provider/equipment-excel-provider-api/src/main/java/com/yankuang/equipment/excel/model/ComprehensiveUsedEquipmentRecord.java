package com.yankuang.equipment.excel.model;

import lombok.Data;

/**
 * @author xhh
 * @date 2018/8/21 9:05
 *  综机设备使用清单
 */

@Data
public class ComprehensiveUsedEquipmentRecord {

    private String item_position;//使用单位

    private String equipment_name;//名称

    private String equipment_specification;//规格型号

    private String unit;// 单位

    private int equipment_num;//数量

    private double daily_billing_standard;//日计费

    private int charge_days;//收费天数

    private double money;//金额

    private String number;//编号

    private String remark;//备注


}
