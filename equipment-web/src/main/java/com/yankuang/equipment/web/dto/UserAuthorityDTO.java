package com.yankuang.equipment.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserAuthorityDTO {

    private String userCode;

    private List<String> authorityCodes;

}
