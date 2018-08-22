package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.Authority;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AuthorityMapper {

    List<Authority> list(Map role);

    Authority findByName(String name);

    List<Authority> findAll();

    Authority findById(Long id);

    boolean update(Authority role);

    boolean create(Authority role);

    boolean delete(Long id);

    boolean deletes(List<Long> ids);
}
