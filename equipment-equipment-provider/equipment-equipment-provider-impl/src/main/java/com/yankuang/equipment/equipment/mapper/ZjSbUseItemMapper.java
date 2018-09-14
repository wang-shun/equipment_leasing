package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ZjSbUseItem;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ZjSbUseItemMapper {

    List<ZjSbUseItem> listEquipmentReceipt(Map zjSbUseItem);

    Integer create(ZjSbUseItem zjSbUseItem);

}
