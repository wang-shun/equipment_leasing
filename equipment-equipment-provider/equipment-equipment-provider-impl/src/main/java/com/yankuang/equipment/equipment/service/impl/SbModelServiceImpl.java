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
@RpcProvider
public class SbModelServiceImpl implements SbModelService {

    @Autowired
    SbModelMapper sbModelMapper;

    public void create(SbModel sbModel) {
        SbModel sbModel1 = sbModelMapper.selectByMaxId();
        if(sbModel1==null){
            sbModel.setCode(sbModel.getSbtypeOne()+sbModel.getSbtypeTwo()+sbModel.getSbtypeThree()+"0001");
        }else{
            Integer code = Integer.parseInt(sbModel.getCode());
            sbModel.setCode(String.valueOf(code+1));
        }
        sbModelMapper.create(sbModel);
    }

    public void update(SbModel sbModel) {
        sbModelMapper.update(sbModel);
    }

    public SbModel findById(Long id) {
        return sbModelMapper.findById(id);
    }

    public void deleteById(Long id) {
        sbModelMapper.deleteById(id);
    }

    public PageInfo<SbModel> list(String code,String name,int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<SbModel> list = sbModelMapper.list(code,name);
        PageInfo<SbModel> pageInfo = new PageInfo<SbModel>(list);
        return pageInfo;
    }
}