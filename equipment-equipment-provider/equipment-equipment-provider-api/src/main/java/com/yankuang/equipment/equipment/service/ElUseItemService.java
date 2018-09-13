package com.yankuang.equipment.equipment.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.model.DtkList;
import com.yankuang.equipment.equipment.model.ElUseItem;

import java.util.List;
import java.util.Map;

public interface ElUseItemService {
    /**
     * @mehtod 领用明细表创建功能
     * @param elUseItem
     * @return
     */
    Boolean create(ElUseItem elUseItem);

    /**
     * @method 领用明细表更新功能
     * @param elUseItem
     * @return
     */
    Boolean update(ElUseItem elUseItem);

    /**
     * @method 领用明细表分页查询功能
     * @param page
     * @param size
     * @param elUseItemMap
     * @return
     */
    PageInfo<ElUseItem> list(Integer page, Integer size, Map elUseItemMap);

    /**
     * @method 领用明细表通过id查询功能
     * @param itemId
     * @return
     */
    ElUseItem findById(Long itemId);

    /**
     * @method 领用明细表通过使用id删除功能
     * @param useId
     * @return
     */
    Boolean delete(Long useId);

    /**
     * @method 领用明细表通过id删除功能
     * @param itemId
     * @return
     */
    Boolean deleteById(Long itemId);

    /**
     * @method 查询是否存在该使用设备
     * @param itemId
     * @return
     */
    List<ElUseItem> findByUseId(Long itemId);

    /**
     * @method 退租明细表分页查询
     * @param page
     * @param size
     * @param elUseItemMap
     * @return
     */
    PageInfo<ElUseItem> listTz(Integer page, Integer size,Map elUseItemMap);

    /**
     * @method 更新退租领用标记
     * @param elUseItem
     * @return
     */
    Integer updateByEquipmentId(ElUseItem elUseItem);

//    /**
//     * @method
//     * @param dtkList
//     * @return
//     */
//    List<DtkList> dtkReport(DtkList dtkList);

    PageInfo<DtkList> dtkReport(Integer page, Integer size,Map dtkListMap);
}
