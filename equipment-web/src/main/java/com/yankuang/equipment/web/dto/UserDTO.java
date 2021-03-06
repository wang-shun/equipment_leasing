package com.yankuang.equipment.web.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDTO implements Serializable {

    private Long id;

    private String code;

    private String name;

    private String projectCode;

    private String token;

    private Long deptId;

    private String deptCode;

    private String deptName;

    private List<RoleDTO> roles;

    private List<AuthorityTreeDTO> authoritys;

}
