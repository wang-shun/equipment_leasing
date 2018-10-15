package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ElEquipmentGroup;
import com.yankuang.equipment.equipment.model.ElProducePlan;

import java.util.List;
import java.util.Map;

/**
 * @Auther: zyy
 * @Date: 2018-10-08 11:32
 * @Description:
 */
public interface ElProducePlanService {

    /**
     * 新增生产接续计划
     * @param elProducePlan
     * @return
     */
    boolean create(ElProducePlan elProducePlan);

    /**
     * 编辑更新生产接续计划
     * @param elProducePlan
     * @return
     */
    boolean update(ElProducePlan elProducePlan);

    /**
     * 查询生产接续计划
     * @param elProducePlan
     * @return
     */
    List<Map<String,Object>> findByCondition(ElProducePlan elProducePlan);

    /**
     * 删除生产接续计划
     * @param id
     * @return
     */
    boolean delete(Long id);

    ElProducePlan findById(Long id);

    List<ElEquipmentGroup> findBySurfaceCode(String configYear, String effectCode);
}
