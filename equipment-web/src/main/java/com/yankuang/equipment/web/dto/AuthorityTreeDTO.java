package com.yankuang.equipment.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class AuthorityTreeDTO {

    private String code;

    private String pcode;

    private Long type;

    private String name;

    private String url;

    private Long level;

    private Long sorting;

    private String icon;

    private List<AuthorityTreeDTO> childList;

}
