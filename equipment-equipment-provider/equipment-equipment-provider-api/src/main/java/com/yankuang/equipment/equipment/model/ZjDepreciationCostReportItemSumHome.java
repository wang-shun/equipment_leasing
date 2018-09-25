package com.yankuang.equipment.equipment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Author: xhh
 * @Date: 2018-09-14 16:17
 * @Version 综机折旧修理费表
 */
@Data
public class ZjDepreciationCostReportItemSumHome implements Serializable {

    private Double moreDeptSum;//本部矿合计的小计
    private Double month01xiaoji;//1月小计
    private Double month02xiaoji;//2月小计
    private Double month03xiaoji;//3月小计
    private Double month04xiaoji;//4月小计
    private Double month05xiaoji;//5月小计
    private Double month06xiaoji;//6月小计
    private Double month07xiaoji;//7月小计
    private Double month08xiaoji;//8月小计
    private Double month09xiaoji;//9月小计
    private Double month10xiaoji;//10月小计
    private Double month11xiaoji;//11月小计
    private Double month12xiaoji;//12月小计


}
