package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.ZEquipmentListReportMapper;
import com.yankuang.equipment.equipment.mapper.ZNewReportMapper;
import com.yankuang.equipment.equipment.model.ListZReport;
import com.yankuang.equipment.equipment.model.ZNewReport;
import com.yankuang.equipment.equipment.service.ZNewReportService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class ZNewReportServiceImpl implements ZNewReportService {

    @Autowired
    ZNewReportMapper zNewReportMapper;
    @Autowired
    ZEquipmentListReportMapper zEquipmentListReportMapper;

    public boolean create(ZNewReport zNewReport){

        if (zNewReportMapper.create(zNewReport) > 0){
            return true;
        }

        return false;
    }

    public List<ListZReport> select(ZNewReport zNewReport){

        ListZReport listZReport = new ListZReport();
        //转换实体类
        BeanUtils.copyProperties(zNewReport,listZReport);

        List<ListZReport> listZReports = zEquipmentListReportMapper.list(listZReport);

        return listZReports;
    }

    public List<ZNewReport> list(ZNewReport zNewReport){
        return zNewReportMapper.list(zNewReport);
    }
}
