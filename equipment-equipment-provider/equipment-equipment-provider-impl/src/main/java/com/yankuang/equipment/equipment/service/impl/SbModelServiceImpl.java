package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.SbModelMapper;
import com.yankuang.equipment.equipment.model.SbModel;
import com.yankuang.equipment.equipment.service.SbModelService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider(version = "0.0.1")
public class SbModelServiceImpl implements SbModelService {

    @Autowired
    SbModelMapper sbModelMapper;

    public void createSbModel(SbModel sbModel) {
        sbModelMapper.insert(sbModel);
    }

    public void updateSbModel(SbModel sbModel) {
        sbModelMapper.updateByPrimaryKey(sbModel);
    }

    public SbModel findSbModelByKey(Long id) {
        return sbModelMapper.selectByPrimaryKey(id);
    }

    public void deleteSbModelByKey(Long id) {
        sbModelMapper.deleteByPrimaryKey(id);
    }

    public PageInfo<SbModel> listAll(String code,String name,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<SbModel> list = sbModelMapper.listAll(code,name);
        PageInfo<SbModel> pageInfo = new PageInfo<SbModel>(list);
        return pageInfo;
    }
}
