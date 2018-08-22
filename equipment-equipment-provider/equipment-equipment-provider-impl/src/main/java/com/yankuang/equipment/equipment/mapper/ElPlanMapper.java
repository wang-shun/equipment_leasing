package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElPlan;
import com.yankuang.equipment.equipment.model.ElPlanItem;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zhouy on 2018/7/30.
 */
@Repository
public class ElPlanMapper extends MyBatisDao<ElPlan> {

    public List<ElPlanItem> queryElPlanItemByPlanId(String planId) { return sqlSession.selectList("queryElPlanItemByPlanId", planId);}

    public ElPlan findById (String planId) {
        return sqlSession.selectOne("findByPrimarykey", planId);
    }

    public int deletePlanItemByPlanId(String planId) { return sqlSession.delete("deletePlanItemByPlanId", planId); }

    public int savePlanItemByPlanId(ElPlan elPlan) { return sqlSession.insert("savePlanItemByPlanId", elPlan); }

    public int deletePlanByPlanId(String planId) { return sqlSession.update("deletePlanByPlanId", planId);  }

    public int insertByPrimaryKey(ElPlan elPlan) {return sqlSession.insert("insertByPrimaryKey", elPlan);}

    public List<ElPlan> listByCondition(ElPlan elPlan) {return sqlSession.selectList("listByCondition", elPlan);}

    public int updateByPrimarykey(ElPlan elPlan) {return sqlSession.update("updateByPrimarykey", elPlan);}

    public List<ElPlan> findByCreatorId(ElPlan elPlan){
        return sqlSession.selectList("findByCreatorId",elPlan);
    }

}
