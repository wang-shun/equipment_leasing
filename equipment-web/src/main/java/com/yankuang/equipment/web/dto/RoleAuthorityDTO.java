package com.yankuang.equipment.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleAuthorityDTO {

    private String roleCode;

    private List<String> authorityCodes;

}
