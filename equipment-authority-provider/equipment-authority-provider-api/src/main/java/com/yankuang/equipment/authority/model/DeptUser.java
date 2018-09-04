package com.yankuang.equipment.authority.model;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "el_department_user_mapping")
@Data
public class DeptUser implements Serializable {

    private Long id;

    private String deptCode;

    private String userCode;

    private Byte status;

    private String projectCode;

}