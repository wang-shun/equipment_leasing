package com.yankuang.equipment.equipment.mapper;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.model.DtkList;
import com.yankuang.equipment.equipment.model.ElUseItem;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface ElUseItemMapper {
    Integer create(ElUseItem elUseItem);

    Integer update(ElUseItem elUseItem);

    List<ElUseItem> list(Map elUseItem);

    ElUseItem findById(Long itemId);

    Integer delete(Long useId);

    Integer deleteById(Long itemId);

    List<ElUseItem> findByUseId(Long useId);

    ElUseItem findByCondition(Map map);

    Integer updateByEquipmentId(ElUseItem elUseItem);

    List<DtkList> dtkReport(DtkList dtkList);

    Date selectTime(DtkList dtkList);

}
