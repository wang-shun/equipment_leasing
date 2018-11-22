package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.ElFeePositionTMapper;
import com.yankuang.equipment.equipment.model.ElFeePositionT;
import com.yankuang.equipment.equipment.model.ElFeeQuarterT;
import com.yankuang.equipment.equipment.service.ElFeePositionTService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

    public boolean createBatch(List<ElFeePositionT> list) {
        ElFeePositionT elFeePositionT = new ElFeePositionT();
        for (ElFeePositionT positionT : list) {
            if (positionT != null
                    && !StringUtils.isEmpty(positionT.getReportYear())
                    && !StringUtils.isEmpty(positionT.getReportMonth())) {
                elFeePositionT = positionT;
                break;
            }
        }
        boolean flag = elFeePositionTMapper.history(elFeePositionT) >= 0;
        return flag && elFeePositionTMapper.createBatch(list);
    }
}
