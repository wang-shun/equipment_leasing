package com.yankuang.equipment.web.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserDTO implements Serializable {

    private Long id;

    private String name;

    private String projectCode;

    private String token;

    private List<RoleDTO> roles;

    private List<AuthorityTreeDTO> authoritys;

}
