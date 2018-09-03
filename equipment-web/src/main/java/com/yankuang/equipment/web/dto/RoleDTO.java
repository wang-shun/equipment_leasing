package com.yankuang.equipment.web.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleDTO implements Serializable {

    private Long id;

    private String name;

    private String code;

}
