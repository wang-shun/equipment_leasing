package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.AuthorityGroupMapping;
import io.terminus.common.mysql.dao.MyBatisDao;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class AuthorityGroupMappingMapper extends MyBatisDao<AuthorityGroupMapping> {
    public AuthorityGroupMapping selectByAuthorityIdAndGroupId(Map map){
        return getSqlSession().selectOne("selectByAuthorityIdAndGroupId", map);
    }

    public Boolean create(AuthorityGroupMapping t) {
        return getSqlSession().insert("com.yankuang.equipment.authority.mapper.AuthorityGroupMappingMapper.create", t) == 1;
    }

    public List<AuthorityGroupMapping> findByGroupId(Long groupId) {
        return this.sqlSession.selectList("findByGroupId", groupId);
    }

}
