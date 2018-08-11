package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.DeptUser;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

@Repository
public class DeptUserMapper extends MyBatisDao<DeptUser> {
    public Long findDeptId(Long userId){
        return this.sqlSession.selectOne("findDeptIds",userId);
    }
    public Boolean deleteByUserId(Long userId) {
        return this.sqlSession.update(this.sqlId("deleteByUserId"), userId) == 1;
    }
}
