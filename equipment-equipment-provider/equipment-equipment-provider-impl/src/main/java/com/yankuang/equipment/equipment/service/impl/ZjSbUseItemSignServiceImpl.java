package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.ZjSbUseItemMapper;
import com.yankuang.equipment.equipment.mapper.ZjSbUseItemSignMapper;
import com.yankuang.equipment.equipment.model.ZjSbUseItem;
import com.yankuang.equipment.equipment.model.ZjSbUseItemSign;
import com.yankuang.equipment.equipment.service.ZjSbUseItemSignService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RpcProvider
public class ZjSbUseItemSignServiceImpl implements ZjSbUseItemSignService {

    @Autowired
    ZjSbUseItemSignMapper zjSbUseItemSignMapper;
    @Autowired
    ZjSbUseItemMapper zjSbUseItemMapper;


    /**
     * @method 更新综机设备使用交接单
     * @param zjSbUseItemSignReport
     * @return
     */
    public Boolean create(ZjSbUseItemSign zjSbUseItemSignReport){

        List<ZjSbUseItem> zjSbUseItemList = zjSbUseItemSignReport.getZjSbUseItems();

        ZjSbUseItemSign zjSbUseItemSign = new ZjSbUseItemSign();
        zjSbUseItemSign.setBuildingUser(zjSbUseItemSignReport.getBuildingUser());
        zjSbUseItemSign.setEquipmentManagement(zjSbUseItemSignReport.getEquipmentManagement());
        zjSbUseItemSign.setHandover(zjSbUseItemSignReport.getHandover());
        zjSbUseItemSign.setCreateAt(new Date());

        zjSbUseItemSignMapper.create(zjSbUseItemSign);

        for (ZjSbUseItem zjSbUseItem:zjSbUseItemList){
            zjSbUseItem.setReceiptId(zjSbUseItemSign.getId());//每个交接单里实体类的id是一样的
            zjSbUseItem.setCreateAt(zjSbUseItemSign.getCreateAt());
            zjSbUseItem.setUseAt(zjSbUseItemSignReport.getHandover());
            zjSbUseItemMapper.create(zjSbUseItem);
        }

        return true;
    }

    public int findByHandoverTime(String handover){

        Integer count = zjSbUseItemSignMapper.findByHandoverTime(handover);

        return count;
    }




}
