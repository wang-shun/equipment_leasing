package com.yankuang.equipment.web.restful;

import com.alibaba.fastjson.JSON;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.FeeUtils;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.equipment.model.ElFeeDetailT;
import com.yankuang.equipment.equipment.model.ElFeeMiddleT;
import com.yankuang.equipment.equipment.model.SbEquipmentT;
import com.yankuang.equipment.equipment.service.ElFeeDetailTService;
import com.yankuang.equipment.equipment.service.ElFeeMiddleTService;
import com.yankuang.equipment.equipment.service.SbEquipmentTService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/elFeeMiddleT")
public class ElFeeMiddleTController {

    @RpcConsumer
    ElFeeMiddleTService elFeeMiddleTService;
    @RpcConsumer
    ElFeeDetailTService elFeeDetailTService;
    @RpcConsumer
    SbEquipmentTService sbEquipmentTService;

    @GetMapping
    public CommonResponse findElFeeMiddleTs(ElFeeMiddleT elFeeMiddleT) {

        try {
            if (elFeeMiddleT == null || StringUtils.isEmpty(elFeeMiddleT.getPositionCode())) {
                return CommonResponse.errorMsg("请补充矿处单位");
            }
            if (elFeeMiddleT == null || StringUtils.isEmpty(elFeeMiddleT.getExportAtStr())) {
                return CommonResponse.errorMsg("请补充导出时间");
            }
            String reportYear = elFeeMiddleT.getExportAtStr().split("-")[0];
            String reportMonth = elFeeMiddleT.getExportAtStr().split("-")[1];
            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(reportYear+"-"+(Integer.valueOf(reportMonth)-1)+"-"+21);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(reportYear+"-"+(Integer.valueOf(reportMonth))+"-"+20);
            elFeeMiddleT.setReportYear(reportYear);
            elFeeMiddleT.setReportMonth(reportMonth);
            String positionName = "";
            long eqNumTotal = 0L;
            long useNumTotal = 0L;
            double costA1Total = 0;
            double costA2Total = 0;
            double costA3Total = 0;
            double feeTotals = 0;
            List<ElFeeMiddleT> historyList = elFeeMiddleTService.findElFeeMiddleTs(elFeeMiddleT, elFeeMiddleT.getPageNum(), elFeeMiddleT.getPageSize());
            if (historyList != null && historyList.size() > 0) {
                boolean flag = false;
                for (ElFeeMiddleT els : historyList) {
                    if (els == null) {
                        continue;
                    }
                    if ("合计".equals(els.getMiddleName())) {
                        flag = true;
                        break;
                    }
                    if (StringUtils.isEmpty(positionName) && !StringUtils.isEmpty(els.getPositionName())) {
                        positionName = els.getPositionName();
                    }
                    eqNumTotal += els.getEquipmentNum();
                    useNumTotal += els.getUseNum();
                    costA1Total += els.getCostA1();
                    costA2Total += els.getCostA2();
                    costA3Total += els.getCostA3();
                    feeTotals += els.getTotalFee();
                }
                if (!flag) {
                    ElFeeMiddleT middleTTotal = new ElFeeMiddleT();
                    middleTTotal.setReportYear(reportYear);
                    middleTTotal.setReportMonth(reportMonth);
                    middleTTotal.setExportAt(endDate);
                    middleTTotal.setPositionCode(elFeeMiddleT.getPositionCode());
                    middleTTotal.setPositionName(positionName);
                    middleTTotal.setStatus((byte)1);
                    middleTTotal.setMiddleName("合计");
                    middleTTotal.setMiddleCode("0000");
                    middleTTotal.setEquipmentNum(eqNumTotal);
                    middleTTotal.setUseNum(useNumTotal);
                    middleTTotal.setCostA1(FeeUtils.scale(costA1Total, 3));
                    middleTTotal.setCostA3(FeeUtils.scale(costA3Total, 3));
                    middleTTotal.setTotalFee(FeeUtils.scale(feeTotals, 3));
                    middleTTotal.setCostA2(FeeUtils.scale(costA2Total, 3));
                    historyList.add(middleTTotal);
                }
                return CommonResponse.ok(historyList);
            }

            // 联表查询
            ElFeeDetailT elFeeDetailT = new ElFeeDetailT();
            elFeeDetailT.setExportAtStr(elFeeMiddleT.getExportAtStr());
            elFeeDetailT.setPositionCode(elFeeMiddleT.getPositionCode());
            List<ElFeeDetailT> detailTs = elFeeDetailTService.findElFeeDetailTs(elFeeDetailT, null, null);
            // 按照中类分组
            Date now = new Date();
            Map<String, List<ElFeeDetailT>> detailTMap = new HashMap<>();
            for (ElFeeDetailT detailT : detailTs) {
                if (detailT == null) {
                    continue;
                }
                List<ElFeeDetailT> tempList = detailTMap.get(detailT.getMiddleTypeName());
                if (tempList == null) {
                    tempList = new ArrayList<>();
                    tempList.add(detailT);
                    detailTMap.put(detailT.getMiddleTypeName(), tempList);
                } else {
                    tempList.add(detailT);
                }
            }
            long eqNumTotals = 0L;
            long useNumTotals = 0L;
            double costA1Totals = 0;
            double costA2Totals = 0;
            double costA3Totals = 0;
            double feeTotalss = 0;
            List<ElFeeMiddleT> middleTs = new ArrayList<>();
            for(String middleName : detailTMap.keySet()){
                List<ElFeeDetailT> feeDetailTs = detailTMap.get(middleName);
                if (feeDetailTs == null || feeDetailTs.size() <= 0) {
                    continue;
                }
                long eqNum = 0L;
                long useNum = 0L;
                double costA1 = 0;
                double costA2 = 0;
                double costA3 = 0;
                double feeTotal = 0;
                String middleCode = feeDetailTs.get(0).getMiddleTypeCode();
                SbEquipmentT sbEquipmentT = new SbEquipmentT();
                sbEquipmentT.setSbtypeTwo(middleCode);
                eqNum = sbEquipmentTService.list(sbEquipmentT, 1, 1000).getList().size();
                for (ElFeeDetailT el : feeDetailTs) {
                    if (el == null) {
                        continue;
                    }
                    if (StringUtils.isEmpty(positionName) && !StringUtils.isEmpty(el.getPositionName())) {
                        positionName = el.getPositionName();
                    }
                    costA1 += el.getCostA1() == null ? 0 : el.getCostA1();
                    costA3 += el.getCostA3() == null ? 0 : el.getCostA3();
                    feeTotal = costA1 + costA3;
                    useNum += 1;
                }
                ElFeeMiddleT middleT = new ElFeeMiddleT();
                middleT.setReportYear(reportYear);
                middleT.setReportMonth(reportMonth);
                //middleT.setExportAt(endDate);
                middleT.setExportAt(now);
                middleT.setPositionCode(elFeeMiddleT.getPositionCode());
                middleT.setPositionName(positionName);
                middleT.setStatus((byte)1);
                middleT.setMiddleName(middleName);
                middleT.setMiddleCode(middleCode);
                middleT.setEquipmentNum(eqNum);
                middleT.setUseNum(useNum);
                middleT.setCostA1(FeeUtils.scale(costA1, 3));
                middleT.setCostA3(FeeUtils.scale(costA3, 3));
                middleT.setTotalFee(FeeUtils.scale(feeTotal, 3));
                middleT.setCostA2(FeeUtils.scale(costA2, 3));
                eqNumTotals += eqNum;
                useNumTotals += useNum;
                costA1Totals += costA1;
                costA2Totals += costA2;
                costA3Totals += costA3;
                feeTotalss += feeTotal;
                middleTs.add(middleT);
            }
            ElFeeMiddleT middleTTotal = new ElFeeMiddleT();
            middleTTotal.setReportYear(reportYear);
            middleTTotal.setReportMonth(reportMonth);
            //middleTTotal.setExportAt(endDate);
            middleTTotal.setExportAt(now);
            middleTTotal.setPositionCode(elFeeMiddleT.getPositionCode());
            middleTTotal.setPositionName(positionName);
            middleTTotal.setStatus((byte)1);
            middleTTotal.setMiddleName("合计");
            middleTTotal.setMiddleCode("0000");
            middleTTotal.setEquipmentNum(eqNumTotals);
            middleTTotal.setUseNum(useNumTotals);
            middleTTotal.setCostA1(FeeUtils.scale(costA1Totals, 3));
            middleTTotal.setCostA3(FeeUtils.scale(costA3Totals, 3));
            middleTTotal.setTotalFee(FeeUtils.scale(feeTotalss, 3));
            middleTTotal.setCostA2(FeeUtils.scale(costA2Totals, 3));
            middleTs.add(middleTTotal);

            if (middleTs == null || middleTs.size() <= 0) {
                return CommonResponse.build(200, "查询结果为空", null);
            }

            return CommonResponse.ok(middleTs);
        } catch (ParseException e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+ JSON.toJSONString(e));
        }
    }

    @PostMapping
    public CommonResponse createBatch(@RequestBody String jsonString) {
        try {
            if (StringUtils.isEmpty(jsonString)) {
                return CommonResponse.errorMsg("参数不得为空");
            }
            List<ElFeeMiddleT> list = JsonUtils.jsonToList(jsonString, ElFeeMiddleT.class);
            if (list == null || list.size() <= 0) {
                return CommonResponse.errorMsg("参数不得为空");
            }

            boolean res = elFeeMiddleTService.createBatch(list);
            if (!res) {
                return CommonResponse.errorMsg("添加失败");
            }
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

}
