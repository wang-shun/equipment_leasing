package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.ElUseItemMapper;
import com.yankuang.equipment.equipment.mapper.SbEquipmentTMapper;
import com.yankuang.equipment.equipment.model.DtkList;
import com.yankuang.equipment.equipment.model.ElUseItem;
import com.yankuang.equipment.equipment.service.ElUseItemService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RpcProvider
public class ElUseItemServiceImpl implements ElUseItemService{
    @Autowired
    ElUseItemMapper elUseItemMapper;

    @Autowired
    SbEquipmentTMapper sbEquipmentTMapper;

    public Boolean create(ElUseItem elUseItem){
        return elUseItemMapper.create(elUseItem) > 0 ;
    }

    public Boolean update(ElUseItem elUseItem){
        return elUseItemMapper.update(elUseItem) >= 0;
    }

    public PageInfo<ElUseItem> list(Integer page, Integer size,Map elUseItemMap){
        PageHelper.startPage(page,size);
        elUseItemMap.put("isUse",(byte)1);
        List<ElUseItem> elUseItems = elUseItemMapper.list(elUseItemMap);
//        if (elUseItems == null){
//            return null;
//        }
//        //分页查询将设备信息添加上
//        for (ElUseItem elUseItem:elUseItems){
//            SbEquipmentT sbEquipmentT = sbEquipmentTMapper.findById(elUseItem.getEquipmentId());
//            elUseItem.setSbEquipmentT(sbEquipmentT);
//        }
        PageInfo<ElUseItem> pageInfo = new PageInfo<ElUseItem>(elUseItems);
        return pageInfo;
    }

    public ElUseItem findById(Long itemId){
        return elUseItemMapper.findById(itemId);
    }

    public Boolean delete(Long useId){
        return elUseItemMapper.delete(useId) >= 0;
    }

    public Boolean deleteById(Long itemId){
        return elUseItemMapper.deleteById(itemId) >= 0;
    }

    public List<ElUseItem> findByUseId(Long itemId){
        return elUseItemMapper.findByUseId(itemId);
    }

    public PageInfo<ElUseItem> listTz(Integer page, Integer size,Map elUseItemMap){
        PageHelper.startPage(page,size);
        elUseItemMap.put("isUse",(byte)2);
        List<ElUseItem> elUseItems = elUseItemMapper.list(elUseItemMap);
//        if (elUseItems == null){
//            return null;
//        }
//        //分页查询将设备信息添加上
//        for (ElUseItem elUseItem:elUseItems){
//            SbEquipmentT sbEquipmentT = sbEquipmentTMapper.findById(elUseItem.getEquipmentId());
//            elUseItem.setSbEquipmentT(sbEquipmentT);
//        }
        PageInfo<ElUseItem> pageInfo = new PageInfo<ElUseItem>(elUseItems);
        return pageInfo;
    }

    public List<ElUseItem> findElUseItemTL(ElUseItem elUseItem) {
        return elUseItemMapper.findElUseItemTL(elUseItem);
    }

    public Integer updateByEquipmentId(ElUseItem elUseItem){
        return elUseItemMapper.updateByEquipmentId(elUseItem);
    }

    public DtkList findSign(DtkList dtkList){
        //查询退租的记录
        DtkList dtkLists = elUseItemMapper.dtkReport(dtkList);
        return dtkLists;
    }

    public List<DtkList> findReportLY(DtkList dtkList){

        List<DtkList> list = elUseItemMapper.findReportLY(dtkList);
        return list;
    }

    public PageInfo<DtkList> dtkReportPage(Integer page,Integer size,List<DtkList> dtkLists){
        PageHelper.startPage(page,size);
        PageInfo<DtkList> pageInfo = new PageInfo<DtkList>(dtkLists);
        return pageInfo;
    }

    public Boolean findKB(DtkList dtkList){
        if (elUseItemMapper.findKB(dtkList) > 0){
            return true;
        }

        return false;
    }

    public Boolean findEquipmentLY(Long id){
        if (elUseItemMapper.findEquipmentLY(id) > 0){
            return true;
        }
        return false;
    }

    public Boolean findEquipmentTZ(Long id){
        if (elUseItemMapper.findEquipmentTZ(id) > 0){
            return true;
        }
        return false;
    }
}
