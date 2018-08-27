package com.yankuang.equipment.equipment.service;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.model.SbElFeeConfig;

import java.util.List;

public interface SbElFeeConfigService {

    public void create(SbElFeeConfig sbElFeeConfig);

    public void update(SbElFeeConfig sbElFeeConfig);

    public SbElFeeConfig findById(Long id);

    public SbElFeeConfig findByYear(String year);

    public void deleteById(Long id);

    public void deletes(List<Long> ids);

    public PageInfo<SbElFeeConfig> list(int pageNum, int pageSize);

}
