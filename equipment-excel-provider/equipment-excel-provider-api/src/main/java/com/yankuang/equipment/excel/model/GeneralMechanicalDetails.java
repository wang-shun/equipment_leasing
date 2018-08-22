package com.yankuang.equipment.excel.model;

import com.yankuang.equipment.excel.service.ExcelAnnotation;
import lombok.Data;

/**
 * @author xhh
 * @date 2018/8/15 10:28
 * 矿处通用机电租赁费用明细表
 */
@Data
public class GeneralMechanicalDetails {

    @ExcelAnnotation(id=1,name={"矿处单位"},width = 5000)
    private String item_position;//矿处单位

    @ExcelAnnotation(id=2,name={"设备中类"},width = 5000)
    private String equipment_middle_type;//设备中类

    @ExcelAnnotation(id=3,name={"设备小类"},width = 5000)
    private String equipment_small_type;//设备小类

    @ExcelAnnotation(id=4,name={"设备识别码"},width = 5000)
    private String code;//设备识别码

    @ExcelAnnotation(id=5,name={"技术标识号"},width = 5000)
    private String tech_code;//技术标识号

    @ExcelAnnotation(id=6,name={"规格型号"},width = 5000)
    private String equipment_specification;//规格型号

    @ExcelAnnotation(id=7,name={"功能位置"},width = 5000)
    private String item_effect;//功能位置

    @ExcelAnnotation(id=8,name={"租赁结束天数"},width = 5000)
    private String lease_end_day;//租赁结束天数

    @ExcelAnnotation(id=9,name={"租赁价格"},width = 5000)
    private double lease_price;//租赁价格

    @ExcelAnnotation(id=10,name={"调整金额"},width = 5000)
    private double readjust_prices;//调整金额

    @ExcelAnnotation(id=11,name={"总计"},width = 5000)
    private double total;//总计

    @ExcelAnnotation(id=12,name={"备注"},width = 6000)
    private String remark;//备注




}
