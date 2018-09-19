package com.yankuang.equipment.web.task;

import com.yankuang.equipment.equipment.service.ReportEquipmentZMonthService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configurable
@EnableScheduling
public class ReportEquipmentZMonthTask {

    public static final Logger logger = Logger.getLogger(ReportEquipmentZMonthTask.class);

    @RpcConsumer
    ReportEquipmentZMonthService reportEquipmentZMonthService;

    @Scheduled(cron = "0 0 3 21 * ?")
    public void calReportEquipmentZMonth(){
        logger.info("......................汇总各矿上月的综机租赁费开始......................");
        try{
            reportEquipmentZMonthService.CalReportEquipmentZMonth();
        }catch (Exception e){
            logger.info("......................汇总各矿上月的综机租赁费异常......................");
            e.printStackTrace();
        }
        logger.info("......................汇总各矿上月的综机租赁费结束......................");
    }

}
