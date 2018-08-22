package com.yankuang.equipment.equipment.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.model.ElPlanUse;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouy on 2018/8/18.
 */
public interface ElPlanUseService {

    /**
     * 创建租赁计划中间类
     * @param elPlanUse
     * @return
     */
    Integer create(ElPlanUse elPlanUse);

    /**
     * 租赁计划设备list
     */
    List<ElPlanUse> findElPlanUse(ElPlanUse elPlanUse);

    /**
     * 编辑更新
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
