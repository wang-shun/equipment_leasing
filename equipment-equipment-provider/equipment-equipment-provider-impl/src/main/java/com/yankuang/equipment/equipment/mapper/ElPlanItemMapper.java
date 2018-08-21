package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElPlanItem;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhouy on 2018/8/4.
 */
@Repository
public class ElPlanItemMapper extends MyBatisDao<ElPlanItem> {
    public int saveByPrimaryKey(ElPlanItem elPlanItem) {return sqlSession.insert("saveByPrimaryKey", elPlanItem);}

    public List<ElPlanItem> findByPlanId(String planId) {
        return sqlSession.selectList("findByPlanId", planId);
    }

    public ElPlanItem findByItemId(String itemId) {
        return sqlSession.selectOne("findByItemId", itemId);
    }

    public Integer updateById(ElPlanItem elPlanItem) {
        return sqlSession.update("updateById", elPlanItem);
    }
}
