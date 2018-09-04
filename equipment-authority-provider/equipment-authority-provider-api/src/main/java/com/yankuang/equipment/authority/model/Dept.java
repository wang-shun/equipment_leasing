package com.yankuang.equipment.authority.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "el_department")
@Data
public class Dept implements Serializable {

    private Long id;

    private String name;

    private String code;

    private String pcode;

    private Long level;

    private Long sorting;

    private String remark;

    private String createBy;

    private Object beginTime;

    private Long createAt;

    private String updateBy;

    private Long updateAt;

    private Long version;

    private Byte status;

    private String projectCode;
}
