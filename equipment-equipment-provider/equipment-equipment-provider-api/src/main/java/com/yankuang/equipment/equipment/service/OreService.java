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
}
