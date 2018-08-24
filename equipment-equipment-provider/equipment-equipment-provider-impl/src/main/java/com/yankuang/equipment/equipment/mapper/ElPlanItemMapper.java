package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElPlanItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhouy on 2018/8/4.
 */
@Repository
public interface ElPlanItemMapper {
    int insert(ElPlanItem elPlanItem);

    List<ElPlanItem> findByPlanId(String planId);

    ElPlanItem findByItemId(String itemId);

    int update(ElPlanItem elPlanItem);
}
