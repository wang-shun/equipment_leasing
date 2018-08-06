package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.SbType;

import java.util.List;

public interface SbTypeService {

    public List<SbType> selectAllSbTypes();

    public void insertSbType(SbType record);
}
