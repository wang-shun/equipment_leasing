package com.yankuang.equipment.authority.mapper;

import com.yankuang.equipment.authority.model.Authority;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AuthorityMapper {

    boolean create(Authority authority);

    boolean delete(List<String> codes);

    boolean update(Authority authority);

    Authority findByCode(String code);

    Authority findByName(String name);

    List<Authority> findByUserCode(String code);

    List<Authority> list(Map map);

}
