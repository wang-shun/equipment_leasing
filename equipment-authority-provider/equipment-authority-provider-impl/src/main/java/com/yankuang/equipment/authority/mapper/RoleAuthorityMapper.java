package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.RoleAuthority;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RoleAuthorityMapper extends MyBatisDao<RoleAuthority> {

    public Boolean create(RoleAuthority t) {
        return this.sqlSession.insert(this.sqlId("create"), t) == 1;
    }

    public List<RoleAuthority> findByRoleId(Long roleId){
        return this.sqlSession.selectList("findByRoleId", roleId);
    }

}
