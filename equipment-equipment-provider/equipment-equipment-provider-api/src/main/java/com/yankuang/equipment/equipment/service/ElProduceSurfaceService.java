package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ElProduceSurface;

import java.util.List;

/**
 * @Auther: zyy
 * @Date: 2018-10-08 11:34
 * @Description:
 */
public interface ElProduceSurfaceService {

    /**
     * 编辑更新生产工作面
     * @param elProduceSurface
     * @return
     */
    boolean update(ElProduceSurface elProduceSurface);

    boolean create(ElProduceSurface surface);

    List<ElProduceSurface> findByCondition(ElProduceSurface surfaceI);
}
