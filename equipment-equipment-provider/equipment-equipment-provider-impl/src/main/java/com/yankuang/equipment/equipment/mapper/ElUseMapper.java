package com.yankuang.equipment.equipment.mapper;

import com.yankuang.equipment.equipment.model.ElUse;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ElUseMapper {
    Integer create(ElUse elUse);

    Integer update(ElUse elUse);

    ElUse select(Long id);

    Integer delete(Long id);

    List<ElUse> list(Map elUse);

    Integer open(Long id);

//    Integer noPass(Long id);
//
//    Integer pass(Long id);
}
