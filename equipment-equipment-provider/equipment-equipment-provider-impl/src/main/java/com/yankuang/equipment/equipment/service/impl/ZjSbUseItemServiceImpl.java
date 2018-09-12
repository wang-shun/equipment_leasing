package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.ZjSbUseItemMapper;
import com.yankuang.equipment.equipment.model.ZjSbUseItem;
import com.yankuang.equipment.equipment.service.ZjSbUseItemService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RpcProvider
public class ZjSbUseItemServiceImpl implements ZjSbUseItemService {
    @Autowired
    ZjSbUseItemMapper zjSbUseItemMapper;

    public PageInfo<ZjSbUseItem> listEquipmentReceipt(Integer page, Integer size, Map zjSbUseItemMap){
        PageHelper.startPage(page,size);
        List<ZjSbUseItem> zjSbUseItems = zjSbUseItemMapper.listEquipmentReceipt(zjSbUseItemMap);
//        if (elUseItems == null){
//            return null;
//        }
//        //分页查询将设备信息添加上
//        for (ElUseItem elUseItem:elUseItems){
//            SbEquipmentT sbEquipmentT = sbEquipmentTMapper.findById(elUseItem.getEquipmentId());
//            elUseItem.setSbEquipmentT(sbEquipmentT);
//        }
        PageInfo<ZjSbUseItem> pageInfo = new PageInfo<ZjSbUseItem>(zjSbUseItems);
        return pageInfo;
    }


}
