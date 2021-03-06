package com.yankuang.equipment.authority.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "el_user")
@Data
public class User implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String account;

    private String token;

    private String telephone;

    private String mail;

    private String password;

    private Byte sex;

    private Byte status;

    private String remark;

    private String createBy;

    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;

    private Long sorting;

    private Long version;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt;

    private String projectCode;

    private Long deptId;

    private String deptCode;

    private String deptName;

    private List<ResultSmall> roles;

    private List<ResultSmall> authorities;

    private List<String> roleCodes;

}
