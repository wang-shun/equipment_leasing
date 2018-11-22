package com.yankuang.equipment.web.task;

import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.authority.service.DeptService;
import com.yankuang.equipment.equipment.model.ElFeeDetailT;
import com.yankuang.equipment.equipment.model.ElFeeMiddleT;
import com.yankuang.equipment.equipment.service.ElFeeDetailTService;
import com.yankuang.equipment.equipment.service.ElFeeMiddleTService;
import com.yankuang.equipment.web.service.ReportTPlusService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Component
@Configurable
@EnableScheduling
public class ReportEquipmentTTask {

    public static final Logger logger = Logger.getLogger(ReportEquipmentTTask.class);

    @RpcConsumer
    ElFeeDetailTService elFeeDetailTService;
    @RpcConsumer
    ElFeeMiddleTService elFeeMiddleTService;
    @RpcConsumer
    DeptService deptService;
    @Autowired
    ReportTPlusService reportTPlusService;

    @Scheduled(cron = "0 0 0 21 * ?")
    public void calReportEquipmentZMonth(){
        logger.info("......................当月的通用设备租赁费明细和中类汇总开始......................");
        try{
            Calendar cal = Calendar.getInstance();
            String reportYear = cal.get(Calendar.YEAR)+"";
            String reportMonth = String.format("%02d", cal.get(Calendar.MONTH )+1)+"";
            Map deptMap = new HashMap();
            deptMap.put("type", 2);
            deptMap.put("pcode","100120104");
            List<Dept> deptList = deptService.findByPage(1, 100, deptMap).getList();
            List<ElFeeDetailT> elFeeDetailTS = new ArrayList<>();
            for (Dept dept : deptList) {
                if (dept != null && !StringUtils.isEmpty(dept.getCode())) {
                    List<ElFeeDetailT> elFeeDetailTList = reportTPlusService.getElFeeDetailTS(dept.getCode(), reportYear, reportMonth);
                    if (elFeeDetailTList != null) elFeeDetailTS.addAll(elFeeDetailTList);
                }
            }
            boolean resDetail = elFeeDetailTService.createBatch(elFeeDetailTS);
            List<ElFeeMiddleT> elFeeMiddleTS = new ArrayList<>();
            for (Dept dept : deptList) {
                if (dept != null && !StringUtils.isEmpty(dept.getCode())) {
                    List<ElFeeMiddleT> elFeeMiddleTList = reportTPlusService.getElFeeMiddleTS(dept.getCode(), reportYear, reportMonth);
                    if (elFeeMiddleTList != null) elFeeMiddleTS.addAll(elFeeMiddleTList);
                }
            }
            boolean resMiddle = elFeeMiddleTService.createBatch(elFeeMiddleTS);
            if (!(resDetail && resMiddle)) {
                logger.info("......................当月的通用设备租赁费明细和中类汇总失败......................");
            } else {
                logger.info("......................当月的通用设备租赁费明细和中类汇总成功......................");
            }

        }catch (Exception e){
            logger.info("......................当月的通用设备租赁费明细和中类汇总异常......................");
            e.printStackTrace();
        }
        logger.info("......................当月的通用设备租赁费明细和中类汇总结束......................");
    }
}
