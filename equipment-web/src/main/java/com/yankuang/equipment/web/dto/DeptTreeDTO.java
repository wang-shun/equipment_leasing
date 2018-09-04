package com.yankuang.equipment.web.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class DeptTreeDTO {

    private String name;

    private String code;

    private String pcode;

    private Long sorting;

    private String remark;

    private String createBy;

    private Byte type;

    private String address;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;

    private List<DeptTreeDTO> childList;

}
