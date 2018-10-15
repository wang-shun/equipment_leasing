package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.common.util.FeeUtils;
import com.yankuang.equipment.common.util.UuidUtils;
import com.yankuang.equipment.equipment.mapper.ElSurfaceParamMapper;
import com.yankuang.equipment.equipment.model.ElSurfaceParam;
import com.yankuang.equipment.equipment.service.ElSurfaceParamService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Auther: zyy
 * @Date: 2018-10-11 16:33
 * @Description:
 */
@Service
@RpcProvider
@Transactional
public class ElSurfaceParamServiceImpl implements ElSurfaceParamService {

    @Autowired
    ElSurfaceParamMapper elSurfaceParamMapper;

    public boolean create(ElSurfaceParam elSurfaceParam) {
        elSurfaceParam.setCode(UuidUtils.newUuid());
        return elSurfaceParamMapper.create(elSurfaceParam) > 0;
    }

    public ElSurfaceParam findByCode(String code) {
        return elSurfaceParamMapper.findByCode(code);
    }

    public List<ElSurfaceParam> findByCondition(ElSurfaceParam elSurfaceParam) {
        List<ElSurfaceParam> parmas = elSurfaceParamMapper.list(elSurfaceParam);
        for (ElSurfaceParam param : parmas) {
            param.setSurfaceHigh(FeeUtils.scale(param.getSurfaceHigh(), 2));
            param.setSurfaceLength(FeeUtils.scale(param.getSurfaceLength(), 2));
            param.setSurfaceReserves(FeeUtils.scale(param.getSurfaceReserves(), 2));
            param.setSurfaceTrend(FeeUtils.scale(param.getSurfaceTrend(), 2));
        }
        return parmas;
    }

    public ElSurfaceParam findById(Long id) {
        return elSurfaceParamMapper.findById(id);
    }

    public boolean update(ElSurfaceParam param) {
        return elSurfaceParamMapper.update(param) >= 0;
    }

    public boolean delete(Long id) {
        return elSurfaceParamMapper.delete(id) > 0;
    }
}
