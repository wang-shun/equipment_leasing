package com.yankuang.equipment.equipment.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.model.SbModel;

public interface SbModelService {

    /**
     * 添加设备规格
     * @param sbModel
     */
    public void create(SbModel sbModel);

    /**
     * 更新设备规格
     * @param sbModel
     */
    public void update(SbModel sbModel);

    /**
     * 根据规格Id查询设备规格
     * @param id
     * @return
     */
    public SbModel findById(Long id);

    /**
     * 根据规格Id删除设备规格
     * @param id
     */
    public void deleteById(Long id);

    /**
     * 查询规格型号列表
     * @param code
     * @param name
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageInfo<SbModel> list(String code,String name,int pageNum, int pageSize);
}
