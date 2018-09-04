package com.yankuang.equipment.authority.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "el_role_user_mapping")
@Data
public class RoleUser implements Serializable {
    private Long id;

    private String roleCode;

    private String userCode;

    private Byte status;

    private String projectCode;

}