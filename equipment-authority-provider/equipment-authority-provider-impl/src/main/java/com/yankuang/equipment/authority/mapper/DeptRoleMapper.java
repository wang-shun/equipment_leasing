package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.DeptRole;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class DeptRoleMapper extends MyBatisDao<DeptRole> {
    public DeptRole selectByDeptIdAndRoleId(Map map){
        return getSqlSession().selectOne("selectByDeptIdAndRoleId", map);
    }
}
