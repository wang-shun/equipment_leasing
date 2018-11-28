package com.yankuang.equipment.authority.service.impl;

import com.yankuang.equipment.authority.mapper.CodeMapper;
import com.yankuang.equipment.authority.service.CodeService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RpcProvider
public class CodeServiceImpl implements CodeService {

    @Autowired
    CodeMapper codeMapper;

    public Long findIdMax(Map map) {
        return codeMapper.findIdMax(map);
    }


}
