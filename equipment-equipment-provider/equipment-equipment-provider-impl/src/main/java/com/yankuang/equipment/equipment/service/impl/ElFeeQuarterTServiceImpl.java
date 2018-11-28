package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.ElFeeQuarterTMapper;
import com.yankuang.equipment.equipment.model.ElFeeQuarterT;
import com.yankuang.equipment.equipment.service.ElFeeQuarterTService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RpcProvider
@Transactional
public class ElFeeQuarterTServiceImpl implements ElFeeQuarterTService {

    @Autowired
    ElFeeQuarterTMapper elFeeQuarterTMapper;

    public List<ElFeeQuarterT> findList(ElFeeQuarterT elFeeQuarterT) {
        return elFeeQuarterTMapper.list(elFeeQuarterT);
    }

    public boolean createBatch(List<ElFeeQuarterT> list) {

        ElFeeQuarterT elFeeQuarterT = new ElFeeQuarterT();
        for (ElFeeQuarterT quarterT : list) {
            if (quarterT != null
                    && !StringUtils.isEmpty(quarterT.getReportYear())
                    && !StringUtils.isEmpty(quarterT.getPositionCode())
                    && !StringUtils.isEmpty(quarterT.getReportQuarter())) {
                elFeeQuarterT = quarterT;
                break;
            }
        }

        boolean flag = elFeeQuarterTMapper.history(elFeeQuarterT) >= 0;
        return flag && elFeeQuarterTMapper.insertBatch(list) > 0;
    }
}
