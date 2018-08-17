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
@RpcProvider
public class SbPositionServiceImpl implements SbPositionService {

    @Autowired
    SbPositionMapper sbPositionMapper;

    public void create(SbPosition sbPosition) {
        SbPosition sbPosition1 = sbPositionMapper.selectByMaxId();
        if(sbPosition1==null){
            sbPosition.setCode("01013100001");
        }else{
            Integer code = Integer.parseInt(sbPosition1.getCode());
            sbPosition.setCode(String.valueOf(code+1).length()<sbPosition1.getCode().length()?'0'+String.valueOf(code+1):String.valueOf(code+1));
        }
        sbPositionMapper.create(sbPosition);
    }

    public void update(SbPosition sbPosition) {
        sbPositionMapper.update(sbPosition);
    }

    public SbPosition findById(Long id) {
        return sbPositionMapper.findById(id);
    }

    public void deleteById(Long id) {
        sbPositionMapper.deleteById(id);
    }

    public PageInfo<SbPosition> list(SbPosition sbPosition,int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<SbPosition> list = sbPositionMapper.list(sbPosition);
        PageInfo<SbPosition> pageInfo = new PageInfo<SbPosition>(list);
        return pageInfo;
    }

    public void deletes(List<Long> ids){
        sbPositionMapper.deletes(ids);
    }
}
