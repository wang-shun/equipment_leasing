package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ZjSbUseItem;
import com.yankuang.equipment.equipment.model.ZjSbUseItemSign;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ZjSbUseItemSignMapper {

    Integer create(ZjSbUseItemSign zjSbUseItemSign);

    Integer findByHandoverTime(String handover);

}
