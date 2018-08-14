package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElOre;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by boms on 2018/8/13.
 */
@Repository
public class OreMapper extends MyBatisDao<ElOre>{
    public Byte findType(Long id){
        return this.sqlSession.selectOne("findType",id);
    }

    public Boolean deleteOre(Long id){
        return this.sqlSession.update("deleteOre", id) >= 0;
    }

    public List<Long> getId(Long id){
        return this.sqlSession.selectList("getId",id);
    }

    public List<ElOre> findByPId(Long id){
        return this.sqlSession.selectList("findByPId",id);
    }
}
