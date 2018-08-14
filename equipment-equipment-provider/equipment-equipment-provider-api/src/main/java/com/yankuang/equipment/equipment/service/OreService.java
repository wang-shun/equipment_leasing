package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ElOre;

import java.util.List;

/**
 * Created by boms on 2018/8/13.
 */
public interface OreService {
    /**
     * @method 矿组织添加
     * @param elOre
     * @return
     */
    Boolean create(ElOre elOre);

    /**
     * @method 更新矿组织信息
     * @param elOre
     * @return
     */
    Boolean update(ElOre elOre);

    /**
     * @method 删除矿组织
     * @param id
     * @return
     */
    Boolean delete(Long id);

    /**
     * @method 根据id查询类型
     * @param id
     * @return
     */
    Byte findType(Long id);

    /**
     * @method 删除矿单位、矿单位分区
     * @param id
     * @return
     */
    Boolean deleteOre(Long id);

    /**
     * @method 查询矿单位分区id
     * @param id
     * @return
     */
    List<Long> getId(Long id);

    /**
     * @method 查询列表对象
     * @param id
     * @return
     */
    ElOre findById(Long id);

    /**
     * @method 通过pid查询子类列表
     * @param id
     * @return
     */
    List<ElOre> findByPId(Long id);
}
