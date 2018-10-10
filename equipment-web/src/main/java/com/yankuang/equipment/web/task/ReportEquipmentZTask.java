package com.yankuang.equipment.web.task;

import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.authority.service.DeptService;
import com.yankuang.equipment.equipment.model.*;
import com.yankuang.equipment.equipment.service.ElUseItemService;
import com.yankuang.equipment.equipment.service.SbElFeeService;
import com.yankuang.equipment.equipment.service.SbPositionService;
import com.yankuang.equipment.equipment.service.ZEquipmentReportService;
import com.yankuang.equipment.web.util.UserFromRedis;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Configurable
@EnableScheduling
public class ReportEquipmentZTask {

    public static final Logger logger = Logger.getLogger(ReportEquipmentTTask.class);

    @RpcConsumer
    ElUseItemService elUseItemService;

    @RpcConsumer
    ZEquipmentReportService zEquipmentReportService;

    @RpcConsumer
    SbElFeeService sbElFeeService;

    @Autowired
    UserFromRedis userFromRedis;

    @RpcConsumer
    DeptService deptService;

    @RpcConsumer
    SbPositionService sbPositionService;
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
        Map map = new HashMap();
        map.put("type", "3");
        map.put("pcode","35");
        SbPosition sbPosition = new SbPosition();
        List<Dept> deptList = deptService.findByPage(1, 100, map).getList();
        for (Dept dept : deptList) {
            sbPosition.setPosition(dept.getId().toString());
            sbPosition.setPosition("01");
            List<SbPosition> sbPositions = sbPositionService.list(sbPosition,1,1000).getList();
            for (SbPosition sbPosition1:sbPositions) {
                DtkList dtkList = new DtkList();
                dtkList.setUseYear(year + "");
                dtkList.setUseMonth(month + "");
                dtkList.setCenterYear(Integer.parseInt(dtkList.getUseYear()));
                dtkList.setCenterMonth(Integer.parseInt(dtkList.getUseMonth()));
                if (dept.getCode() == null || "".equals(dept.getCode())) {
                    return;
                }
                dtkList.setUseDeptCode(dept.getCode());
                dtkList.setDeptCode(dept.getCode());
                dtkList.setDeptName(dept.getName());
                dtkList.setEquipmentEffect(sbPosition1.getName());

                //获取满足条件的领用记录
                List<DtkList> dtkListLYs = elUseItemService.findReportLY(dtkList);
                if (dtkListLYs == null || dtkListLYs.size() <= 0) {
                    return;
                }
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
                    if (dtkListLY.getSign() == null || "".equals(dtkListLY.getSign())) {
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
                dtkList.setSum(sum);

                if (elUseItemService.findKB(dtkList)) {
                    dtkList.setKb((byte) 1);
                } else {
                    dtkList.setKb((byte) 2);
                }

                List<ListZReportItem> listZReportItems = new ArrayList<>();
                ListZReportItem listZReportItem = new ListZReportItem();

                //判断清单列表是否有数据
                if (listZReportItems == null || listZReportItems.size() <= 0) {
                    for (DtkList dtkList1 : dtkListLYs) {
                        BeanUtils.copyProperties(dtkList1, listZReportItem);
                        if (listZReportItem == null) {
                            continue;
                        }
                        listZReportItem.setName(listZReportItem.getSbName());
                        listZReportItems.add(listZReportItem);
                    }
                }
                ListZReport listZReport = new ListZReport();
                //实体类属性数据转换到dto中
                BeanUtils.copyProperties(dtkList, listZReport);
                zEquipmentReportService.create(listZReport, listZReportItems);
            }
        }
    }
}
