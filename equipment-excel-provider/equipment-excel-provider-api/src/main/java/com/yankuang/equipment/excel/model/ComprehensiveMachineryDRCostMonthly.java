package com.yankuang.equipment.excel.model;

import lombok.Data;

/**
 * @author xhh
 * @date 2018/8/24 14:31
 * 综机折旧修理费月报（汇总）月度明细.
 */
@Data
public class ComprehensiveMachineryDRCostMonthly {

    private String org_name;//矿别

    private double coal_annual_plan_indicator;//上报煤业版，年度计划指标

    private double yh_annual_plan_indicator;//HY版，年度计划指标

    private double first_half_indicator;//上半年指标，按煤业版的一半

    private double first_half_hy_predictor;//上半年HY预测指标

    private double onetosix_real_income;//1-6月实收

    private double onetofive_real_income;//1-5月实收

    private double six_real_income;//6月实收

    private String remark;//6月实收

}
