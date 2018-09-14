package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.ZEquipmentListReportItemMapper;
import com.yankuang.equipment.equipment.mapper.ZEquipmentListReportMapper;
import com.yankuang.equipment.equipment.model.ListZReport;
import com.yankuang.equipment.equipment.model.ListZReportItem;
import com.yankuang.equipment.equipment.service.ZEquipmentReportService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RpcProvider
@Service
public class ZEquipmentReportServiceImpl implements ZEquipmentReportService {

    @Autowired
    private ZEquipmentListReportMapper zEquipmentListReportMapper;

    @Autowired
    private ZEquipmentListReportItemMapper zEquipmentListReportItemMapper;

    @Transactional
    public Boolean create(ListZReport listZReport,List<ListZReportItem> listZReportItems){
        
        Integer num = zEquipmentListReportMapper.create(listZReport);
        if (num <= 0){
            return false;
        }

        //循环添加明细表记录
        for (ListZReportItem listZReportItem:listZReportItems){
            listZReportItem.setReportId(listZReport.getId());
            Integer numItem = zEquipmentListReportItemMapper.create(listZReportItem);
            if (numItem <= 0 ){
                return false;
            }
        }

        return true;
    }
}
