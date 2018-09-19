package com.yankuang.equipment.equipment.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: xhh
 * @Date: 2018-09-14 16:17
 * @Version 综机折旧修理费表
 */
@Data
@Entity
@Table(name = "report_zj_depreciation_cost")
public class ZjDepreciationCostReport implements Serializable {

    private Long id;

    private String assetComp;//资产公司

    private String yearMonthTime;//月份（时间年月）

    private String remark;//备注

    private String reportName;//报表名称

    private Date createAt;

    private Date updateAt;

    List<ZjDepreciationCostReportItem> zjDepreciationCostReportItems;

    private List<ZjDepreciationCostReportItem> zjDepreciationCostReportItemsHome;//存放本部数据

    private List<ZjDepreciationCostReportItem> zjDepreciationCostReportItemsExternal;//存放外部数据

    private List<ZjDepreciationCostReportItemSumHome> zjDepreciationCostReportItemSumHomes;//存放本部合计、小计

    private List<ZjDepreciationCostReportItemSumExternal> zjDepreciationCostReportItemSumExternals;//存放本部合计、小计

    private List<ZjDepreciationCostReportItemAddUpSum> zjDepreciationCostReportItemAddUpSums;//存放本部和外部累计


}