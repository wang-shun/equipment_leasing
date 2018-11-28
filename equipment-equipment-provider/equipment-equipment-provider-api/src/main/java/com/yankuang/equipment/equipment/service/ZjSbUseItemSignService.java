package com.yankuang.equipment.equipment.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.model.ZjSbUseItem;
import com.yankuang.equipment.equipment.model.ZjSbUseItemSign;

import java.util.Map;


public interface ZjSbUseItemSignService {

    /**
     * @method 更新综机设备使用交接单
     * @param zjSbUseItemSign
     * @return
     */
    Boolean create(ZjSbUseItemSign zjSbUseItemSign);

    int findByHandoverTime(String handover);

}
