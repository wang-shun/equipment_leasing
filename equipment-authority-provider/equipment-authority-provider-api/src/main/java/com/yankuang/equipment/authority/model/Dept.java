package com.yankuang.equipment.authority.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createAt;

    private String updateBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateAt;

    private Long version;

    private Byte status;

    private String projectCode;

    private Byte type;

    private String address;

}
