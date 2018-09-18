package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.equipment.mapper.ZjDepreciationCostReportItemMapper;
import com.yankuang.equipment.equipment.mapper.ZjDepreciationCostReportMapper;
import com.yankuang.equipment.equipment.mapper.ZjSbUseItemMapper;
import com.yankuang.equipment.equipment.model.ZjDepreciationCostReport;
import com.yankuang.equipment.equipment.model.ZjDepreciationCostReportItem;
import com.yankuang.equipment.equipment.model.ZjSbUseItem;
import com.yankuang.equipment.equipment.model.ZjSbUseItemSign;
import com.yankuang.equipment.equipment.service.ZjDepreciationCostReportService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RpcProvider
public class ZjDepCostReportServiceImpl implements ZjDepreciationCostReportService {

    @Autowired
    ZjDepreciationCostReportMapper zjDepreciationCostReportMapper;

    @Autowired
    ZjDepreciationCostReportItemMapper zjDepreciationCostReportItemMapper;


    /**
     * @method 创建综机折旧修理费
     * @param zjDepreciationCostReport
     * @return
     */
    public Boolean create(ZjDepreciationCostReport zjDepreciationCostReport){

        List<ZjDepreciationCostReportItem> zjDepreciationCostReportItems = zjDepreciationCostReport.getZjDepreciationCostReportItems();

        ZjDepreciationCostReport zjDepreciationCost = new ZjDepreciationCostReport();
        zjDepreciationCost.setAssetComp(zjDepreciationCostReport.getAssetComp());
        zjDepreciationCost.setReportName(zjDepreciationCostReport.getReportName());
        zjDepreciationCost.setRemark(zjDepreciationCostReport.getRemark());
        zjDepreciationCost.setYearMonthTime(zjDepreciationCostReport.getYearMonthTime());
        zjDepreciationCost.setCreateAt(new Date());

        zjDepreciationCostReportMapper.create(zjDepreciationCost);
        if (zjDepreciationCostReportItems.size()==0 || zjDepreciationCostReportItems == null){
            return false;
        }
        for (ZjDepreciationCostReportItem zjDepCostItem:zjDepreciationCostReportItems){
            zjDepCostItem.setReportId(zjDepreciationCost.getId());//每个交接单里实体类的id是一样的
            zjDepreciationCostReportItemMapper.create(zjDepCostItem);
        }

        return true;
    }




}
