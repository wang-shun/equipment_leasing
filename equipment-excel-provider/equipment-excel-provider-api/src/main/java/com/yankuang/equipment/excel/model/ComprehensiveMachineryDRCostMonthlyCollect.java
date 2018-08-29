package com.yankuang.equipment.excel.model;

import lombok.Data;

/**
 * @author xhh
 * @date 2018/8/25 8:53
 * 综机折旧修理费月报（汇总）全年汇总.
 */
@Data
public class ComprehensiveMachineryDRCostMonthlyCollect {

    private String org_name;//矿别

    private double annual_plan_indicator;//年度计划指标

    private double monthly_plan_receivables;//每月计划应收

    private double total_plan_receivables;//1-12月计划应收

    private double january_repairs_cost;//一月修理费

    private double  february_repairs_cost;//二月修理费

    private double  march_repairs_cost;//三月修理费

    private double  april_repairs_cost;//四月修理费

    private double  may_repairs_cost;//五月修理费

    private double  june_repairs_cost;//六月修理费

    private double  july_repairs_cost;//七月修理费

    private double  august_repairs_cost;//八月修理费

    private double  sep_repairs_cost;//九月修理费

    private double  oct_repairs_cost;//十月修理费

    private double  nov_repairs_cost;//十一月修理费

    private double  dec_repairs_cost;//十二月修理费

}
