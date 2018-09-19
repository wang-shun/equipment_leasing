package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.ElFeeDetailTMapper;
import com.yankuang.equipment.equipment.model.ElFeeDetailT;
import com.yankuang.equipment.equipment.service.ElFeeDetailTService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return elFeeDetailTMapper.insertBatch(list);
    }
}
