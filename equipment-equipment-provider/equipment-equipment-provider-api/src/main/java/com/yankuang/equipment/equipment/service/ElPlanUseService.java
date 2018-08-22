package com.yankuang.equipment.equipment.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.model.ElPlanUse;

import java.util.Map;

public interface ElPlanUseService {
    /**
     * @method 更新计划使用
     * @param elPlanUse
     * @return
     */
    Boolean update(ElPlanUse elPlanUse);

    /**
     * @method 分页查询领用计划
     * @param page
     * @param size
     * @param elPlanUseMap
     * @return
     */
    PageInfo<ElPlanUse> list(Integer page, Integer size, Map elPlanUseMap);
}
