package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.ZEquipmentListReportMapper;
import com.yankuang.equipment.equipment.mapper.ZNewReportMapper;
import com.yankuang.equipment.equipment.model.EquipmentLoss;
import com.yankuang.equipment.equipment.model.ListZReport;
import com.yankuang.equipment.equipment.model.ZNewReport;
import com.yankuang.equipment.equipment.service.ZNewReportService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RpcProvider
public class ZNewReportServiceImpl implements ZNewReportService {

    @Autowired
    ZNewReportMapper zNewReportMapper;
    @Autowired
    ZEquipmentListReportMapper zEquipmentListReportMapper;

    @Transactional
    public boolean create(ZNewReport zNewReport){
        //添加对之前数据更改
        zNewReportMapper.update(zNewReport);
        if (zNewReportMapper.create(zNewReport) > 0){
            return true;
        }

        return false;
    }

    public PageInfo<ListZReport> select(Integer page,Integer size,ZNewReport zNewReport){
        Double sum = 0.0;
        ListZReport listZReport = new ListZReport();
        //转换实体类
        BeanUtils.copyProperties(zNewReport,listZReport);

        List<ListZReport> listZReports = zEquipmentListReportMapper.list(listZReport);
        for (ListZReport listZReportq:listZReports){
            listZReportq.setSum(listZReportq.getSum() == null? 0:listZReportq.getSum());
            sum += listZReportq.getSum();
        }
        for (ListZReport listZReportf:listZReports){
            listZReportf.setFeeSum(sum);
        }
        PageHelper.startPage(page, size);
        PageInfo<ListZReport> pageInfo = new PageInfo<ListZReport>(listZReports);
        return pageInfo;
    }

    public PageInfo<ZNewReport> list(Integer page,Integer size,ZNewReport zNewReport){
        Double sum = 0.0;
        PageHelper.startPage(page, size);
        List<ZNewReport> maps = zNewReportMapper.list(zNewReport);
        for (ZNewReport zNewReportq:maps){
            zNewReportq.setSum((zNewReportq.getSum() == null)?0:zNewReportq.getSum());
            sum += zNewReportq.getSum();
        }
        for (ZNewReport zNewReportf:maps){
            zNewReportf.setFeeSum(sum);
        }
        PageInfo<ZNewReport> pageInfo = new PageInfo<ZNewReport>(maps);
        return pageInfo;
    }
}
