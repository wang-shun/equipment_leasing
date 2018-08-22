package com.yankuang.equipment.equipment.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.model.ElPlanItem;
import com.yankuang.equipment.equipment.model.ElUse;

import java.util.List;
import java.util.Map;

public interface ElUseService {
    /**
     * @method 添加设备领用申请记录
     * @param elUse
     * @return
     */
    Boolean create(ElUse elUse);

    /**
     * @method 更新设备领用记录
     * @param elUse
     * @return
     */
    Boolean update(ElUse elUse);

    /**
     * @method 通过id查询更新设备领用记录
     * @param id
     * @return
     */
    ElUse select(Long id);

    /**
     * @method 通过id删除设备领用记录
     * @param id
     * @return
     */
    Boolean delete(Long id);

    /**
     * @method 查询设备领用记录列表，然后在controller中通过pagehelper分页
     * @return
     */
    PageInfo<ElUse> list(Integer page, Integer size, Map elUseMap);

    /**
     * @method 提交审核
     * @param id
     * @return
     */
    Boolean open(Long id);

    /**
     * @method 通过计划查询分区对象列表
     * @param elPlanItem
     * @return
     */
    List<ElPlanItem> findByPlanId(ElPlanItem elPlanItem);
}
