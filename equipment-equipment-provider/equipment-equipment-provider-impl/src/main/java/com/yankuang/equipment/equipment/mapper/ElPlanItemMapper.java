package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElPlanItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouy on 2018/8/4.
 */
@Repository
public interface ElPlanItemMapper {
    int insert(ElPlanItem elPlanItem);

    List<ElPlanItem> findByPlanId(String planId);

    ElPlanItem findByItemId(String itemId);

    int update(ElPlanItem elPlanItem);

    List<ElPlanItem> findElPlanItemList(ElPlanItem elPlanItem);

    int deleteItemById(String itemId);

}
