package com.yankuang.equipment.authority.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "el_role")
@Data
public class Role implements Serializable {

    private Long id;

    private String code;

    private String pcode;

    private String name;

    private Long type;

    private Long sorting;

    private Byte status;

    private String remark;

    private String createBy;

    private Long updateAt;

    private String updateBy;

    private Long level;

    private Long createAt;

    private Long version;

    private String projectCode;


}
