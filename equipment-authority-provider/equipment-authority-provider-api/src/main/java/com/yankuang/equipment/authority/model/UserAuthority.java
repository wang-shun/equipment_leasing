package com.yankuang.equipment.authority.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "el_user_authority_mapping")
@Data
public class UserAuthority implements Serializable {

    private Long id;

    private String userCode;

    private String authorityCode;

    private Byte status;

    private String projectCode;

}