package com.yankuang.equipment.authority.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Table(name = "el_authority")
@Data
@Getter
@Setter
public class Authority implements Serializable {

    private Long id;

    private String code;

    @NotEmpty(message = "父级权限pcode不能为空!")
    private String pcode;

    @NotEmpty(message = "权限name不能为空!")
    private String name;

    @NotEmpty(message = "权限type不能为空!")
    private Long type;

    private String url;

    private String icon;

    @NotEmpty(message = "权限sorting不能为空!")
    private Long sorting;

    private Long level;

    private String remark;

    private Byte status;

    private Long version;

    private String updateBy;

    private String createBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateAt;

//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createAt;

    private String projectCode;

    private String pname;

    private List<Authority> childList;

}