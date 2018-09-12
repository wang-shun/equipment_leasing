package com.yankuang.equipment.equipment.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.model.ZjSbUseItem;

import java.util.Map;


public interface ZjSbUseItemService {

    /**
     * @method 综机设备使用交接单分页查询功能
     * @param page
     * @param size
     * @param zjSbUseItemMap
     * @return
     */
    PageInfo<ZjSbUseItem> listEquipmentReceipt(Integer page, Integer size, Map zjSbUseItemMap);
}
