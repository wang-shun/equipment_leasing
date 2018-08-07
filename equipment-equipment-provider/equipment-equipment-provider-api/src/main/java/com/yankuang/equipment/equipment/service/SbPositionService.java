package com.yankuang.equipment.equipment.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.model.SbPosition;

public interface SbPositionService {

    /**
     * 添加设备位置
     * @param sbPosition
     */
    public void createSbPosition(SbPosition sbPosition);

    /**
     * 更新设备位置
     * @param sbPosition
     */
    public void updateSbPosition(SbPosition sbPosition);

    /**
     * 根据位置Id查询设备位置信息
     * @param id
     * @return
     */
    public SbPosition findSbPositionByKey(Long id);

    /**
     * 根据位置Id删除设备位置信息
     * @param id
     */
    public void deleteSbPositionByKey(Long id);

    /**
     * 查询设备功能位置列表
     * @param code
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<SbPosition> listAll(String code,String name,int pageNum, int pageSize);
}
