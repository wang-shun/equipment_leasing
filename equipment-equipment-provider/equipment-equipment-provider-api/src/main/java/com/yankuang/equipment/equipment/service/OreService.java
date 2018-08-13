package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.ElOre;

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
}
