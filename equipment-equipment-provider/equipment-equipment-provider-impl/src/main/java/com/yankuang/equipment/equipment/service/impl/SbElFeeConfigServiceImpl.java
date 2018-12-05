package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.SbElFeeConfigMapper;
import com.yankuang.equipment.equipment.model.SbElFeeConfig;
import com.yankuang.equipment.equipment.service.SbElFeeConfigService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RpcProvider
public class SbElFeeConfigServiceImpl implements SbElFeeConfigService {

    @Autowired
    SbElFeeConfigMapper sbElFeeConfigMapper;

    public void create(SbElFeeConfig sbElFeeConfig) {
        sbElFeeConfigMapper.create(sbElFeeConfig);
    }

    public void update(SbElFeeConfig sbElFeeConfig) {
        sbElFeeConfigMapper.update(sbElFeeConfig);
    }

    public SbElFeeConfig findById(Long id) {
        return sbElFeeConfigMapper.findById(id);
    }

    public SbElFeeConfig findByYear(String year){
        return sbElFeeConfigMapper.findByYear(year);
    }

    public void deleteById(Long id) {
        sbElFeeConfigMapper.deleteById(id);
    }

    public void deletes(List<Long> ids) {
        sbElFeeConfigMapper.deletes(ids);
    }

    public PageInfo<SbElFeeConfig> list(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<SbElFeeConfig> list = sbElFeeConfigMapper.list();
        PageInfo pageInfo = new PageInfo<SbElFeeConfig>(list);
        return pageInfo;
    }
}
