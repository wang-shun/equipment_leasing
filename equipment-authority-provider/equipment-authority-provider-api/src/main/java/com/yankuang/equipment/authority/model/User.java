package com.yankuang.equipment.authority.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "el_user")
@Data
public class User implements Serializable {

    private Long id;

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

    private String projectCode;

}
