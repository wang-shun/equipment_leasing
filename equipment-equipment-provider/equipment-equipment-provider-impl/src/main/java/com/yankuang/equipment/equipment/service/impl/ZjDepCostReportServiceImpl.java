package com.yankuang.equipment.equipment.service.impl;

import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.equipment.mapper.ZEquipmentListReportMapper;
import com.yankuang.equipment.equipment.mapper.ZjDepreciationCostReportItemMapper;
import com.yankuang.equipment.equipment.mapper.ZjDepreciationCostReportMapper;
import com.yankuang.equipment.equipment.mapper.ZjxlReportMapper;
import com.yankuang.equipment.equipment.model.*;
import com.yankuang.equipment.equipment.service.ZjDepreciationCostReportService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RpcProvider
public class ZjDepCostReportServiceImpl implements ZjDepreciationCostReportService {

    @Autowired
    ZjDepreciationCostReportMapper zjDepreciationCostReportMapper;

    @Autowired
    ZjDepreciationCostReportItemMapper zjDepCReportItemMapper;

    @Autowired
    ZjxlReportMapper zjxlReportMapper;

    @Autowired
    ZEquipmentListReportMapper zEquipmentListReportMapper;


    public int findCostRepairList(String yearMonthTime){

        Integer count = zjDepreciationCostReportMapper.findCostRepairList(yearMonthTime);
        return count;
    }

    /**
     * @method 创建综机折旧修理费
     * @param zjDepreciationCostReport
     * @return
     */
    public Boolean create(ZjDepreciationCostReport zjDepreciationCostReport){

        List<ZjDepreciationCostReportItem> zjDepreciationCostReportItems = zjDepreciationCostReport.getZjDepreciationCostReportItems();

        ZjDepreciationCostReport zjDepreciationCost = new ZjDepreciationCostReport();
        zjDepreciationCost.setAssetComp(zjDepreciationCostReport.getAssetComp());
        zjDepreciationCost.setReportName(zjDepreciationCostReport.getReportName());
        zjDepreciationCost.setRemark(zjDepreciationCostReport.getRemark());
        zjDepreciationCost.setYearTime(zjDepreciationCostReport.getYearTime());
        zjDepreciationCost.setCreateAt(new Date());

        zjDepreciationCostReportMapper.create(zjDepreciationCost);
        if (zjDepreciationCostReportItems.size()==0 || zjDepreciationCostReportItems == null){
            return false;
        }
        for (ZjDepreciationCostReportItem zjDepCostItem:zjDepreciationCostReportItems){
            zjDepCostItem.setReportId(zjDepreciationCost.getId());//每个交接单里实体类的id是一样的
            zjDepCReportItemMapper.create(zjDepCostItem);
        }

        return true;
    }

    public ZjDepreciationCostReport listzjxl(ZjxlReport zjxlReport){
       //前端传过来 年份，月份，煤业或东华或汇总，本部矿或者是外部矿
        //本部矿和外部矿不是前台传的需要自己区分
        List<ZjxlReport> zjxlReports = zjxlReportMapper.list(zjxlReport);
        List<ZjDepreciationCostReportItem> zjDepreciationCostReportItemList = new ArrayList<ZjDepreciationCostReportItem>();
        ZjDepreciationCostReportItem zjDepreciationCostReportItem = new ZjDepreciationCostReportItem();
        ZjDepreciationCostReport zjDepreciationCostReport = new ZjDepreciationCostReport();
        List list = new ArrayList();
//        list.
        if (zjxlReport.getZjxlMonth() == null) {
            for(ZjxlReport zjxl:zjxlReports) {
                zjDepreciationCostReport.setYearTime(zjxl.getZjxlYear());
                zjDepreciationCostReport.setCreateAt(new Date());
                this.name(zjxl.getUseDepartment(),zjxl,zjDepreciationCostReportItem);
            }
         zjDepreciationCostReportItemList.add(zjDepreciationCostReportItem);
         zjDepreciationCostReport.setZjDepreciationCostReportItems(zjDepreciationCostReportItemList);
        }
// else {//这边还需要判断
//            if (zjxlReport.getZjxlMonth() == "1") {
//                zjDepreciationCostReportItem.setJanuaryRepairsCost(zjxl.getSum());
//            }
//            if (zjxlReport.getZjxlMonth() == "2") {
//                zjDepreciationCostReportItem.setFebruaryRepairsCost(zjxl.getSum());
//            }
//            if (zjxlReport.getZjxlMonth() == "3") {
//                zjDepreciationCostReportItem.setMarchRepairsCost(zjxl.getSum());
//            }
//            if (zjxlReport.getZjxlMonth() == "4") {
//                zjDepreciationCostReportItem.setAprilRepairsCost(zjxl.getSum());
//            }
//            if (zjxlReport.getZjxlMonth() == "5") {
//                zjDepreciationCostReportItem.setMayRepairsCost(zjxl.getSum());
//            }
//            if (zjxlReport.getZjxlMonth() == "6") {
//                zjDepreciationCostReportItem.setJuneRepairsCost(zjxl.getSum());
//            }
//            if (zjxlReport.getZjxlMonth() == "7") {
//                zjDepreciationCostReportItem.setJulyRepairsCost(zjxl.getSum());
//            }
//            if (zjxlReport.getZjxlMonth() == "8") {
//                zjDepreciationCostReportItem.setAugustRepairsCost(zjxl.getSum());
//            }
//            if (zjxlReport.getZjxlMonth() == "9") {
//                zjDepreciationCostReportItem.setSepRepairsCost(zjxl.getSum());
//            }
//            if (zjxlReport.getZjxlMonth() == "10") {
//                zjDepreciationCostReportItem.setOctRepairsCost(zjxl.getSum());
//            }
//            if (zjxlReport.getZjxlMonth() == "11") {
//                zjDepreciationCostReportItem.setNovRepairsCost(zjxl.getSum());
//            }
//            if (zjxlReport.getZjxlMonth() == "12") {
//                zjDepreciationCostReportItem.setDecRepairsCost(zjxl.getSum());
//            }
//            zjDepreciationCostReportItemList.add(zjDepreciationCostReportItem);
//            zjDepreciationCostReport.setZjDepreciationCostReportItems(zjDepreciationCostReportItemList);
//        }

        return zjDepreciationCostReport;
    }
    private ZjDepreciationCostReportItem name(String deptName,ZjxlReport zjxl,ZjDepreciationCostReportItem zjDepreciationCostReportItem){
        if(zjxl.getUseDepartment().equals(deptName)) {
            zjDepreciationCostReportItem.setDeptName(zjxl.getUseDepartment());
            //判断传过来的月份，将所得的月份数据存到对应的字段上
            if (zjxl.getZjxlMonth().equals("1")) {
                zjDepreciationCostReportItem.setJanuaryRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("2")) {
                zjDepreciationCostReportItem.setFebruaryRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("3")) {
                zjDepreciationCostReportItem.setMarchRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("4")) {
                zjDepreciationCostReportItem.setAprilRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("5")) {
                zjDepreciationCostReportItem.setMayRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("6")) {
                zjDepreciationCostReportItem.setJuneRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("7")) {
                zjDepreciationCostReportItem.setJulyRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("8")) {
                zjDepreciationCostReportItem.setAugustRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("9")) {
                zjDepreciationCostReportItem.setSepRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("10")) {
                zjDepreciationCostReportItem.setOctRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("11")) {
                zjDepreciationCostReportItem.setNovRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("12")) {
                zjDepreciationCostReportItem.setDecRepairsCost(zjxl.getSum());
            }
        }
        return zjDepreciationCostReportItem;
    }

    public ZjDepreciationCostReport list(Map zjCostRepairMap){

        ZjDepreciationCostReport zjCostRepairReport = zjDepreciationCostReportMapper.list(zjCostRepairMap);

        //将查询出的数据按照本部和外部进行区分，传到前台
        if(zjCostRepairReport!=null){
            //本部煤矿计算合计、小计
            BigDecimal total = new BigDecimal(0);
            Double totalxiaoji = new Double(0);
            BigDecimal subTotal1 = new BigDecimal(0);
            BigDecimal subTotal2 = new BigDecimal(0);
            BigDecimal subTotal3 = new BigDecimal(0);
            BigDecimal subTotal4 = new BigDecimal(0);
            BigDecimal subTotal5 = new BigDecimal(0);
            BigDecimal subTotal6 = new BigDecimal(0);
            BigDecimal subTotal7 = new BigDecimal(0);
            BigDecimal subTotal8 = new BigDecimal(0);
            BigDecimal subTotal9 = new BigDecimal(0);
            BigDecimal subTotal10 = new BigDecimal(0);
            BigDecimal subTotal11 = new BigDecimal(0);
            BigDecimal subTotal12 = new BigDecimal(0);

            Double month01xiaoji = new Double(0);
            Double month02xiaoji = new Double(0);
            Double month03xiaoji = new Double(0);
            Double month04xiaoji = new Double(0);
            Double month05xiaoji = new Double(0);
            Double month06xiaoji = new Double(0);
            Double month07xiaoji = new Double(0);
            Double month08xiaoji = new Double(0);
            Double month09xiaoji = new Double(0);
            Double month10xiaoji = new Double(0);
            Double month11xiaoji = new Double(0);
            Double month12xiaoji = new Double(0);
            List<ZjDepreciationCostReportItem> zjDepreciationCostReportItems = zjDepCReportItemMapper.listHomeOffice(zjCostRepairReport.getId(),"1");
            if(zjDepreciationCostReportItems!=null || zjDepreciationCostReportItems.size()>0){

                for (ZjDepreciationCostReportItem zjDepreciationCostReportItem:zjDepreciationCostReportItems){

                    BigDecimal b1 = new BigDecimal(0);
                    BigDecimal b2 = new BigDecimal(0);
                    BigDecimal b3 = new BigDecimal(0);
                    BigDecimal b4 = new BigDecimal(0);
                    BigDecimal b5 = new BigDecimal(0);
                    BigDecimal b6 = new BigDecimal(0);
                    BigDecimal b7 = new BigDecimal(0);
                    BigDecimal b8 = new BigDecimal(0);
                    BigDecimal b9 = new BigDecimal(0);
                    BigDecimal b10 = new BigDecimal(0);
                    BigDecimal b11 = new BigDecimal(0);
                    BigDecimal b12 = new BigDecimal(0);


                    if(zjDepreciationCostReportItem.getJanuaryRepairsCost()!= null ){
                        b1 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getJanuaryRepairsCost()));
                        subTotal1 = subTotal1.add(b1);
                        month01xiaoji = subTotal1.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getFebruaryRepairsCost()!= null ){
                        b2 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getFebruaryRepairsCost()));
                        subTotal2 = subTotal2.add(b2);
                        month02xiaoji= subTotal2.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getMarchRepairsCost()!= null ){
                        b3 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getMarchRepairsCost()));
                        subTotal3 = subTotal3.add(b3);
                        month03xiaoji = subTotal3.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getAprilRepairsCost()!= null ){
                        b4 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getAprilRepairsCost()));
                        subTotal4 = subTotal4.add(b4);
                        month04xiaoji = subTotal4.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getMayRepairsCost()!= null ){
                        b5 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getMayRepairsCost()));
                        subTotal5 = subTotal5.add(b5);
                        month05xiaoji = subTotal5.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getJuneRepairsCost()!= null ){
                        b6 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getJuneRepairsCost()));
                        subTotal6 = subTotal6.add(b6);
                        month06xiaoji = subTotal6.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getJulyRepairsCost()!= null ){
                        b7 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getJulyRepairsCost()));
                        subTotal7 = subTotal7.add(b7);
                        month07xiaoji = subTotal7.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getAugustRepairsCost()!= null ){
                        b8 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getAugustRepairsCost()));
                        subTotal8 = subTotal8.add(b8);
                        month08xiaoji = subTotal8.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getSepRepairsCost()!= null ){
                        b9 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getSepRepairsCost()));
                        subTotal9 = subTotal9.add(b9);
                        month09xiaoji = subTotal9.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getOctRepairsCost()!= null ){
                        b10 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getOctRepairsCost()));
                        subTotal10 = subTotal10.add(b10);
                        month10xiaoji = subTotal10.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getNovRepairsCost()!= null ){
                        b11 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getNovRepairsCost()));
                        subTotal11 = subTotal11.add(b11);
                        month11xiaoji = subTotal11.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getDecRepairsCost()!= null ){
                        b12 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getDecRepairsCost()));
                        subTotal12 = subTotal12.add(b12);
                        month12xiaoji = subTotal12.doubleValue();
                    }

                    double totalT = b1.add(b2).add(b3).add(b4).add(b5).add(b6).add(b7).add(b8).add(b9).add(b10).add(b11).add(b12).doubleValue();
                    zjDepreciationCostReportItem.setDeptSum(totalT);//合计(内部一个矿的合计)
                    BigDecimal b13 = new BigDecimal(Double.toString(totalT));
                    total = total.add(b13);//合计下的小计
                    totalxiaoji=total.doubleValue();//合计的总合计

                    zjCostRepairReport.setZjDepreciationCostReportItemsHome(zjDepreciationCostReportItems);
                }
                List<ZjDepreciationCostReportItemSumHome> moreDeptSumList = new ArrayList<ZjDepreciationCostReportItemSumHome>();
                ZjDepreciationCostReportItemSumHome zjDepreciationCostReportItemSum = new ZjDepreciationCostReportItemSumHome();
                zjDepreciationCostReportItemSum.setMoreDeptSum(totalxiaoji);//合计的总合计
                zjDepreciationCostReportItemSum.setMonth01xiaoji(month01xiaoji);//小计(内部所有矿每个月的小计)
                zjDepreciationCostReportItemSum.setMonth02xiaoji(month02xiaoji);
                zjDepreciationCostReportItemSum.setMonth03xiaoji(month03xiaoji);
                zjDepreciationCostReportItemSum.setMonth04xiaoji(month04xiaoji);
                zjDepreciationCostReportItemSum.setMonth05xiaoji(month05xiaoji);
                zjDepreciationCostReportItemSum.setMonth06xiaoji(month06xiaoji);
                zjDepreciationCostReportItemSum.setMonth07xiaoji(month07xiaoji);
                zjDepreciationCostReportItemSum.setMonth08xiaoji(month08xiaoji);
                zjDepreciationCostReportItemSum.setMonth09xiaoji(month09xiaoji);
                zjDepreciationCostReportItemSum.setMonth10xiaoji(month10xiaoji);
                zjDepreciationCostReportItemSum.setMonth11xiaoji(month11xiaoji);
                zjDepreciationCostReportItemSum.setMonth12xiaoji(month12xiaoji);

                moreDeptSumList.add(zjDepreciationCostReportItemSum);
                zjCostRepairReport.setZjDepreciationCostReportItemSumHomes(moreDeptSumList);
            }

           //外部煤矿计算合计、小计
            List<ZjDepreciationCostReportItem> zjDepreciationCostReportItemList = zjDepCReportItemMapper.listHomeOffice(zjCostRepairReport.getId(),"2");
            BigDecimal totalE = new BigDecimal(0);
            Double totalxiaojiE = new Double(0);
            BigDecimal subTotalE1 = new BigDecimal(0);
            BigDecimal subTotalE2 = new BigDecimal(0);
            BigDecimal subTotalE3 = new BigDecimal(0);
            BigDecimal subTotalE4 = new BigDecimal(0);
            BigDecimal subTotalE5 = new BigDecimal(0);
            BigDecimal subTotalE6 = new BigDecimal(0);
            BigDecimal subTotalE7 = new BigDecimal(0);
            BigDecimal subTotalE8 = new BigDecimal(0);
            BigDecimal subTotalE9 = new BigDecimal(0);
            BigDecimal subTotalE10 = new BigDecimal(0);
            BigDecimal subTotalE11 = new BigDecimal(0);
            BigDecimal subTotalE12 = new BigDecimal(0);

            Double month01xiaojiE = new Double(0);
            Double month02xiaojiE = new Double(0);
            Double month03xiaojiE = new Double(0);
            Double month04xiaojiE = new Double(0);
            Double month05xiaojiE = new Double(0);
            Double month06xiaojiE = new Double(0);
            Double month07xiaojiE = new Double(0);
            Double month08xiaojiE = new Double(0);
            Double month09xiaojiE = new Double(0);
            Double month10xiaojiE = new Double(0);
            Double month11xiaojiE = new Double(0);
            Double month12xiaojiE = new Double(0);
            if(zjDepreciationCostReportItems!=null || zjDepreciationCostReportItems.size()>0){

                for (ZjDepreciationCostReportItem zjDepreciationCostReportItem:zjDepreciationCostReportItemList){
                    BigDecimal b1 = new BigDecimal(0);
                    BigDecimal b2 = new BigDecimal(0);
                    BigDecimal b3 = new BigDecimal(0);
                    BigDecimal b4 = new BigDecimal(0);
                    BigDecimal b5 = new BigDecimal(0);
                    BigDecimal b6 = new BigDecimal(0);
                    BigDecimal b7 = new BigDecimal(0);
                    BigDecimal b8 = new BigDecimal(0);
                    BigDecimal b9 = new BigDecimal(0);
                    BigDecimal b10 = new BigDecimal(0);
                    BigDecimal b11 = new BigDecimal(0);
                    BigDecimal b12 = new BigDecimal(0);

                    if(zjDepreciationCostReportItem.getJanuaryRepairsCost()!= null ){
                        b1 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getJanuaryRepairsCost()));
                        subTotalE1 = subTotalE1.add(b1);
                        month01xiaojiE = subTotalE1.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getFebruaryRepairsCost()!= null ){
                        b2 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getFebruaryRepairsCost()));
                        subTotalE2 = subTotalE2.add(b2);
                        month02xiaojiE= subTotalE2.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getMarchRepairsCost()!= null ){
                        b3 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getMarchRepairsCost()));
                        subTotalE3 = subTotalE3.add(b3);
                        month03xiaojiE = subTotalE3.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getAprilRepairsCost()!= null ){
                        b4 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getAprilRepairsCost()));
                        subTotalE4 = subTotalE4.add(b4);
                        month04xiaojiE = subTotalE4.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getMayRepairsCost()!= null ){
                        b5 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getMayRepairsCost()));
                        subTotalE5 = subTotalE5.add(b5);
                        month05xiaojiE = subTotalE5.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getJuneRepairsCost()!= null ){
                        b6 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getJuneRepairsCost()));
                        subTotalE6 = subTotalE6.add(b6);
                        month06xiaojiE = subTotalE6.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getJulyRepairsCost()!= null ){
                        b7 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getJulyRepairsCost()));
                        subTotalE7 = subTotalE7.add(b7);
                        month07xiaojiE = subTotalE7.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getAugustRepairsCost()!= null ){
                        b8 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getAugustRepairsCost()));
                        subTotalE8 = subTotalE8.add(b8);
                        month08xiaojiE = subTotalE8.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getSepRepairsCost()!= null ){
                        b9 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getSepRepairsCost()));
                        subTotalE9 = subTotalE9.add(b9);
                        month09xiaojiE = subTotalE9.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getOctRepairsCost()!= null ){
                        b10 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getOctRepairsCost()));
                        subTotalE10 = subTotalE10.add(b10);
                        month10xiaojiE = subTotalE10.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getNovRepairsCost()!= null ){
                        b11 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getNovRepairsCost()));
                        subTotalE11 = subTotalE11.add(b11);
                        month11xiaojiE = subTotalE11.doubleValue();
                    }
                    if(zjDepreciationCostReportItem.getDecRepairsCost()!= null ){
                        b12 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getDecRepairsCost()));
                        subTotalE12 = subTotalE12.add(b12);
                        month12xiaojiE = subTotalE12.doubleValue();
                    }

                    zjCostRepairReport.setZjDepreciationCostReportItemsHome(zjDepreciationCostReportItems);

                    double totalExternal = b1.add(b2).add(b3).add(b4).add(b5).add(b6).add(b7).add(b8).add(b9).add(b10).add(b11).add(b12).doubleValue();

                    zjDepreciationCostReportItem.setDeptSum(totalExternal);//合计(内部一个矿的合计)
                    BigDecimal b13 = new BigDecimal(Double.toString(totalExternal));
                    totalE = totalE.add(b13);//合计下的小计
                    totalxiaojiE=totalE.doubleValue();
                    zjCostRepairReport.setZjDepreciationCostReportItemsExternal(zjDepreciationCostReportItemList);
                }
                List<ZjDepreciationCostReportItemSumExternal> moreDeptSumList = new ArrayList<ZjDepreciationCostReportItemSumExternal>();
                ZjDepreciationCostReportItemSumExternal zjDepreciationCostReportItemSumExternal = new ZjDepreciationCostReportItemSumExternal();
                zjDepreciationCostReportItemSumExternal.setMoreDeptSum(totalxiaojiE);//合计的总合计
                zjDepreciationCostReportItemSumExternal.setMonth01xiaoji(month01xiaojiE);//小计(内部所有矿每个月的小计)
                zjDepreciationCostReportItemSumExternal.setMonth02xiaoji(month02xiaojiE);
                zjDepreciationCostReportItemSumExternal.setMonth03xiaoji(month03xiaojiE);
                zjDepreciationCostReportItemSumExternal.setMonth04xiaoji(month04xiaojiE);
                zjDepreciationCostReportItemSumExternal.setMonth05xiaoji(month05xiaojiE);
                zjDepreciationCostReportItemSumExternal.setMonth06xiaoji(month06xiaojiE);
                zjDepreciationCostReportItemSumExternal.setMonth07xiaoji(month07xiaojiE);
                zjDepreciationCostReportItemSumExternal.setMonth08xiaoji(month08xiaojiE);
                zjDepreciationCostReportItemSumExternal.setMonth09xiaoji(month09xiaojiE);
                zjDepreciationCostReportItemSumExternal.setMonth10xiaoji(month10xiaojiE);
                zjDepreciationCostReportItemSumExternal.setMonth11xiaoji(month11xiaojiE);
                zjDepreciationCostReportItemSumExternal.setMonth12xiaoji(month12xiaojiE);

                moreDeptSumList.add(zjDepreciationCostReportItemSumExternal);
                zjCostRepairReport.setZjDepreciationCostReportItemSumExternals(moreDeptSumList);
            }

            //计算累计
            List<ZjDepreciationCostReportItemAddUpSum> zjDepAddUpSums = new ArrayList<ZjDepreciationCostReportItemAddUpSum>();
            ZjDepreciationCostReportItemAddUpSum zjDepreciationCostReportItemAddUpSum = new ZjDepreciationCostReportItemAddUpSum();

            BigDecimal totalxiaojihome = new BigDecimal(Double.toString(totalxiaoji));
            BigDecimal totalxiaojiexter = new BigDecimal(Double.toString(totalxiaojiE));
            zjDepreciationCostReportItemAddUpSum.setMoreDeptAddUpSum(totalxiaojihome.add(totalxiaojiexter).doubleValue());//一月份本部外部累计
            BigDecimal b1 = new BigDecimal(Double.toString(month01xiaoji));
            BigDecimal b1E = new BigDecimal(Double.toString(month01xiaojiE));
            zjDepreciationCostReportItemAddUpSum.setAddUpSum01(b1.add(b1E).doubleValue());//一月份本部外部累计
            BigDecimal b2 = new BigDecimal(Double.toString(month02xiaoji));
            BigDecimal b2E = new BigDecimal(Double.toString(month02xiaojiE));
            zjDepreciationCostReportItemAddUpSum.setAddUpSum02(b2.add(b2E).doubleValue());//二月份本部外部累计
            BigDecimal b3 = new BigDecimal(Double.toString(month03xiaoji));
            BigDecimal b3E = new BigDecimal(Double.toString(month03xiaojiE));
            zjDepreciationCostReportItemAddUpSum.setAddUpSum03(b3.add(b3E).doubleValue());//三月份本部外部累计
            BigDecimal b4 = new BigDecimal(Double.toString(month04xiaoji));
            BigDecimal b4E = new BigDecimal(Double.toString(month04xiaojiE));
            zjDepreciationCostReportItemAddUpSum.setAddUpSum04(b4.add(b4E).doubleValue());//四月份本部外部累计
            BigDecimal b5 = new BigDecimal(Double.toString(month05xiaoji));
            BigDecimal b5E = new BigDecimal(Double.toString(month05xiaojiE));
            zjDepreciationCostReportItemAddUpSum.setAddUpSum05(b5.add(b5E).doubleValue());//五月份本部外部累计
            BigDecimal b6 = new BigDecimal(Double.toString(month06xiaoji));
            BigDecimal b6E = new BigDecimal(Double.toString(month06xiaojiE));
            zjDepreciationCostReportItemAddUpSum.setAddUpSum06(b6.add(b6E).doubleValue());//六月份本部外部累计
            BigDecimal b7 = new BigDecimal(Double.toString(month07xiaoji));
            BigDecimal b7E = new BigDecimal(Double.toString(month07xiaojiE));
            zjDepreciationCostReportItemAddUpSum.setAddUpSum07(b7.add(b7E).doubleValue());//七月份本部外部累计
            BigDecimal b8 = new BigDecimal(Double.toString(month08xiaoji));
            BigDecimal b8E = new BigDecimal(Double.toString(month08xiaojiE));
            zjDepreciationCostReportItemAddUpSum.setAddUpSum08(b8.add(b8E).doubleValue());//八月份本部外部累计
            BigDecimal b9 = new BigDecimal(Double.toString(month09xiaoji));
            BigDecimal b9E = new BigDecimal(Double.toString(month09xiaojiE));
            zjDepreciationCostReportItemAddUpSum.setAddUpSum09(b9.add(b9E).doubleValue());//九月份本部外部累计
            BigDecimal b10 = new BigDecimal(Double.toString(month10xiaoji));
            BigDecimal b10E = new BigDecimal(Double.toString(month10xiaojiE));
            zjDepreciationCostReportItemAddUpSum.setAddUpSum10(b10.add(b10E).doubleValue());//十月份本部外部累计
            BigDecimal b11 = new BigDecimal(Double.toString(month11xiaoji));
            BigDecimal b11E = new BigDecimal(Double.toString(month11xiaojiE));
            zjDepreciationCostReportItemAddUpSum.setAddUpSum11(b11.add(b11E).doubleValue());//十一月份本部外部累计
            BigDecimal b12 = new BigDecimal(Double.toString(month12xiaoji));
            BigDecimal b12E = new BigDecimal(Double.toString(month12xiaojiE));
            zjDepreciationCostReportItemAddUpSum.setAddUpSum12(b12.add(b12E).doubleValue());//十二月份本部外部累计
            zjDepAddUpSums.add(zjDepreciationCostReportItemAddUpSum);
            zjCostRepairReport.setZjDepreciationCostReportItemAddUpSums(zjDepAddUpSums);
        }

        return zjCostRepairReport;
    }




}
