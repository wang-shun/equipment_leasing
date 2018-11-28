package com.yankuang.equipment.syslog.service.impl;

import com.yankuang.equipment.syslog.model.SysLog;
import com.yankuang.equipment.syslog.service.SysLogService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xhh
 * @date 2018/8/11 16:27
 */
@Service
@RpcProvider
public class SysLogServiceImpl implements SysLogService {


    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean add(SysLog syslog) {
        mongoTemplate.insert(syslog);
        return true;
    }
}
