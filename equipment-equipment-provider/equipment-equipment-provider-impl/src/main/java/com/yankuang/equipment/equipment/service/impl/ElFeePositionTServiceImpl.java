package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.ElFeePositionTMapper;
import com.yankuang.equipment.equipment.model.ElFeePositionT;
import com.yankuang.equipment.equipment.service.ElFeePositionTService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RpcProvider
@Transactional
public class ElFeePositionTServiceImpl implements ElFeePositionTService {

    @Autowired
    ElFeePositionTMapper elFeePositionTMapper;

    public List<ElFeePositionT> list(ElFeePositionT elFeePositionT) {
        return elFeePositionTMapper.list(elFeePositionT);
    }
}
