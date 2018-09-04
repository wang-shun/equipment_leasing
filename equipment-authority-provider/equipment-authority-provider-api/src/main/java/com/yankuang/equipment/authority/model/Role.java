package com.yankuang.equipment.authority.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateAt;

    private String updateBy;

    private Long level;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createAt;

    private Long version;

    private String projectCode;


}
