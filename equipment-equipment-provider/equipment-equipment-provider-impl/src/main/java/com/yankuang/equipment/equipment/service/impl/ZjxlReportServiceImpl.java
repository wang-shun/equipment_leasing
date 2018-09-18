package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.ZjxlReportMapper;
import com.yankuang.equipment.equipment.model.ZjxlReport;
import com.yankuang.equipment.equipment.service.ZjxlReportService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RpcProvider
public class ZjxlReportServiceImpl implements ZjxlReportService {

    @Autowired
    ZjxlReportMapper zjxlReportMapper;

    public Boolean create(ZjxlReport zjxlReport){
        if (zjxlReportMapper.create(zjxlReport) <=0 ){
            return false;
        }

        return true;
    }
}
