package com.yankuang.equipment.equipment.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.equipment.mapper.ElFeeMiddleTMapper;
import com.yankuang.equipment.equipment.model.ElFeeMiddleT;
import com.yankuang.equipment.equipment.service.ElFeeMiddleTService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RpcProvider
@Transactional
public class ElFeeMiddleTServiceImpl implements ElFeeMiddleTService {

    @Autowired
    ElFeeMiddleTMapper elFeeMiddleTMapper;

    public List<ElFeeMiddleT> findElFeeMiddleTs(ElFeeMiddleT elFeeMiddleT, Integer pageNum, Integer pageSize) {

        if (pageNum == null || pageSize == null) {
            return elFeeMiddleTMapper.list(elFeeMiddleT);
        } else {
            PageHelper.startPage(pageSize, pageNum);
            List<ElFeeMiddleT> list = elFeeMiddleTMapper.list(elFeeMiddleT);
            PageInfo<ElFeeMiddleT> pageInfo = new PageInfo<ElFeeMiddleT>(list);
            return pageInfo.getList();
        }
    }

    public boolean createBatch(List<ElFeeMiddleT> list) {
        return elFeeMiddleTMapper.createBatch(list) > 0;
    }
}
