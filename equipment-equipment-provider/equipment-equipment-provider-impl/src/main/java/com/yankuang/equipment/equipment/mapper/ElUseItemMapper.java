package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElUseItem;
import org.springframework.stereotype.Repository;

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
}
