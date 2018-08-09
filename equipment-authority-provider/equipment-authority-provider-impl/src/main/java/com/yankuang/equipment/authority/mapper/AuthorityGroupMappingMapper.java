package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.AuthorityGroupMapping;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class AuthorityGroupMappingMapper extends MyBatisDao<AuthorityGroupMapping> {
    public AuthorityGroupMapping selectByAuthorityIdAndGroupId(Map map){
        return getSqlSession().selectOne("selectByAuthorityIdAndGroupId", map);
    }
}
