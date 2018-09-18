package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.ZjDepreciationCostReportItemMapper;
import com.yankuang.equipment.equipment.mapper.ZjSbUseItemMapper;
import com.yankuang.equipment.equipment.model.ZjDepreciationCostReportItem;
import com.yankuang.equipment.equipment.model.ZjSbUseItem;
import com.yankuang.equipment.equipment.service.ZjDepreciationCostReportItemService;
import com.yankuang.equipment.equipment.service.ZjSbUseItemService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RpcProvider
public class ZjDepCostReportItemServiceImpl implements ZjDepreciationCostReportItemService {

    @Autowired
    ZjDepreciationCostReportItemMapper zjDepreciationCostReportItemMapper;


    /**
     * @method 创建综机折旧修理费
     * @param zjDepreciationCostReportItem
     * @return
     */
    public Boolean create(ZjDepreciationCostReportItem zjDepreciationCostReportItem){
        return zjDepreciationCostReportItemMapper.create(zjDepreciationCostReportItem) >=0;
    }




}
