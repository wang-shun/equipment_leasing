package com.yankuang.equipment.web.dto;

import java.util.List;

public class IdsDTO {

    private List<Long> ids;

    private Long id;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
