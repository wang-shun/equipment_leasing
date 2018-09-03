package com.yankuang.equipment.web.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserIn implements Serializable {

    private Long id;

    private String username;

    private String name;

    private String token;

    private String telephone;

    private String mail;

    private String password;

    private Byte sex;

    private Byte status;

    private String remark;

    private String createBy;

    private String updateBy;

    private Date updateAt;

    private Long sorting;

    private Long version;

    private Date createAt;

    private String code;

    private String account;

    private String deptCode;

    private String roleCode;

}
