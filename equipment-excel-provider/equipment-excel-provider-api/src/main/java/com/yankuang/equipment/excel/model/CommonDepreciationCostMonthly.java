package com.yankuang.equipment.excel.model;

import lombok.Data;

/**
 * @author xhh
 * @date 2018/8/17
 *
 * 通用设备折旧修理费月报（跨矿）
 */
@Data
public class CommonDepreciationCostMonthly {

    private String equipment_middle_type;//设备中类

//    private String item_position;//矿处单位

    private String subtotal;//小计

    private String total;//合计

    private String order;//序号

    private double nantun;//南屯

    private double xinglongzhuang;//兴隆庄

    private double baodian;//鲍店

    private double dongtan;//东滩

    private double jier;//济二

    private double jisan;//济三

    private double yangcun;//杨村





}
