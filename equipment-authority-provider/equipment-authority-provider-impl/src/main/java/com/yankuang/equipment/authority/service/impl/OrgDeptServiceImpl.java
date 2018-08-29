package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.OrgDeptMapper;
import com.yankuang.equipment.authority.model.OrgDept;
import com.yankuang.equipment.authority.service.OrgDeptService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RpcProvider
public class OrgDeptServiceImpl implements OrgDeptService {

    @Autowired
    OrgDeptMapper orgDeptMapper;


    public Boolean create(OrgDept orgDept) {
        return orgDeptMapper.create(orgDept);
    }

    /**
     * 组织删除,根据组织id，删除对应组织部门关系.
     * @param id
     * @return
     */
    public Boolean deleteByOrgId(Long id) {
        return orgDeptMapper.deleteByOrgId(id);
    }

    /**
     * 部门字典删除，根据部门id，删除对应部门组织关系.
     * 管理员字典维护使用.
     * @param id
     * @return
     */
    public Boolean deleteByDeptId(Long id) {
        return orgDeptMapper.deleteByDeptId(id);
    }

    /**
     * 根据组织id和下属部门id,删除组织与下属部门关系  .
     * @param map
     * @return
     */
    public Boolean deleteByOrgIdAndDeptId(Map map) {
        return orgDeptMapper.deleteByOrgIdAndDeptId(map);
    }

    public Boolean update(OrgDept orgDept) {
        return orgDeptMapper.update(orgDept);
    }

    /**
     * 根据组织id,从关系表查询下属部门列表
     * @param id
     * @return
     */
    public List<OrgDept> findByOrgId(Long id) {
        return orgDeptMapper.findByOrgId(id);
    }

    /**
     * 添加部门时，根据组织id和部门id，关系表查重
     * @param map
     * @return
     */
    public List<OrgDept> findByOrgIdAndDeptId(Map map) {
        return orgDeptMapper.findByOrgIdAndDeptId(map);
    }
}
