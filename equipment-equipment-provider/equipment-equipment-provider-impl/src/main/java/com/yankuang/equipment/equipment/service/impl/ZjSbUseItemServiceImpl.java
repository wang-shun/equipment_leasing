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

    @Autowired
    SbElFeeServiceImpl sbElFeeService;

    public PageInfo<ZjSbUseItem> listEquipmentReceipt(Integer page, Integer size, Map zjSbUseItemMap){
        PageHelper.startPage(page,size);
        List<ZjSbUseItem> zjSbUseItems = zjSbUseItemMapper.listEquipmentReceipt(zjSbUseItemMap);
        PageInfo<ZjSbUseItem> pageInfo = new PageInfo<ZjSbUseItem>(zjSbUseItems);
        return pageInfo;
    }

    /**
     * @method 更新综机设备使用交接单
     * @param zjSbUseItem
     * @return
     */
    public Boolean create(ZjSbUseItem zjSbUseItem){
        return zjSbUseItemMapper.create(zjSbUseItem) >=0;
    }




}
