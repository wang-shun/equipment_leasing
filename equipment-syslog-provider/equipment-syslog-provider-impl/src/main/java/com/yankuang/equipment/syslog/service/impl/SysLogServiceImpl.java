package com.yankuang.equipment.syslog.service.impl;

import com.yankuang.equipment.syslog.mapper.SysLogMapper;
import com.yankuang.equipment.syslog.model.SysLog;
import com.yankuang.equipment.syslog.service.SysLogService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xhh
 * @date 2018/8/11 16:27
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
