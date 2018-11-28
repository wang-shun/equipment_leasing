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
     * 查询通用设备领用记录
     * @param elUseItem
     * @return
     */
    List<ElUseItem> findElUseItemTL(ElUseItem elUseItem);

    /**
     * @method 更新退租领用标记
     * @param elUseItem
     * @return
     */
    Integer updateByEquipmentId(ElUseItem elUseItem);

    /**
     * 查询设备对应的退租记录
     * @param dtkList
     * @return
     */
    DtkList findSign(DtkList dtkList);

    /**
     * 查询没有退租的设备对应的领用记录
     * @param dtkList
     * @return
     */
    List<DtkList> findReportLY(DtkList dtkList);

    /**
     * 分页查询清单报表
     * @param page
     * @param size
     * @param dtkLists
     * @return
     */
    PageInfo<DtkList> dtkReportPage(Integer page,Integer size,List<DtkList> dtkLists);

    /**
     * 判断矿别
     * @param dtkList
     * @return
     */
    Boolean findKB(DtkList dtkList);

    /**
     * 判断设备是否被审核领用
     * @param id
     * @return
     */
    Boolean findEquipmentLY(Long id);

    /**
     * 判断设备是否被审核退租
     * @param id
     * @return
     */
    Boolean findEquipmentTZ(Long id);
}
