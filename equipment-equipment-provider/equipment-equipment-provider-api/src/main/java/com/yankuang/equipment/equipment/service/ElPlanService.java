package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.equipment.model.ElPlan;
import com.yankuang.equipment.equipment.model.ElPlanItem;
import io.terminus.common.model.Paging;

import java.util.List;

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

    /**
     * 删除设备租赁计划
     * @param planId
     * @return
     */
    Boolean deletePlan(String planId);

    /**
     * 分页条件查询设备租赁计划
     * @param elPlan
     * @param pageSize
     * @param pageNum
     * @return
     */
    Paging findElPlanByCondition(ElPlan elPlan, int pageSize, int pageNum);

    /**
     * 审核租赁计划
     * @param elPlan
     * @return
     */
    CommonResponse approve(ElPlan elPlan);

    /**
     * 通过itemId查询
     * @param itemId
     * @return
     */
    ElPlanItem findEPlanItemByItemId(String itemId);

    /**
     * @method 多条件查询计划列表
     * @param elPlan
     * @return
     */
    List<ElPlan> findByCreatorId(ElPlan elPlan);

    /**
     * 删除租赁计划设备列表记录
     * @param itemId
     * @return
     */
    boolean deleteItemById(String itemId);
}
