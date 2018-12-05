package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ZjSbUseItemSign;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ZjSbUseItemSignMapper {

    Integer create(ZjSbUseItemSign zjSbUseItemSign);

    Integer historySign(@Param("handover") String handover);

    Integer findByHandoverTime(String handover);

}
