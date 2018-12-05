package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.ElFeeDetailTMapper;
import com.yankuang.equipment.equipment.model.ElFeeDetailT;
import com.yankuang.equipment.equipment.service.ElFeeDetailTService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RpcProvider
public class ElFeeDetailTServiceImpl implements ElFeeDetailTService {

    @Autowired
    ElFeeDetailTMapper elFeeDetailTMapper;

    public List<ElFeeDetailT> findElFeeDetailTs(ElFeeDetailT elFeeDetailT, Integer pageNum, Integer pageSize) {

        if (pageNum == null || pageSize == null) {
            List<ElFeeDetailT> list = elFeeDetailTMapper.list(elFeeDetailT);
            return list;
        } else {
            PageHelper.startPage(pageSize, pageNum);
            List<ElFeeDetailT> list = elFeeDetailTMapper.list(elFeeDetailT);
            PageInfo<ElFeeDetailT> pageInfo = new PageInfo<ElFeeDetailT>(list);
            return pageInfo.getList();
        }
    }

    public boolean createBatch(List<ElFeeDetailT> list) {

        ElFeeDetailT elFeeDetailT = new ElFeeDetailT();
        for (ElFeeDetailT detailT: list) {
            if (detailT != null
                    && !StringUtils.isEmpty(detailT.getPositionCode())
                    && !StringUtils.isEmpty(detailT.getReportYear())
                    && !StringUtils.isEmpty(detailT.getReportMonth())) {
                elFeeDetailT.setPositionCode(detailT.getPositionCode());
                elFeeDetailT.setReportMonth(detailT.getReportMonth());
                elFeeDetailT.setReportYear(detailT.getReportYear());
                break;
            }
        }
        Map<String, List<ElFeeDetailT>> detailTMap = new HashMap<String, List<ElFeeDetailT>>();
        for (ElFeeDetailT detailT : list) {
            if (detailT == null) {
                continue;
            }
            List<ElFeeDetailT> tempList = detailTMap.get(detailT.getPositionCode());
            if (tempList == null) {
                tempList = new ArrayList<ElFeeDetailT>();
                tempList.add(detailT);
                detailTMap.put(detailT.getPositionCode(), tempList);
            } else {
                tempList.add(detailT);
            }
        }
        boolean flag = false;
        for (String positionCode : detailTMap.keySet()) {
            elFeeDetailT.setPositionCode(positionCode);
            flag = elFeeDetailTMapper.history(elFeeDetailT) >= 0;
            if (!flag) break;
        }

        return flag && elFeeDetailTMapper.insertBatch(list) > 0;
    }
}
