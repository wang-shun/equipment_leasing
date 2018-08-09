package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.SbPositionMapper;
import com.yankuang.equipment.equipment.model.SbPosition;
import com.yankuang.equipment.equipment.service.SbPositionService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider(version = "0.0.1")
public class SbPositionServiceImpl implements SbPositionService {

    @Autowired
    SbPositionMapper sbPositionMapper;

    public void createSbPosition(SbPosition sbPosition) {
        SbPosition sbPosition1 = sbPositionMapper.selectByMaxId();
        if(sbPosition1==null){
            sbPosition.setCode("01013100001");
        }else{
            Integer code = Integer.parseInt(sbPosition.getCode());
            sbPosition.setCode(String.valueOf(code+1));
        }
        sbPositionMapper.insert(sbPosition);
    }

    public void updateSbPosition(SbPosition sbPosition) {
        sbPositionMapper.updateByPrimaryKey(sbPosition);
    }

    public SbPosition findSbPositionByKey(Long id) {
        return sbPositionMapper.selectByPrimaryKey(id);
    }

    public void deleteSbPositionByKey(Long id) {
        sbPositionMapper.deleteByPrimaryKey(id);
    }

    public PageInfo<SbPosition> listAll(String code,String name,int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<SbPosition> list = sbPositionMapper.listAll(code,name);
        PageInfo<SbPosition> pageInfo = new PageInfo<SbPosition>(list);
        return pageInfo;
    }
}
