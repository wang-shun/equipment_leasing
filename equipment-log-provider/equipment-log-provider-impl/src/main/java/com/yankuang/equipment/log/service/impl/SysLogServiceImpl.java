package com.yankuang.equipment.log.service.impl;

import com.yankuang.equipment.log.mapper.SysLogMapper;
import com.yankuang.equipment.log.model.SysLog;
import com.yankuang.equipment.log.service.SysLogService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xhh
 * @date 2018/8/10 14:09
 */
@Service
@RpcProvider
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    private SysLogMapper sysLogMapper;

    public boolean add(SysLog syslog) {

        return sysLogMapper.create(syslog);
    }
}
