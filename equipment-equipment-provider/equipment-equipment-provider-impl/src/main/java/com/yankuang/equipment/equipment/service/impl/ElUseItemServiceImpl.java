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

    public Integer updateByEquipmentId(ElUseItem elUseItem){
        return elUseItemMapper.updateByEquipmentId(elUseItem);
    }

    public PageInfo<DtkList> dtkReport(Integer page, Integer size,Map dtkListMap){
        PageHelper.startPage(page,size);
        DtkList dtkList = (DtkList) dtkListMap.get("dtkList");
        //查询退租的记录
        List<DtkList> dtkLists = elUseItemMapper.dtkReport(dtkList);
        if (dtkLists == null || dtkLists.size() <= 0){
            return null;
        }
        for (DtkList dtkList1:dtkLists){
            //查询对应领用时间
            Date startTime = elUseItemMapper.selectTime(dtkList);
            Date endTime = dtkList1.getUseAt();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String startString,endString;
            Calendar  cale;
            //若领用时间为空，则将月初作为领用时间
            if (startTime == null){
                cale = Calendar.getInstance();
                cale.add(Calendar.MONTH, 0);
                cale.set(Calendar.DAY_OF_MONTH, 1);
                startString = formatter.format(cale.getTime());
            }else {
                startString = formatter.format(startTime);
            }
            //若退租时间为空，则将月末作为退租时间
            if (endTime == null){
                cale = Calendar.getInstance();
                cale.add(Calendar.MONTH, 1);
                cale.set(Calendar.DAY_OF_MONTH, 0);
                endString = formatter.format(cale.getTime());
            }else{
                endString = formatter.format(endTime);
            }
            //获取租用天数
            try
            {
                Date beginDate = formatter.parse(startString);
                Date endDate= formatter.parse(endString);
                Long day=(endDate.getTime()-beginDate.getTime())/(24*60*60*1000);
                Double costA1Fee = dtkList.getCostA1() * dtkList.getEquipmentNum() * day;
                dtkList1.setCostA1Fee(costA1Fee);
                dtkList1.setDay(day);
                dtkList1.setUnit("台");//TODO 没有单位字段，暂时写死
             } catch (Exception e) {// TODO 自动生成 catch 块
                e.printStackTrace();
            }
        }
        PageInfo<DtkList> pageInfo = new PageInfo<DtkList>(dtkLists);
        return pageInfo;
    }
}
