package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElPlan;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhouy on 2018/7/30.
 */
@Repository
public interface ElPlanMapper{

    ElPlan findById (String planId);

    int delete(String planId);

    int insert(ElPlan elPlan);

    List<ElPlan> listByCondition(ElPlan elPlan);

    int update(ElPlan elPlan);

    List<ElPlan> findByCreatorId(ElPlan elPlan);

}
