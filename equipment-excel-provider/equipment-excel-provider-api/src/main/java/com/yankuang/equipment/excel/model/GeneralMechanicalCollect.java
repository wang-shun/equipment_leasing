package com.yankuang.equipment.excel.model;

import com.yankuang.equipment.excel.service.ExcelAnnotation;
import lombok.Data;

/**
 * @author xhh
 * @date 2018/8/15 16:32
 *  矿处通用机电租赁费用汇总表.
 */
@Data
public class GeneralMechanicalCollect {

    @ExcelAnnotation(id=1,name={"矿处单位"},width = 5000)
    private String item_position;//矿处单位

    @ExcelAnnotation(id=2,name={"设备名称(中类)"},width = 5000)
    private String equipment_middle_type;//设备中类

    @ExcelAnnotation(id=3,name={"在籍数量"},width = 5000)
    private String equipment_num;//在籍数量

    @ExcelAnnotation(id=4,name={"在用数量"},width = 5000)
    private String quantity_used;//在用数量

    @ExcelAnnotation(id=5,name={"租赁费"},width = 5000)
    private double rental_fee;//租赁费

    @ExcelAnnotation(id=6,name={"费用调整"},width = 5000)
    private double adjustment_expenses;//费用调整

    @ExcelAnnotation(id=7,name={"合计"},width = 5000)
    private double total;//合计

    @ExcelAnnotation(id=8,name={"备注"},width = 5000)
    private String remark;//备注

}
