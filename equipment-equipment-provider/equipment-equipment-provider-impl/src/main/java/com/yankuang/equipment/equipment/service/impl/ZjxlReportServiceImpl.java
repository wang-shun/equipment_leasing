package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.ZEquipmentListReportMapper;
import com.yankuang.equipment.equipment.mapper.ZjxlReportMapper;
import com.yankuang.equipment.equipment.model.DtkList;
import com.yankuang.equipment.equipment.model.ListZReport;
import com.yankuang.equipment.equipment.model.ListZReportItem;
import com.yankuang.equipment.equipment.model.ZjxlReport;
import com.yankuang.equipment.equipment.service.ZjxlReportService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class ZjxlReportServiceImpl implements ZjxlReportService {

    @Autowired
    ZjxlReportMapper zjxlReportMapper;

    @Autowired
    ZEquipmentListReportMapper zEquipmentListReportMapper;

    public Boolean create(ZjxlReport zjxlReport){
        if (zjxlReportMapper.create(zjxlReport) <=0 ){
            return false;
        }

        return true;
    }

    public List<ZjxlReport> list(ZjxlReport zjxlReport){
        List<ZjxlReport> zjxlReports = zjxlReportMapper.list(zjxlReport);
        if (zjxlReports != null && zjxlReports.size() > 0 ){
            return  zjxlReports;
        }
        ListZReport listZReport = new ListZReport();
        listZReport.setUseYear(zjxlReport.getZjxlYear());
        listZReport.setUseMonth(zjxlReport.getZjxlMonth());
        listZReport.setEquipmentPosition(zjxlReport.getUsePosition());

        List<ListZReport> listZReports = zEquipmentListReportMapper.list(listZReport);
        for (ListZReport listZReport1:listZReports){
            zjxlReport.setUsePosition(listZReport1.getEquipmentPosition());
            zjxlReport.setZjxlFee(listZReport1.getSum());
            zjxlReports.add(zjxlReport);
        }

        return zjxlReports;
    }
}
