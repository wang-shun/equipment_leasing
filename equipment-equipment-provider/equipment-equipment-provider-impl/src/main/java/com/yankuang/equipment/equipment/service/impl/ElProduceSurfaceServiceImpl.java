package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.ElProduceSurfaceMapper;
import com.yankuang.equipment.equipment.model.ElProduceSurface;
import com.yankuang.equipment.equipment.service.ElProduceSurfaceService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Auther: zyy
 * @Date: 2018-10-08 11:42
 * @Description:
 */
@Service
@RpcProvider
public class ElProduceSurfaceServiceImpl implements ElProduceSurfaceService {

    @Autowired
    ElProduceSurfaceMapper elProduceSurfaceMapper;

    public boolean update(ElProduceSurface elProduceSurface) {
        return elProduceSurfaceMapper.update(elProduceSurface) > 0;
    }

    public boolean create(ElProduceSurface surface) {
        return elProduceSurfaceMapper.create(surface) > 0;
    }

    public List<ElProduceSurface> findByCondition(ElProduceSurface surfaceI) {

        List<ElProduceSurface> surfaces = elProduceSurfaceMapper.findByCondition(surfaceI);

        return surfaces;
    }
}
