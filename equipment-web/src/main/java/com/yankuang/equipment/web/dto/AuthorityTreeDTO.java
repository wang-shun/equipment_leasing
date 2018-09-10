package com.yankuang.equipment.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createAt;

    private String remark;

    private List<AuthorityTreeDTO> childList;

}
