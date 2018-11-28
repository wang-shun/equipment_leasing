package com.yankuang.equipment.web.task;

import com.yankuang.equipment.equipment.service.ReportEquipmentZMonthService;
import com.yankuang.equipment.equipment.service.ZjDepreciationCostReportItemService;
import com.yankuang.equipment.equipment.service.ZjDepreciationCostReportService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configurable
@EnableScheduling
public class ReportZjDepreciationCostTask {

    public static final Logger logger = Logger.getLogger(ReportZjDepreciationCostTask.class);

    @RpcConsumer
    ZjDepreciationCostReportService zjDepreciationCostReportService;

//    @Scheduled(cron = "0 0 3 21 * ?")
    public void calDepreciationCostReport(){
        logger.info("......................汇总各矿上月的综机折旧修理费开始......................");
        try{
            zjDepreciationCostReportService.creatDepCostReport();
//            zjDepreciationCostReportItemService.CalReportEquipmentZMonth();
        }catch (Exception e){
            logger.info("......................汇总各矿上月的综机折旧修理费异常......................");
            e.printStackTrace();
        }
        logger.info("......................汇总各矿上月的综机折旧修理费结束......................");
    }

}
