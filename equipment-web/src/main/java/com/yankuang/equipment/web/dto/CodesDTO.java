package com.yankuang.equipment.web.dto;

import lombok.Data;

import java.util.List;

@Data
public class CodesDTO {

    private List<String> codes;

    private String code;

    private List<Long> ids;

    private Long id;

}
