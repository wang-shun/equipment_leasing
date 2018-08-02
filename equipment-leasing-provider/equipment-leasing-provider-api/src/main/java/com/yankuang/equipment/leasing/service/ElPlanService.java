package com.yankuang.equipment.leasing.service;

import com.yankuang.equipment.leasing.model.ElPlan;

/**
 * Created by zhouy on 2018/7/30.
 */
public interface ElPlanService {

    /**
     * 新增设备租赁计划
     * @param elPlan
     * @return
     */
    Boolean create(ElPlan elPlan);

    /**
     * 按照主键查询租赁计划
     * @param planId
     * @return
     */
    ElPlan findElPlanById(String planId);

    /**
     * 更新设备租赁计划
     * @param elPlan
     * @return
     */
    Boolean update(ElPlan elPlan);
}
