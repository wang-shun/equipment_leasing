package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ElSurfaceParam;

import java.util.List;

/**
 * @Auther: zyy
 * @Date: 2018-10-11 16:29
 * @Description:
 */
public interface ElSurfaceParamService {
    boolean create(ElSurfaceParam elSurfaceParam);

    ElSurfaceParam findByCode(String code);

    List<ElSurfaceParam> findByCondition(ElSurfaceParam elSurfaceParam);

    ElSurfaceParam findById(Long id);

    boolean update(ElSurfaceParam param);

    boolean delete(Long id);
}
