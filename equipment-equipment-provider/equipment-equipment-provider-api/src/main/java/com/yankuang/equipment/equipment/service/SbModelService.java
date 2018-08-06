package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.SbModel;

public interface SbModelService {

    /**
     * 添加设备规格
     * @param sbModel
     */
    public void createSbModel(SbModel sbModel);

    /**
     * 更新设备规格
     * @param sbModel
     */
    public void updateSbModel(SbModel sbModel);

    /**
     * 根据规格Id查询设备规格
     * @param id
     * @return
     */
    public SbModel findSbModelByKey(Long id);

    /**
     * 根据规格Id删除设备规格
     * @param id
     */
    public void deleteSbModelByKey(Long id);
}
