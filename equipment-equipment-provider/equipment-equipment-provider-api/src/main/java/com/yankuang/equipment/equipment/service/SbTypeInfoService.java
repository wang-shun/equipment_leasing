package com.yankuang.equipment.equipment.service;

import com.yankuang.equipment.equipment.model.SbTypeInfo;

public interface SbTypeInfoService {

    public SbTypeInfo selectByPrimaryKey(Long id);

    public void insertSbTypeInfo(SbTypeInfo record);

    public void updateSbTypeInfo(SbTypeInfo record);
}
