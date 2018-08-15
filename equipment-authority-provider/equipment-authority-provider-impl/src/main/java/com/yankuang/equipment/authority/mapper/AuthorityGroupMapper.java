package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.AuthorityGroup;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AuthorityGroupMapper {
    AuthorityGroup findByName(String name);

    Boolean delete(Long id);

    Boolean create(AuthorityGroup t);

    Boolean update(AuthorityGroup t);

    AuthorityGroup findById(Long id);

    List<AuthorityGroup> list(Map<?, ?> criteria);
}
