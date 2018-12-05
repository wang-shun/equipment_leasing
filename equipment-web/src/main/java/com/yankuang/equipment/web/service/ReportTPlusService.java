package com.yankuang.equipment.web.service;

import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.authority.service.DeptService;
import com.yankuang.equipment.common.util.FeeUtils;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.equipment.model.*;
import com.yankuang.equipment.equipment.service.*;
import com.yankuang.equipment.web.dto.GenericDTO;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class ReportTPlusService {

    @RpcConsumer
    DeptService deptService;
    @RpcConsumer
    ElUseItemService elUseItemService;
    @RpcConsumer
    SbElFeeService sbElFeeService;
    @RpcConsumer
    ElPlanUseService elPlanUseService;
    @RpcConsumer
    ElFeeMiddleTService elFeeMiddleTService;
    @RpcConsumer
    ElFeeDetailTService elFeeDetailTService;
    @RpcConsumer
    SbEquipmentTService sbEquipmentTService;
    @RpcConsumer
    ElFeePositionTService elFeePositionTService;

    public List<ElFeeDetailT> getElFeeDetailTS(String positionCode, String reportYear, String reportMonth) throws ParseException {
        Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(reportYear+"-"+(Integer.valueOf(reportMonth)-1)+"-"+21);
        Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(reportYear+"-"+(Integer.valueOf(reportMonth))+"-"+20);
        HashMap<String, String> map = new HashMap<>();
        map.put("pcode", positionCode);
        List<Dept> depts = deptService.findByPage(1, 1000, map).getList();
        List<ElUseItem> elUseItemList = new ArrayList<>();
        for (Dept d : depts) {
            if (d == null || d.getId() == null) {
                continue;
            }
            ElUseItem elUseItem = new ElUseItem();
            elUseItem.setEquipmentPosition(d.getId());
            List<ElUseItem> useItems = elUseItemService.findElUseItemTL(elUseItem);
            if (useItems != null && useItems.size() > 0) {
                elUseItemList.addAll(useItems);
            }
        }
        List<ElFeeDetailT> elFeeDetailTS = new ArrayList<>();
        Date now = new Date();
        for (ElUseItem uItem : elUseItemList) {
            Long days = 0L;
            if (uItem == null) {
                continue;
            }
            if (uItem.getSign() == null) {
                days = sbElFeeService.CalEquipmentElDays(uItem.getUseId(), null, uItem.getEquipmentId(), startDate, endDate);
            } else {
                Long uItemTId = uItem.getSign();
                ElUseItem uItemT = elUseItemService.findById(uItemTId);
                if (uItemT != null && uItemT.getUseAt().after(startDate)) {
                    days = sbElFeeService.CalEquipmentElDays(uItem.getUseId(), uItemT.getUseId(), uItem.getEquipmentId(), startDate, endDate);
                }
            }
            if (days <= 0l) {
                continue;
            }
            ElFeeDetailT detailT = new ElFeeDetailT();
            Dept de = deptService.findByCode(positionCode);
            if (de == null) {
                continue;
            }
            detailT.setPositionName(de.getName());
            detailT.setPositionCode(positionCode);
            ElPlanUse planUse = elPlanUseService.findById(uItem.getPlanUseId());
            detailT.setMiddleTypeName(planUse.getMiddleTypeName());
            detailT.setMiddleTypeCode(planUse.getMiddleTypeCode());
            detailT.setSmallTypeCode(planUse.getSmallTypeCode());
            detailT.setSmallTypeName(planUse.getSmallTypeName());
            detailT.setEquipmentCode(planUse.getEquipmentCode());
            detailT.setTechCode(planUse.getTechCode());
            detailT.setModelName(planUse.getEquipmentSpecification());
            detailT.setModelCode(planUse.getEquipmentModel());
            detailT.setEffectName(planUse.getEffectName());
            detailT.setEffectCode(planUse.getEffectCode());
            detailT.setElDays(days);
            detailT.setCostA1(uItem.getCostA1());
            Double a3Rate = sbElFeeService.CalDayElFeeA3T_rate(uItem.getUseId(), uItem.getEquipmentId());
            double a3s = (a3Rate-1)*uItem.getCostA1();
            detailT.setCostA3(FeeUtils.scale(a3s, 3));
            double totalFees = uItem.getCostA1()*days+(a3Rate-1)*uItem.getCostA1();
            detailT.setTotalFee(FeeUtils.scale(totalFees, 3));
            detailT.setReportYear(reportYear);
            detailT.setReportMonth(reportMonth);
//            detailT.setExportAt(endDate);
            detailT.setExportAt(now);
            detailT.setEquipmentId(planUse.getEquipmentId());
//            detailT.setPageNum(detailTs.getPageNum());
//            detailT.setPageSize(detailTs.getPageSize());
            detailT.setStatus((byte)1);
            elFeeDetailTS.add(detailT);
        }
        return elFeeDetailTS;
    }

    public List<ElFeeMiddleT> getElFeeMiddleTSHistory(ElFeeMiddleT elFeeMiddleT, String reportYear, String reportMonth) throws ParseException {
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
        List<ElFeeMiddleT> historyList = elFeeMiddleTService.findElFeeMiddleTs(elFeeMiddleT, null, null);
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
        }
        return historyList;
    }

    public List<ElFeeMiddleT> getElFeeMiddleTS(String positionCode, String reportYear, String reportMonth) {
        // 联表查询
        ElFeeDetailT elFeeDetailT = new ElFeeDetailT();
        elFeeDetailT.setReportYear(reportYear);
        elFeeDetailT.setReportMonth(reportMonth);
        elFeeDetailT.setPositionCode(positionCode);
        List<ElFeeDetailT> detailTs = elFeeDetailTService.findElFeeDetailTs(elFeeDetailT, null, null);
        if (detailTs == null || detailTs.size() <= 0) return null;
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
        String positionName = "";
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
            middleT.setPositionCode(positionCode);
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
        middleTTotal.setPositionCode(positionCode);
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
        return middleTs;
    }

    public List<ElFeeQuarterT> getElFeeQuarterTS(String positionCode, String reportYear, String reportQuarter) throws ParseException {
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
            elFeeMiddleT.setPositionCode(positionCode);
            elFeeMiddleT.setReportYear(reportYear);
            elFeeMiddleT.setReportMonth(reportMonth);
            elFeeMiddleT.setMiddleCode("0000");
            ElFeeMiddleT elFeeMiddleTi = elFeeMiddleTService.findTotal(elFeeMiddleT);
            elFeeMiddleTiList.add(elFeeMiddleTi);
        }
        Date exportAt = new SimpleDateFormat("yyyy-MM-dd").parse(reportYear+"-"+reportMonthList.get(2)+"-"+20);

        String positionName = deptService.findByCode(positionCode) == null ? "" : deptService.findByCode(positionCode).getName();
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
            elFeeQuarterTi.setPositionCode(positionCode);
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
        elFeeQuarterTa.setPositionCode(positionCode);
        elFeeQuarterTa.setPositionName(positionName);
        elFeeQuarterTa.setExportAt(now);
        elFeeQuarterTa.setTotalDay(null);
        elFeeQuarterTa.setStatus((byte)1);
        elFeeQuarterTa.setVersion(0L);
        elFeeQuarterTa.setRemarks("");
        elFeeQuarterTa.setUseNum(null);
        elFeeQuarterTa.setTotalCostA1(FeeUtils.scale(costA1, 3));
        elFeeQuarterTa.setTotalCostA3(FeeUtils.scale(costA3, 3));
        elFeeQuarterTa.setTotalCostA2(FeeUtils.scale(costA2, 3));
        elFeeQuarterTa.setTotalFee(FeeUtils.scale(totalFee, 3));
        elFeeQuarterTiS.add(elFeeQuarterTa);
        return elFeeQuarterTiS;
    }

    public List<ElFeePositionT> getElFeePositionTSHistory(ElFeePositionT elFeePositionT, String reportYear, String reportMonth) {
        elFeePositionT.setReportYear(reportYear);
        elFeePositionT.setReportMonth(reportMonth);
        List<ElFeePositionT> historyList = elFeePositionTService.list(elFeePositionT);
        for (ElFeePositionT elPositionT : historyList) {
            if (elPositionT != null && !StringUtils.isEmpty(elPositionT.getPositionMap())) {
                Map<String, Double> positionMap = JsonUtils.jsonToPojo(elPositionT.getPositionMap(), Map.class);
                if (positionMap == null) {
                    continue;
                }
                elPositionT.setDepositMap(positionMap);
                List<GenericDTO> genericDTOList = new ArrayList<>();
                for (String positionName : positionMap.keySet()) {
                    GenericDTO genericDTO = new GenericDTO(positionName, positionMap.get(positionName));
                    genericDTOList.add(genericDTO);
                }
                elPositionT.setDepositList(genericDTOList);
            }
        }
        return historyList;
    }

    public List<ElFeePositionT> getElFeePositionTS(ElFeePositionT elFeePositionT, String reportYear, String reportMonth) {
        ElFeeMiddleT elFeeMiddleT = new ElFeeMiddleT();
        elFeeMiddleT.setReportYear(reportYear);
        elFeeMiddleT.setReportMonth(reportMonth);
        List<ElFeeMiddleT> elFeeMiddleTs = elFeeMiddleTService.findElFeeMiddleTs(elFeeMiddleT, null, null);
        if (elFeeMiddleTs == null || elFeeMiddleTs.size() <= 0) return null;
        Map<String, List<ElFeeMiddleT>> middleTMap = new HashMap<>();
        Map<String, String> positionMap = new HashMap<>();
        for (ElFeeMiddleT middleT : elFeeMiddleTs) {
            if (middleT == null) {
                continue;
            }
            List<ElFeeMiddleT> tempList = middleTMap.get(middleT.getMiddleName());
            String positionName = positionMap.get(middleT.getPositionCode());
            if (tempList == null) {
                tempList = new ArrayList<>();
                tempList.add(middleT);
                middleTMap.put(middleT.getMiddleName(), tempList);
            } else {
                tempList.add(middleT);
            }
            if (positionName == null) {
                positionMap.put(middleT.getPositionCode(), middleT.getPositionName());
            }
        }

        for (String poCode : positionMap.keySet()) {
            if (poCode == null) {
                positionMap.remove(poCode);
                continue;
            }
            Dept dept = deptService.findByCode(poCode);
            if (dept == null) {
                positionMap.remove(poCode);
            } else if(dept.getAddress() == null || !dept.getAddress().contains("济宁")) {
                    positionMap.remove(poCode);
            }
        }
        if (positionMap.size() <= 0) return null;

        List<ElFeePositionT> positionTs = new ArrayList<>();
        Date now = new Date();
        for (String middleName : middleTMap.keySet()) {
            if (middleName == null) {
                continue;
            }
            List<ElFeeMiddleT> middleTs = middleTMap.get(middleName);
            if (middleTs == null) {
                continue;
            }
            ElFeePositionT positionT = new ElFeePositionT();
            for (ElFeeMiddleT middleT : middleTs) {
                if ((positionT.getMiddleCode() == null
                        || positionT.getMiddleName() == null)
                        && (middleT != null && !StringUtils.isEmpty(middleT.getMiddleName())
                        && !StringUtils.isEmpty(middleT.getMiddleCode()))) {
                    positionT.setMiddleCode(middleT.getMiddleCode());
                    positionT.setMiddleName(middleT.getMiddleName());
                    break;
                }
            }
            Map<String, Double> positionMapi = new HashMap<>();
            List<GenericDTO> genericDTOs = new ArrayList<>();
            double totalFeei = 0D;
            for (String poCodei : positionMap.keySet()) {
                if (poCodei == null) {
                    continue;
                }
                boolean res = false;
                for (ElFeeMiddleT middleTi: middleTs) {
                    if (poCodei != null && middleTi != null && poCodei.equals(middleTi.getPositionCode())) {
                        positionMapi.put(positionMap.get(poCodei), middleTi.getTotalFee());
                        GenericDTO genericDTO = new GenericDTO(positionMap.get(poCodei), middleTi.getTotalFee());
                        genericDTOs.add(genericDTO);
                        res = true;
                    }
                }
                if (!res) {
                    positionMapi.put(positionMap.get(poCodei), 0D);
                    GenericDTO genericDTO = new GenericDTO(positionMap.get(poCodei), 0D);
                    genericDTOs.add(genericDTO);
                }
                totalFeei += positionMapi.get(positionMap.get(poCodei));
            }
            positionT.setDepositMap(positionMapi);
            positionT.setPositionMap(JsonUtils.objectToJson(positionMapi));
            positionT.setDepositList(genericDTOs);
            positionT.setTotalFee(totalFeei);
            positionT.setReportYear(reportYear);
            positionT.setReportMonth(reportMonth);
            positionT.setExportAt(now);
            positionT.setVersion(0L);
            positionT.setStatus((byte)1);
            positionT.setRemarks("");
            positionT.setPoStatus(elFeePositionT.getPoStatus());
            positionTs.add(positionT);
        }
        return positionTs;
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
