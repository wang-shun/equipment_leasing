package com.yankuang.equipment.authority.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "el_department_role_mapping")
@Data
public class DeptRole implements Serializable {

    private Long id;

    private String roleCode;

    private String deptCode;

    private Byte status;

    private String projectCode;

}