package com.yankuang.equipment.web.task;

import com.yankuang.equipment.equipment.model.DtkList;
import com.yankuang.equipment.equipment.service.ElUseItemService;
import com.yankuang.equipment.equipment.service.SbElFeeService;
import com.yankuang.equipment.equipment.service.ZEquipmentReportService;
import com.yankuang.equipment.web.util.UserFromRedis;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component
@Configurable
@EnableScheduling
public class ReportEquipmentZTask {

    public static final Logger logger = Logger.getLogger(ReportEquipmentTTask.class);

    @RpcConsumer
    ElUseItemService elUseItemService;

    @RpcConsumer
    SbElFeeService sbElFeeService;

    /**
     * 定时生成综机列表清单
     * @throws ParseException
     */
    @Scheduled(cron = "0 0 0 20 * ?")
    public void save() throws ParseException {
        Long day;
        Double sum = 0.0;
        Date date = new Date();

        Calendar chargingDate = Calendar.getInstance();
        chargingDate.setTime(date);
        int month = chargingDate.get(Calendar.MONTH);
        int year = chargingDate.get(Calendar.YEAR);

        DtkList dtkList = new DtkList();

        dtkList.setUseYear(month + "");
        dtkList.setUseMonth(year + "");
        dtkList.setCenterYear(Integer.parseInt(dtkList.getUseYear()));
        dtkList.setCenterMonth(Integer.parseInt(dtkList.getUseMonth()));

        //获取满足条件的领用记录
        List<DtkList> dtkListLYs = elUseItemService.findReportLY(dtkList);
        Date endDate = new SimpleDateFormat("yyyy-M-dd").parse(year + "-" + month + "-20");
        Date startDate = new SimpleDateFormat("yyyy-M-dd").parse(year + "-" + (month - 1) + "-21");
        //循环获取
        for (DtkList dtkListLY : dtkListLYs) {
            Double isNew = sbElFeeService.CalDayElFeeA3Z_rate(dtkListLY.getUseId(), dtkListLY.getEquipmentId());
            //判断是否是新设备
            if (isNew > 1) {
                dtkListLY.setIsNew((byte) 1);
            } else {
                dtkListLY.setIsNew((byte) 2);
            }
            if (dtkListLY.getSign() == null || "".equals(dtkListLY)) {
                day = sbElFeeService.CalEquipmentElDays(dtkListLY.getUseId(), null, dtkListLY.getEquipmentId(), startDate, endDate);
                //获取收费期限
                dtkListLY.setFeeDay(new SimpleDateFormat("yyyy-MM-dd").format(dtkListLY.getUseAt()) + "下");
            } else {
                dtkListLY.setStartDate(startDate);
                DtkList findSign = elUseItemService.findSign(dtkListLY);
                if (findSign == null) {
                    continue;
                }
                //获取收费期限
                dtkListLY.setFeeDay(new SimpleDateFormat("yyyy-MM-dd").format(dtkListLY.getUseAt()) + "-" + new SimpleDateFormat("yyyy-MM-dd").format(findSign.getUseAt()));
                day = sbElFeeService.CalEquipmentElDays(dtkListLY.getUseId(), findSign.getUseId(), dtkListLY.getEquipmentId(), startDate, endDate);
            }

            if (dtkListLY.getCostA1() == null) {
                return;
            }
            if (dtkListLY.getEquipmentNum() == null) {
                return;
            }
            Double costA1Fee = dtkListLY.getCostA1() * dtkListLY.getEquipmentNum() * day;
            sum += costA1Fee;
            dtkListLY.setCostA1Fee(costA1Fee);
            dtkListLY.setSum(costA1Fee);
            dtkListLY.setDay(day);
            dtkList.setUseAt(dtkListLY.getUseAt());
        }
    }
}
