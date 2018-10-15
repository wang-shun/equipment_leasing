package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.ElEquipmentGroupMapper;
import com.yankuang.equipment.equipment.model.ElEquipmentGroup;
import com.yankuang.equipment.equipment.service.ElEquipmentGroupService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: zyy
 * @Date: 2018-10-08 11:44
 * @Description:
 */
@Service
@RpcProvider
public class ElEquipmentGroupServiceImpl implements ElEquipmentGroupService {

    @Autowired
    ElEquipmentGroupMapper elEquipmentGroupMapper;

    public boolean update(ElEquipmentGroup elEquipmentGroup) {
        return elEquipmentGroupMapper.update(elEquipmentGroup) > 0;
    }

    public List<ElEquipmentGroup> findByCondition(ElEquipmentGroup groupI) {

        List<ElEquipmentGroup> groups = elEquipmentGroupMapper.findByCondition(groupI);

        return groups;
    }

    public boolean create(ElEquipmentGroup group) {
        return elEquipmentGroupMapper.create(group) > 0;
    }
}
