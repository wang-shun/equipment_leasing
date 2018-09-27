package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.ZEquipmentListReportItemMapper;
import com.yankuang.equipment.equipment.mapper.ZEquipmentListReportMapper;
import com.yankuang.equipment.equipment.model.DtkList;
import com.yankuang.equipment.equipment.model.ListZReport;
import com.yankuang.equipment.equipment.model.ListZReportItem;
import com.yankuang.equipment.equipment.service.ZEquipmentReportService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RpcProvider
@Service
public class ZEquipmentReportServiceImpl implements ZEquipmentReportService {

    @Autowired
    private ZEquipmentListReportMapper zEquipmentListReportMapper;

    @Autowired
    private ZEquipmentListReportItemMapper zEquipmentListReportItemMapper;

    @Transactional
    public Boolean create(ListZReport listZReport,List<ListZReportItem> listZReportItems){

        //解决修改导出的数据累加问题
        zEquipmentListReportMapper.update(listZReport);

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

    @Transactional
    public Map findByPage(Integer page, Integer size, Map listZReportMap) {

        ListZReport listZReport = new ListZReport();
        DtkList dtkList = new DtkList();
        List<ListZReportItem> listZReportItems = new ArrayList<ListZReportItem>();

        PageHelper.startPage(page, size);
        //将map中数据传到实体类中
        BeanUtils.copyProperties(listZReportMap,listZReport);
        List<ListZReport> listZReports = zEquipmentListReportMapper.list(listZReport);

        ListZReportItem listZReportItem = (ListZReportItem)listZReportMap.get("listZReportItems");
        if (listZReportItem == null){
            listZReportItem = new ListZReportItem();
        }

        //查询清单列表
        for (ListZReport listZReport1: listZReports){
            dtkList.setDeptName(listZReport1.getUseDeptName());
            dtkList.setEquipmentEffect(listZReport1.getEquipmentPosition());
            dtkList.setCenterMonth(Integer.parseInt(listZReport1.getUseMonth()));
            dtkList.setCenterYear(Integer.parseInt(listZReport1.getUseYear()));
            dtkList.setKb(listZReport1.getType());
            listZReportItem.setReportId(listZReport1.getId());
            List<ListZReportItem> listZReportItemList = zEquipmentListReportItemMapper.list(listZReportItem);
            for (ListZReportItem listZReportItem1:listZReportItemList){
                listZReportItem1.setSbName(listZReportItem1.getName());
                listZReportItem1.setAssetUnit(listZReportItem1.getEquipmentUnit());
                listZReportItem1.setSbCode(listZReportItem1.getEquipmentCode());
            }
            listZReportItems.addAll(listZReportItemList);
        }
        PageInfo<ListZReportItem> pageInfo = new PageInfo<ListZReportItem>(listZReportItems);

        Map map = new HashMap();
        map.put("dtkList",dtkList);
        map.put("pageInfo",pageInfo);
        return map;
    }

    public Boolean find(DtkList dtkList){
        if(zEquipmentListReportMapper.find(dtkList)>0){
            return true;
        }

        return false;
    }
}
