package com.yankuang.equipment.web.restful;

import com.alibaba.fastjson.JSON;
import com.yankuang.equipment.authority.service.DeptService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.FeeUtils;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.equipment.model.ElFeeMiddleT;
import com.yankuang.equipment.equipment.model.ElFeeQuarterT;
import com.yankuang.equipment.equipment.service.ElFeeMiddleTService;
import com.yankuang.equipment.equipment.service.ElFeeQuarterTService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/elFeeQuarterT")
public class ElFeeQuarterTController {

    @RpcConsumer
    ElFeeQuarterTService elFeeQuarterTService;
    @RpcConsumer
    ElFeeMiddleTService elFeeMiddleTService;
    @RpcConsumer
    DeptService deptService;

    @GetMapping
    public CommonResponse findList(ElFeeQuarterT elFeeQuarterT){

        try {
            // 查询历史记录
            if (elFeeQuarterT == null || StringUtils.isEmpty(elFeeQuarterT.getPositionCode())) {
                return CommonResponse.errorMsg("矿处单位不得为空");
            }
            if (elFeeQuarterT == null || StringUtils.isEmpty(elFeeQuarterT.getExportAtStr())) {
                return CommonResponse.errorMsg("导出时间不得为空");
            }
            String reportYear = elFeeQuarterT.getExportAtStr().split("-")[0];
            String reportQuarter = elFeeQuarterT.getExportAtStr().split("-")[1];
            elFeeQuarterT.setReportYear(reportYear);
            elFeeQuarterT.setReportQuarter(reportQuarter);
            List<ElFeeQuarterT> historyList = elFeeQuarterTService.findList(elFeeQuarterT);
            if (historyList != null && historyList.size() > 0) {
                return CommonResponse.ok(historyList);
            }

            // 联表查询
            int year = Integer.valueOf(reportYear);
            int quarter = Integer.valueOf(reportQuarter);
            List<Integer> monthList = new ArrayList<>();
            List<String> reportMonthList = new ArrayList<>();
            List<Long> dayList = new ArrayList<>();
            List<ElFeeMiddleT> elFeeMiddleTiList = new ArrayList<>();
            for (int i=0; i<3; i++) {
                int month = (quarter-1)*3+1+i;
                monthList.add(month);
                dayList.add(monthOfdays(year, month));
                String reportMonth = String.format("%02d", month);
                reportMonthList.add(reportMonth);
                ElFeeMiddleT elFeeMiddleT = new ElFeeMiddleT();
                elFeeMiddleT.setPositionCode(elFeeQuarterT.getPositionCode());
                elFeeMiddleT.setReportYear(reportYear);
                elFeeMiddleT.setReportMonth(reportMonth);
                elFeeMiddleT.setMiddleCode("0000");
                ElFeeMiddleT elFeeMiddleTi = elFeeMiddleTService.findTotal(elFeeMiddleT);
                elFeeMiddleTiList.add(elFeeMiddleTi);
            }
            Date exportAt = new SimpleDateFormat("yyyy-MM-dd").parse(reportYear+"-"+reportMonthList.get(2)+"-"+20);

            String positionName = deptService.findByCode(elFeeQuarterT.getPositionCode()) == null ? "" : deptService.findByCode(elFeeQuarterT.getPositionCode()).getName();
            List<ElFeeQuarterT> elFeeQuarterTiS = new ArrayList<>();
            double costA1 = 0D;
            double costA2 = 0D;
            double costA3 = 0D;
            double totalFee = 0D;
            Date now = new Date();
            for (int i=0; i<3; i++) {
                ElFeeQuarterT elFeeQuarterTi = new ElFeeQuarterT();
                elFeeQuarterTi.setReportMonth(monthList.get(i)+"月");
                elFeeQuarterTi.setReportYear(reportYear);
                elFeeQuarterTi.setReportQuarter(reportQuarter);
                elFeeQuarterTi.setPositionCode(elFeeQuarterT.getPositionCode());
                elFeeQuarterTi.setPositionName(positionName);
                elFeeQuarterTi.setExportAt(now);
                if (elFeeMiddleTiList.get(i) == null) {
                    elFeeQuarterTi.setUseNum(0L);
                    elFeeQuarterTi.setTotalCostA1(0D);
                    elFeeQuarterTi.setTotalCostA3(0D);
                    elFeeQuarterTi.setTotalCostA2(0D);
                    elFeeQuarterTi.setTotalFee(0D);
                } else {
                    elFeeQuarterTi.setUseNum(elFeeMiddleTiList.get(i).getUseNum() == null ? 0L : elFeeMiddleTiList.get(i).getUseNum());
                    double costA1i = elFeeMiddleTiList.get(i).getCostA1() == null ? 0D : elFeeMiddleTiList.get(i).getCostA1();
                    elFeeQuarterTi.setTotalCostA1(FeeUtils.scale(costA1i, 3));
                    double costA3i = elFeeMiddleTiList.get(i).getCostA3() == null ? 0D : elFeeMiddleTiList.get(i).getCostA3();
                    elFeeQuarterTi.setTotalCostA3(FeeUtils.scale(costA3i, 3));
                    double costA2i = elFeeMiddleTiList.get(i).getCostA2() == null ? 0D : elFeeMiddleTiList.get(i).getCostA2();
                    elFeeQuarterTi.setTotalCostA2(FeeUtils.scale(costA2i, 3));
                    double totalFeei = elFeeMiddleTiList.get(i).getTotalFee() == null ? 0D : elFeeMiddleTiList.get(i).getTotalFee();
                    elFeeQuarterTi.setTotalFee(FeeUtils.scale(totalFeei, 3));
                }
                elFeeQuarterTi.setTotalDay(dayList.get(i));
                elFeeQuarterTi.setStatus((byte)1);
                elFeeQuarterTi.setVersion(0L);
                elFeeQuarterTi.setRemarks("");
                costA1 += elFeeQuarterTi.getTotalCostA1();
                costA2 += elFeeQuarterTi.getTotalCostA2();
                costA3 += elFeeQuarterTi.getTotalCostA3();
                totalFee += elFeeQuarterTi.getTotalFee();
                elFeeQuarterTiS.add(elFeeQuarterTi);
            }
            ElFeeQuarterT elFeeQuarterTa = new ElFeeQuarterT();
            elFeeQuarterTa.setReportMonth("合计");
            elFeeQuarterTa.setReportYear(reportYear);
            elFeeQuarterTa.setReportQuarter(reportQuarter);
            elFeeQuarterTa.setPositionCode(elFeeQuarterT.getPositionCode());
            elFeeQuarterTa.setPositionName(positionName);
            elFeeQuarterTa.setExportAt(now);
            elFeeQuarterTa.setTotalDay(null);
            elFeeQuarterTa.setStatus((byte)1);
            elFeeQuarterTa.setVersion(0L);
            elFeeQuarterTa.setRemarks("");
            elFeeQuarterTa.setUseNum(null);
            elFeeQuarterTa.setTotalCostA1(costA1);
            elFeeQuarterTa.setTotalCostA3(costA3);
            elFeeQuarterTa.setTotalCostA2(costA2);
            elFeeQuarterTa.setTotalFee(totalFee);
            elFeeQuarterTiS.add(elFeeQuarterTa);

            if (elFeeQuarterTiS == null || elFeeQuarterTiS.size() <= 0) {
                return CommonResponse.build(200, "查询结果为空", null);
            }
            return CommonResponse.ok(elFeeQuarterTiS);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常:"+JSON.toJSONString(e));
        }
    }

    @PostMapping
    public CommonResponse createBatch(@RequestBody String jsonString) {

        try {
            if (org.springframework.util.StringUtils.isEmpty(jsonString)) {
                return CommonResponse.errorMsg("参数不得为空");
            }
            List<ElFeeQuarterT> list = JsonUtils.jsonToList(jsonString, ElFeeQuarterT.class);
            if (list == null || list.size() <= 0) {
                return CommonResponse.errorMsg("参数不得为空");
            }

            boolean res = elFeeQuarterTService.createBatch(list);
            if (!res) {
                return CommonResponse.errorMsg("创建失败");
            }
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }

    }

    public long monthOfdays(int year, int month) {

        int reportYear = 1970;
        int reportMonth = 0;
        if (month == 1) {
            reportYear = year-1;
        } else {
            reportYear = year;
        }
        reportMonth = month-2;
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR,reportYear);
        cal.set(Calendar.MONTH,reportMonth);
        long maxDate = cal.getActualMaximum(Calendar.DATE);
        if (reportYear == 1970 || reportMonth == 0) {
            return 0L;
        }
        return maxDate;
    }

}
