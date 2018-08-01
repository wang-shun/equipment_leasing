package com.yankuang.equipment.common.base;

import org.apache.log4j.Logger;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
/**
 * Created by zhouy on 2018/7/31.
 */
public class BaseService {
    public static final Logger logger = Logger.getLogger(BaseService.class);
    /**
     * 事物回滚
     */
    protected void rollback() {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}
