package com.yankuang.equipment.web.dto;

import java.io.Serializable;
import java.util.List;

public class AuthorityGroupDTO implements Serializable {

    private Long id;

    private String name;

    private List<Long> ids;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    @Override
    public String toString() {
        return "{AuthorityGroupDTO:{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ids=" + ids +
                '}'+ '}';
    }
}
