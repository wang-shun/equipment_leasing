package com.yankuang.equipment.web.dto;

import java.util.List;


public class AuthorityDTO {

    private Long id;

    private Long pId;

    private Long type;

    private String name;

    private String url;

    private Long level;


    private List<AuthorityDTO> childList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public List<AuthorityDTO> getChildList() {
        return childList;
    }

    public void setChildList(List<AuthorityDTO> childList) {
        this.childList = childList;
    }
}
