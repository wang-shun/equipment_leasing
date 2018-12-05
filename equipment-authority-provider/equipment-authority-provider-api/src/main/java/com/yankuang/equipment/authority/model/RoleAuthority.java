package com.yankuang.equipment.authority.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "el_role_authority_mapping")
@Data
public class RoleAuthority implements Serializable {

    private Long id;

    private String roleCode;

    private String authorityCode;

    private Byte status;

    private String projectCode;

}