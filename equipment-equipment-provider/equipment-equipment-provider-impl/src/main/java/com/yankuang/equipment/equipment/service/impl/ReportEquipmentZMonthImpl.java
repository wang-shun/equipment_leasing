package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.ReportEquipmentZMonthItemMapper;
import com.yankuang.equipment.equipment.mapper.ReportEquipmentZMonthMapper;
import com.yankuang.equipment.equipment.mapper.ZEquipmentListReportMapper;
import com.yankuang.equipment.equipment.model.*;
import com.yankuang.equipment.equipment.service.ReportEquipmentZMonthService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Service
@RpcProvider
public class ReportEquipmentZMonthImpl implements ReportEquipmentZMonthService {

    @Autowired
    ReportEquipmentZMonthMapper reportEquipmentZMonthMapper;

    @Autowired
    ReportEquipmentZMonthItemMapper reportEquipmentZMonthItemMapper;

    @Autowired
    ZEquipmentListReportMapper zEquipmentListReportMapper;

    public ReportEquipmentZMonth findByYear(String year) {
        ReportEquipmentZMonth reportEquipmentZMonth = reportEquipmentZMonthMapper.findByYear(year);
        if(reportEquipmentZMonth==null){
            //每年开始第一个月默认添加一个报表的表头和备注信息
            ReportEquipmentZMonth equipmentZMonth = new ReportEquipmentZMonth();
            Calendar cale = Calendar.getInstance();
            equipmentZMonth.setYear(String.valueOf(cale.get(Calendar.YEAR)));
            equipmentZMonth.setReportName(String.valueOf(cale.get(Calendar.YEAR))+"年综机折旧修理费月报(汇总)");
            equipmentZMonth.setRemark("注:1、石拉乌素、龙凤矿本月直接收取上半年全部租赁费、全部入1180账号。\n 2、安源矿本月直接收取上半年全部租赁费、全部入1730账号。");
            equipmentZMonth.setCreateBy("admin");
            equipmentZMonth.setUpdateBy("admin");
            reportEquipmentZMonthMapper.insert(equipmentZMonth);

            reportEquipmentZMonth = reportEquipmentZMonthMapper.findByYear(year);
        }
        if(reportEquipmentZMonth != null){
            List<ReportEquipmentZMonthItem> list1 = reportEquipmentZMonthItemMapper.listByExportId(reportEquipmentZMonth.getId().longValue(),"1");
            List<ReportEquipmentZMonthItem> list1Sum = reportEquipmentZMonthItemMapper.sumByExportId(reportEquipmentZMonth.getId().longValue(),"1");
            list1.addAll(list1Sum);
            reportEquipmentZMonth.setList1(list1);

            List<ReportEquipmentZMonthItem> list2 = reportEquipmentZMonthItemMapper.listByExportId(reportEquipmentZMonth.getId().longValue(),"2");
            List<ReportEquipmentZMonthItem> list2Sum = reportEquipmentZMonthItemMapper.sumByExportId(reportEquipmentZMonth.getId().longValue(),"2");
            list2.addAll(list2Sum);
            reportEquipmentZMonth.setList2(list2);

            List<ReportEquipmentZMonthItem> list3 = reportEquipmentZMonthItemMapper.sumByExportId(reportEquipmentZMonth.getId().longValue(),"");
            list3.get(0).setDeptName("累计");
            reportEquipmentZMonth.setList3(list3);
        }
        return reportEquipmentZMonth;
    }

    public ReportEquipmentZMonthSum findSumByYear(String year){
        //获取当前的月份
        Calendar cale = Calendar.getInstance();
        int month = cale.get(Calendar.MONTH) + 1;
        int day = cale.get(Calendar.DATE);
        if(day >= 1 && day <= 20){
            month = month -1;
            if(month == 0){
                month = 12;
            }
        }

        ReportEquipmentZMonth reportEquipmentZMonth = reportEquipmentZMonthMapper.findByYear(year);
        ReportEquipmentZMonthSum reportEquipmentZMonthSum = new ReportEquipmentZMonthSum();
        if(reportEquipmentZMonth != null){
            //汇总报表表头及备注
            reportEquipmentZMonthSum.setReportName(reportEquipmentZMonth.getReportName());
            reportEquipmentZMonthSum.setRemark(reportEquipmentZMonth.getRemark());
            reportEquipmentZMonthSum.setYear(reportEquipmentZMonth.getYear());

            //汇总报表的本部矿及小计
            List<ReportEquipmentZMonthItem> list1 = reportEquipmentZMonthItemMapper.listByExportId(reportEquipmentZMonth.getId().longValue(),"1");
            List<ReportEquipmentZMonthItem> list1Sum = reportEquipmentZMonthItemMapper.sumByExportId(reportEquipmentZMonth.getId().longValue(),"1");
            List<ReportEquipmentZMonthSumItem> list1_ = new ArrayList<ReportEquipmentZMonthSumItem>();
            ReportEquipmentZMonthSumItem reportEquipmentZMonthSumItem1 = null;
            for(int i=0;i<list1.size();i++){
                reportEquipmentZMonthSumItem1 = new ReportEquipmentZMonthSumItem();
                ReportEquipmentZMonthItem reportEquipmentZMonthItem = list1.get(i);
                //reportEquipmentZMonthSumItem1.setDeptCode(reportEquipmentZMonthItem.getDeptCode());
                reportEquipmentZMonthSumItem1.setDeptName(reportEquipmentZMonthItem.getDeptName());
                reportEquipmentZMonthSumItem1.setYearPlanVal(reportEquipmentZMonthItem.getYearPlanVal());

                BigDecimal curMonthsPlanVal = new BigDecimal(reportEquipmentZMonthItem.getYearPlanVal()*month/12).setScale(4, RoundingMode.UP);
                reportEquipmentZMonthSumItem1.setCurMonthsPlanVal(curMonthsPlanVal.doubleValue());

                reportEquipmentZMonthSumItem1.setCurMonthsVal(new BigDecimal(reportEquipmentZMonthItem.getSumVal()).setScale(4, RoundingMode.UP).doubleValue());

                double curMonthVal = 0;
                if(month == 1){curMonthVal = reportEquipmentZMonthItem.getMonth01();}
                if(month == 2){curMonthVal = reportEquipmentZMonthItem.getMonth02();}
                if(month == 3){curMonthVal = reportEquipmentZMonthItem.getMonth03();}
                if(month == 4){curMonthVal = reportEquipmentZMonthItem.getMonth04();}
                if(month == 5){curMonthVal = reportEquipmentZMonthItem.getMonth05();}
                if(month == 6){curMonthVal = reportEquipmentZMonthItem.getMonth06();}
                if(month == 7){curMonthVal = reportEquipmentZMonthItem.getMonth07();}
                if(month == 8){curMonthVal = reportEquipmentZMonthItem.getMonth08();}
                if(month == 9){curMonthVal = reportEquipmentZMonthItem.getMonth09();}
                if(month == 10){curMonthVal = reportEquipmentZMonthItem.getMonth10();}
                if(month == 11){curMonthVal = reportEquipmentZMonthItem.getMonth11();}
                if(month == 12){curMonthVal = reportEquipmentZMonthItem.getMonth12();}
                reportEquipmentZMonthSumItem1.setCurMonthVal(new BigDecimal(curMonthVal).setScale(4, RoundingMode.UP).doubleValue());

                BigDecimal preMonthsPlanVal = new BigDecimal(reportEquipmentZMonthItem.getSumVal()-curMonthVal).setScale(4, RoundingMode.UP);
                reportEquipmentZMonthSumItem1.setPreMonthsVal(preMonthsPlanVal.doubleValue());

                list1_.add(reportEquipmentZMonthSumItem1);
            }
            if(list1Sum!=null){
                reportEquipmentZMonthSumItem1 = new ReportEquipmentZMonthSumItem();
                ReportEquipmentZMonthItem reportEquipmentZMonthItem = list1Sum.get(0);
                //reportEquipmentZMonthSumItem1.setDeptCode(reportEquipmentZMonthItem.getDeptCode());
                reportEquipmentZMonthSumItem1.setDeptName(reportEquipmentZMonthItem.getDeptName());
                reportEquipmentZMonthSumItem1.setYearPlanVal(reportEquipmentZMonthItem.getYearPlanVal());

                BigDecimal curMonthsPlanVal = new BigDecimal(reportEquipmentZMonthItem.getYearPlanVal()*month/12).setScale(4, RoundingMode.UP);
                reportEquipmentZMonthSumItem1.setCurMonthsPlanVal(curMonthsPlanVal.doubleValue());

                reportEquipmentZMonthSumItem1.setCurMonthsVal(new BigDecimal(reportEquipmentZMonthItem.getSumVal()).setScale(4, RoundingMode.UP).doubleValue());

                double curMonthVal = 0;
                if(month == 1){curMonthVal = reportEquipmentZMonthItem.getMonth01();}
                if(month == 2){curMonthVal = reportEquipmentZMonthItem.getMonth02();}
                if(month == 3){curMonthVal = reportEquipmentZMonthItem.getMonth03();}
                if(month == 4){curMonthVal = reportEquipmentZMonthItem.getMonth04();}
                if(month == 5){curMonthVal = reportEquipmentZMonthItem.getMonth05();}
                if(month == 6){curMonthVal = reportEquipmentZMonthItem.getMonth06();}
                if(month == 7){curMonthVal = reportEquipmentZMonthItem.getMonth07();}
                if(month == 8){curMonthVal = reportEquipmentZMonthItem.getMonth08();}
                if(month == 9){curMonthVal = reportEquipmentZMonthItem.getMonth09();}
                if(month == 10){curMonthVal = reportEquipmentZMonthItem.getMonth10();}
                if(month == 11){curMonthVal = reportEquipmentZMonthItem.getMonth11();}
                if(month == 12){curMonthVal = reportEquipmentZMonthItem.getMonth12();}
                reportEquipmentZMonthSumItem1.setCurMonthVal(new BigDecimal(curMonthVal).setScale(4, RoundingMode.UP).doubleValue());

                BigDecimal preMonthsPlanVal = new BigDecimal(reportEquipmentZMonthItem.getSumVal()-curMonthVal).setScale(4, RoundingMode.UP);
                reportEquipmentZMonthSumItem1.setPreMonthsVal(preMonthsPlanVal.doubleValue());

                list1_.add(reportEquipmentZMonthSumItem1);
            }
            reportEquipmentZMonthSum.setList1(list1_);

            //汇总报表的外部矿及小计
            List<ReportEquipmentZMonthItem> list2 = reportEquipmentZMonthItemMapper.listByExportId(reportEquipmentZMonth.getId().longValue(),"2");
            List<ReportEquipmentZMonthItem> list2Sum = reportEquipmentZMonthItemMapper.sumByExportId(reportEquipmentZMonth.getId().longValue(),"2");
            List<ReportEquipmentZMonthSumItem> list2_ = new ArrayList<ReportEquipmentZMonthSumItem>();
            ReportEquipmentZMonthSumItem reportEquipmentZMonthSumItem2 = null;

            for(int i=0;i<list2.size();i++){
                reportEquipmentZMonthSumItem2 = new ReportEquipmentZMonthSumItem();
                ReportEquipmentZMonthItem reportEquipmentZMonthItem = list2.get(i);
                //reportEquipmentZMonthSumItem2.setDeptCode(reportEquipmentZMonthItem.getDeptCode());
                reportEquipmentZMonthSumItem2.setDeptName(reportEquipmentZMonthItem.getDeptName());
                reportEquipmentZMonthSumItem2.setYearPlanVal(reportEquipmentZMonthItem.getYearPlanVal());

                BigDecimal curMonthsPlanVal = new BigDecimal(reportEquipmentZMonthItem.getYearPlanVal()*month/12).setScale(4, RoundingMode.UP);
                reportEquipmentZMonthSumItem2.setCurMonthsPlanVal(curMonthsPlanVal.doubleValue());

                reportEquipmentZMonthSumItem2.setCurMonthsVal(new BigDecimal(reportEquipmentZMonthItem.getSumVal()).setScale(4, RoundingMode.UP).doubleValue());

                double curMonthVal = 0;
                if(month == 1){curMonthVal = reportEquipmentZMonthItem.getMonth01();}
                if(month == 2){curMonthVal = reportEquipmentZMonthItem.getMonth02();}
                if(month == 3){curMonthVal = reportEquipmentZMonthItem.getMonth03();}
                if(month == 4){curMonthVal = reportEquipmentZMonthItem.getMonth04();}
                if(month == 5){curMonthVal = reportEquipmentZMonthItem.getMonth05();}
                if(month == 6){curMonthVal = reportEquipmentZMonthItem.getMonth06();}
                if(month == 7){curMonthVal = reportEquipmentZMonthItem.getMonth07();}
                if(month == 8){curMonthVal = reportEquipmentZMonthItem.getMonth08();}
                if(month == 9){curMonthVal = reportEquipmentZMonthItem.getMonth09();}
                if(month == 10){curMonthVal = reportEquipmentZMonthItem.getMonth10();}
                if(month == 11){curMonthVal = reportEquipmentZMonthItem.getMonth11();}
                if(month == 12){curMonthVal = reportEquipmentZMonthItem.getMonth12();}
                reportEquipmentZMonthSumItem2.setCurMonthVal(new BigDecimal(curMonthVal).setScale(4, RoundingMode.UP).doubleValue());

                BigDecimal preMonthsPlanVal = new BigDecimal(reportEquipmentZMonthItem.getSumVal()-curMonthVal).setScale(4, RoundingMode.UP);
                reportEquipmentZMonthSumItem2.setPreMonthsVal(preMonthsPlanVal.doubleValue());

                list2_.add(reportEquipmentZMonthSumItem2);
            }
            if(list2Sum!=null){
                reportEquipmentZMonthSumItem2 = new ReportEquipmentZMonthSumItem();
                ReportEquipmentZMonthItem reportEquipmentZMonthItem = list2Sum.get(0);
                //reportEquipmentZMonthSumItem2.setDeptCode(reportEquipmentZMonthItem.getDeptCode());
                reportEquipmentZMonthSumItem2.setDeptName(reportEquipmentZMonthItem.getDeptName());
                reportEquipmentZMonthSumItem2.setYearPlanVal(reportEquipmentZMonthItem.getYearPlanVal());

                BigDecimal curMonthsPlanVal = new BigDecimal(reportEquipmentZMonthItem.getYearPlanVal()*month/12).setScale(4, RoundingMode.UP);
                reportEquipmentZMonthSumItem2.setCurMonthsPlanVal(curMonthsPlanVal.doubleValue());

                reportEquipmentZMonthSumItem2.setCurMonthsVal(new BigDecimal(reportEquipmentZMonthItem.getSumVal()).setScale(4, RoundingMode.UP).doubleValue());

                double curMonthVal = 0;
                if(month == 1){curMonthVal = reportEquipmentZMonthItem.getMonth01();}
                if(month == 2){curMonthVal = reportEquipmentZMonthItem.getMonth02();}
                if(month == 3){curMonthVal = reportEquipmentZMonthItem.getMonth03();}
                if(month == 4){curMonthVal = reportEquipmentZMonthItem.getMonth04();}
                if(month == 5){curMonthVal = reportEquipmentZMonthItem.getMonth05();}
                if(month == 6){curMonthVal = reportEquipmentZMonthItem.getMonth06();}
                if(month == 7){curMonthVal = reportEquipmentZMonthItem.getMonth07();}
                if(month == 8){curMonthVal = reportEquipmentZMonthItem.getMonth08();}
                if(month == 9){curMonthVal = reportEquipmentZMonthItem.getMonth09();}
                if(month == 10){curMonthVal = reportEquipmentZMonthItem.getMonth10();}
                if(month == 11){curMonthVal = reportEquipmentZMonthItem.getMonth11();}
                if(month == 12){curMonthVal = reportEquipmentZMonthItem.getMonth12();}
                reportEquipmentZMonthSumItem2.setCurMonthVal(new BigDecimal(curMonthVal).setScale(4, RoundingMode.UP).doubleValue());

                BigDecimal preMonthsPlanVal = new BigDecimal(reportEquipmentZMonthItem.getSumVal()-curMonthVal).setScale(4, RoundingMode.UP);
                reportEquipmentZMonthSumItem2.setPreMonthsVal(preMonthsPlanVal.doubleValue());

                list2_.add(reportEquipmentZMonthSumItem2);
            }
            reportEquipmentZMonthSum.setList2(list2_);

            //累计
            List<ReportEquipmentZMonthItem> list3Sum = reportEquipmentZMonthItemMapper.sumByExportId(reportEquipmentZMonth.getId().longValue(),"");
            List<ReportEquipmentZMonthSumItem> list3_ = new ArrayList<ReportEquipmentZMonthSumItem>();
            ReportEquipmentZMonthSumItem reportEquipmentZMonthSumItem3 = null;

            if(list3Sum!=null){
                reportEquipmentZMonthSumItem3 = new ReportEquipmentZMonthSumItem();
                ReportEquipmentZMonthItem reportEquipmentZMonthItem = list3Sum.get(0);
                //reportEquipmentZMonthSumItem3.setDeptCode(reportEquipmentZMonthItem.getDeptCode());
                reportEquipmentZMonthSumItem3.setDeptName("累计");
                reportEquipmentZMonthSumItem3.setYearPlanVal(reportEquipmentZMonthItem.getYearPlanVal());

                BigDecimal curMonthsPlanVal = new BigDecimal(reportEquipmentZMonthItem.getYearPlanVal()*month/12).setScale(4, RoundingMode.UP);
                reportEquipmentZMonthSumItem3.setCurMonthsPlanVal(curMonthsPlanVal.doubleValue());

                reportEquipmentZMonthSumItem3.setCurMonthsVal(new BigDecimal(reportEquipmentZMonthItem.getSumVal()).setScale(4, RoundingMode.UP).doubleValue());

                double curMonthVal = 0;
                if(month == 1){curMonthVal = reportEquipmentZMonthItem.getMonth01();}
                if(month == 2){curMonthVal = reportEquipmentZMonthItem.getMonth02();}
                if(month == 3){curMonthVal = reportEquipmentZMonthItem.getMonth03();}
                if(month == 4){curMonthVal = reportEquipmentZMonthItem.getMonth04();}
                if(month == 5){curMonthVal = reportEquipmentZMonthItem.getMonth05();}
                if(month == 6){curMonthVal = reportEquipmentZMonthItem.getMonth06();}
                if(month == 7){curMonthVal = reportEquipmentZMonthItem.getMonth07();}
                if(month == 8){curMonthVal = reportEquipmentZMonthItem.getMonth08();}
                if(month == 9){curMonthVal = reportEquipmentZMonthItem.getMonth09();}
                if(month == 10){curMonthVal = reportEquipmentZMonthItem.getMonth10();}
                if(month == 11){curMonthVal = reportEquipmentZMonthItem.getMonth11();}
                if(month == 12){curMonthVal = reportEquipmentZMonthItem.getMonth12();}
                reportEquipmentZMonthSumItem3.setCurMonthVal(new BigDecimal(curMonthVal).setScale(4, RoundingMode.UP).doubleValue());

                BigDecimal preMonthsPlanVal = new BigDecimal(reportEquipmentZMonthItem.getSumVal()-curMonthVal).setScale(4, RoundingMode.UP);
                reportEquipmentZMonthSumItem3.setPreMonthsVal(preMonthsPlanVal.doubleValue());

                list3_.add(reportEquipmentZMonthSumItem3);
            }
            reportEquipmentZMonthSum.setList3(list3_);
        }

        return reportEquipmentZMonthSum;
    }

    public int CalReportEquipmentZMonth() {
        int res = 1;
        //获取当前的年月
        Calendar cale = Calendar.getInstance();
        int month = cale.get(Calendar.MONTH) + 1;
        int year = cale.get(Calendar.YEAR);

        //查询当前年月的各矿的综机租赁费合计
        ListZReport listZReport = new ListZReport();
        listZReport.setUseYear(String.valueOf(year));
        listZReport.setUseMonth(String.valueOf(month));
        List<ListZReport> list = zEquipmentListReportMapper.list(listZReport);
        if(list != null && list.size() > 0){
            ReportEquipmentZMonth reportEquipmentZMonth = reportEquipmentZMonthMapper.findByYear(String.valueOf(year));
            if(reportEquipmentZMonth != null){
                for(int i=0;i<list.size();i++) {
                    ListZReport zReport = list.get(i);
                    ReportEquipmentZMonthItem record = new ReportEquipmentZMonthItem();
                    record.setReportId(reportEquipmentZMonth.getId());
                    record.setDeptCode(zReport.getUseDeptCode());
                    List<ReportEquipmentZMonthItem> items = reportEquipmentZMonthItemMapper.list(record);
                    //判断汇总的明细是否存在,存在则更新,不存在则添加
                    if(items != null && items.size() > 0){
                        ReportEquipmentZMonthItem reportEquipmentZMonthItem = items.get(0);
                        if(month == 1){reportEquipmentZMonthItem.setMonth01(zReport.getSum());}
                        if(month == 2){reportEquipmentZMonthItem.setMonth02(zReport.getSum());}
                        if(month == 3){reportEquipmentZMonthItem.setMonth03(zReport.getSum());}
                        if(month == 4){reportEquipmentZMonthItem.setMonth04(zReport.getSum());}
                        if(month == 5){reportEquipmentZMonthItem.setMonth05(zReport.getSum());}
                        if(month == 6){reportEquipmentZMonthItem.setMonth06(zReport.getSum());}
                        if(month == 7){reportEquipmentZMonthItem.setMonth07(zReport.getSum());}
                        if(month == 8){reportEquipmentZMonthItem.setMonth08(zReport.getSum());}
                        if(month == 9){reportEquipmentZMonthItem.setMonth09(zReport.getSum());}
                        if(month == 10){reportEquipmentZMonthItem.setMonth10(zReport.getSum());}
                        if(month == 11){reportEquipmentZMonthItem.setMonth11(zReport.getSum());}
                        if(month == 12){reportEquipmentZMonthItem.setMonth12(zReport.getSum());}
                        res = reportEquipmentZMonthItemMapper.update(reportEquipmentZMonthItem);
                    }else{
                        ReportEquipmentZMonthItem reportEquipmentZMonthItem = new ReportEquipmentZMonthItem();
                        reportEquipmentZMonthItem.setDeptCode(zReport.getUseDeptCode());
                        reportEquipmentZMonthItem.setDeptName(zReport.getUseDeptName());
                        reportEquipmentZMonthItem.setDeptType(String.valueOf(zReport.getType()));
                        reportEquipmentZMonthItem.setReportId(reportEquipmentZMonth.getId());
                        if(month == 1){reportEquipmentZMonthItem.setMonth01(zReport.getSum());}
                        if(month == 2){reportEquipmentZMonthItem.setMonth02(zReport.getSum());}
                        if(month == 3){reportEquipmentZMonthItem.setMonth03(zReport.getSum());}
                        if(month == 4){reportEquipmentZMonthItem.setMonth04(zReport.getSum());}
                        if(month == 5){reportEquipmentZMonthItem.setMonth05(zReport.getSum());}
                        if(month == 6){reportEquipmentZMonthItem.setMonth06(zReport.getSum());}
                        if(month == 7){reportEquipmentZMonthItem.setMonth07(zReport.getSum());}
                        if(month == 8){reportEquipmentZMonthItem.setMonth08(zReport.getSum());}
                        if(month == 9){reportEquipmentZMonthItem.setMonth09(zReport.getSum());}
                        if(month == 10){reportEquipmentZMonthItem.setMonth10(zReport.getSum());}
                        if(month == 11){reportEquipmentZMonthItem.setMonth11(zReport.getSum());}
                        if(month == 12){reportEquipmentZMonthItem.setMonth12(zReport.getSum());}
                        res = reportEquipmentZMonthItemMapper.insert(reportEquipmentZMonthItem);
                    }
                }
            }
        }
        return res;
    }

    public int updateRemarkById(ReportEquipmentZMonth reportEquipmentZMonth) {
        return reportEquipmentZMonthMapper.updateRemarkById(reportEquipmentZMonth);
    }

    public int updateYearPlanValById(ReportEquipmentZMonthItem reportEquipmentZMonthItem) {
        return reportEquipmentZMonthItemMapper.updateYearPlanValById(reportEquipmentZMonthItem);
    }
}
