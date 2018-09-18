package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.equipment.mapper.ReportEquipmentZMonthItemMapper;
import com.yankuang.equipment.equipment.mapper.ReportEquipmentZMonthMapper;
import com.yankuang.equipment.equipment.model.ReportEquipmentZMonth;
import com.yankuang.equipment.equipment.model.ReportEquipmentZMonthItem;
import com.yankuang.equipment.equipment.model.ReportEquipmentZMonthSum;
import com.yankuang.equipment.equipment.model.ReportEquipmentZMonthSumItem;
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

    public ReportEquipmentZMonth findByYear(String year) {
        ReportEquipmentZMonth reportEquipmentZMonth = reportEquipmentZMonthMapper.findByYear(year);
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

                reportEquipmentZMonthSumItem1.setCurMonthsVal(reportEquipmentZMonthItem.getSumVal());

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
                reportEquipmentZMonthSumItem1.setCurMonthVal(curMonthVal);

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

                reportEquipmentZMonthSumItem1.setCurMonthsVal(reportEquipmentZMonthItem.getSumVal());

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
                reportEquipmentZMonthSumItem1.setCurMonthVal(curMonthVal);

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

                reportEquipmentZMonthSumItem2.setCurMonthsVal(reportEquipmentZMonthItem.getSumVal());

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
                reportEquipmentZMonthSumItem2.setCurMonthVal(curMonthVal);

                BigDecimal preMonthsPlanVal = new BigDecimal(reportEquipmentZMonthItem.getSumVal()-curMonthVal).setScale(4, RoundingMode.UP);
                reportEquipmentZMonthSumItem2.setPreMonthsVal(preMonthsPlanVal.doubleValue());

                list1_.add(reportEquipmentZMonthSumItem2);
            }
            if(list2Sum!=null){
                reportEquipmentZMonthSumItem2 = new ReportEquipmentZMonthSumItem();
                ReportEquipmentZMonthItem reportEquipmentZMonthItem = list2Sum.get(0);
                //reportEquipmentZMonthSumItem2.setDeptCode(reportEquipmentZMonthItem.getDeptCode());
                reportEquipmentZMonthSumItem2.setDeptName(reportEquipmentZMonthItem.getDeptName());
                reportEquipmentZMonthSumItem2.setYearPlanVal(reportEquipmentZMonthItem.getYearPlanVal());

                BigDecimal curMonthsPlanVal = new BigDecimal(reportEquipmentZMonthItem.getYearPlanVal()*month/12).setScale(4, RoundingMode.UP);
                reportEquipmentZMonthSumItem2.setCurMonthsPlanVal(curMonthsPlanVal.doubleValue());

                reportEquipmentZMonthSumItem2.setCurMonthsVal(reportEquipmentZMonthItem.getSumVal());

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
                reportEquipmentZMonthSumItem2.setCurMonthVal(curMonthVal);

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

                reportEquipmentZMonthSumItem3.setCurMonthsVal(reportEquipmentZMonthItem.getSumVal());

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
                reportEquipmentZMonthSumItem3.setCurMonthVal(curMonthVal);

                BigDecimal preMonthsPlanVal = new BigDecimal(reportEquipmentZMonthItem.getSumVal()-curMonthVal).setScale(4, RoundingMode.UP);
                reportEquipmentZMonthSumItem3.setPreMonthsVal(preMonthsPlanVal.doubleValue());

                list3_.add(reportEquipmentZMonthSumItem3);
            }
            reportEquipmentZMonthSum.setList3(list3_);
        }

        return reportEquipmentZMonthSum;
    }
}
