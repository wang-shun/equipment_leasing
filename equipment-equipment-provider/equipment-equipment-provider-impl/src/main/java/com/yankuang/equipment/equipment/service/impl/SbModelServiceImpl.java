package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.SbModelMapper;
import com.yankuang.equipment.equipment.model.SbModel;
import com.yankuang.equipment.equipment.service.SbModelService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
