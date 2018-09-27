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
import java.util.*;

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


    public int findCostRepairList(String yearMonthTime,String month,String assetComp){

        Integer count = zjDepreciationCostReportMapper.findCostRepairList(yearMonthTime,month,assetComp);
        return count;
    }

    /**
     * @method 创建综机折旧修理费
     * @param zjDepreciationCostReport
     * @return
     */
    public Boolean create(ZjDepreciationCostReport zjDepreciationCostReport){

        List<ZjDepreciationCostReportItem> zjDepreciationCostReportItemHome = zjDepreciationCostReport.getZjDepreciationCostReportItemsHome();
        List<ZjDepreciationCostReportItem> zjDepreciationCostReportItemExternal = zjDepreciationCostReport.getZjDepreciationCostReportItemsExternal();

        ZjDepreciationCostReport zjDepreciationCost = new ZjDepreciationCostReport();
        zjDepreciationCost.setAssetComp(zjDepreciationCostReport.getAssetComp());
        zjDepreciationCost.setReportName(zjDepreciationCostReport.getReportName());
        zjDepreciationCost.setRemark(zjDepreciationCostReport.getRemark());
        zjDepreciationCost.setYearTime(zjDepreciationCostReport.getYearTime());
        zjDepreciationCost.setMonthTime(zjDepreciationCostReport.getMonthTime());
        zjDepreciationCost.setCreateAt(new Date());
        zjDepreciationCost.setStatus((byte)1);
        zjDepreciationCostReportMapper.history(zjDepreciationCostReport.getYearTime(),zjDepreciationCostReport.getMonthTime(),zjDepreciationCostReport.getAssetComp());
        zjDepreciationCostReportMapper.create(zjDepreciationCost);
        if (zjDepreciationCostReportItemHome.size()==0 || zjDepreciationCostReportItemHome == null){
            return false;
        }
        for (ZjDepreciationCostReportItem zjDepCostItem:zjDepreciationCostReportItemHome){
            zjDepCostItem.setReportId(zjDepreciationCost.getId());//每个交接单里实体类的id是一样的
//            zjDepCostItem.setStatus((byte)1);
//            zjDepCReportItemMapper.historyItems(zjDepreciationCost.getId());
            zjDepCReportItemMapper.create(zjDepCostItem);
        }
        for (ZjDepreciationCostReportItem zjDepCostItemExternal:zjDepreciationCostReportItemExternal){
            zjDepCostItemExternal.setReportId(zjDepreciationCost.getId());//每个交接单里实体类的id是一样的
//            zjDepCostItemExternal.setStatus((byte)1);
            zjDepCReportItemMapper.create(zjDepCostItemExternal);
        }

        return true;
    }

    //从数据库查询展示到页面
    public ZjDepreciationCostReport listzjxl(ZjDepreciationCostReport zjDepreciationCostReport){
       //前端传过来 年份，月份，煤业或东华或汇总，本部矿或者是外部矿
        //本部矿和外部矿不是前台传的需要自己区分
        ZjxlReport zjxlReport = new ZjxlReport();
        zjxlReport.setZjxlYear(zjDepreciationCostReport.getYearTime());
        if(!zjDepreciationCostReport.getAssetComp().equals("1100")){
            zjxlReport.setZc(zjDepreciationCostReport.getAssetComp());//东华还是煤业（1180,1730）
        }

        List<ZjxlReport> zjxlReports = zjxlReportMapper.list(zjxlReport);

        String month = zjDepreciationCostReport.getMonthTime();
        List<ZjDepreciationCostReportItem> zjDepreciationCostReportItemListHome = new ArrayList<ZjDepreciationCostReportItem>();
        List<ZjDepreciationCostReportItem> zjDepreciationCostReportItemListEnter = new ArrayList<ZjDepreciationCostReportItem>();

        //查询出本部矿
//        List<ZjxlReport> zjxlReportList = zjxlReportMapper.listHome("1");
        if (zjxlReport.getZjxlMonth() == null) {
            zjDepreciationCostReport.setCreateAt(new Date());
            //本部矿
            ZjDepreciationCostReportItem zjDepreciationCostReportItemNan = new ZjDepreciationCostReportItem();
            ZjDepreciationCostReportItem zjDepreciationCostReportItemXing = new ZjDepreciationCostReportItem();
            ZjDepreciationCostReportItem zjDepreciationCostReportItemBao = new ZjDepreciationCostReportItem();
            ZjDepreciationCostReportItem zjDepreciationCostReportItemDong = new ZjDepreciationCostReportItem();
            ZjDepreciationCostReportItem zjDepreciationCostReportItemJier = new ZjDepreciationCostReportItem();
            ZjDepreciationCostReportItem zjDepreciationCostReportItemJiSan = new ZjDepreciationCostReportItem();
            ZjDepreciationCostReportItem zjDepreciationCostReportItemYang = new ZjDepreciationCostReportItem();
            for(ZjxlReport zjxl:zjxlReports) {
                zjDepreciationCostReportItemNan.setDeptName("南屯矿");
                zjDepreciationCostReportItemNan.setDeptType("1");
                this.monthCost("南屯矿",zjxl,zjDepreciationCostReportItemNan,month);//查询一个矿所查月份的修理费
            }
            Double totalNan = totalheji(zjDepreciationCostReportItemNan);//南屯矿合计
            zjDepreciationCostReportItemNan.setDeptSum(totalNan);
            if(totalNan!=0.0){
                zjDepreciationCostReportItemListHome.add(zjDepreciationCostReportItemNan);
            }

            for(ZjxlReport zjxl:zjxlReports) {
                zjDepreciationCostReportItemXing.setDeptName("兴隆矿");
                zjDepreciationCostReportItemXing.setDeptType("1");
                this.monthCost("兴隆矿",zjxl,zjDepreciationCostReportItemXing,month);
            }
            Double totalxing = totalheji(zjDepreciationCostReportItemXing);//兴隆矿合计
            zjDepreciationCostReportItemXing.setDeptSum(totalxing);
            zjDepreciationCostReportItemListHome.add(zjDepreciationCostReportItemXing);

            for(ZjxlReport zjxl:zjxlReports) {
                zjDepreciationCostReportItemBao.setDeptName("鲍店");
                zjDepreciationCostReportItemBao.setDeptType("1");
                this.monthCost("鲍店",zjxl,zjDepreciationCostReportItemBao,month);
            }
            Double totalBao = totalheji(zjDepreciationCostReportItemBao);//鲍店合计
            zjDepreciationCostReportItemBao.setDeptSum(totalBao);
            if(totalBao!=0.0){
                zjDepreciationCostReportItemListHome.add(zjDepreciationCostReportItemBao);
            }

            for(ZjxlReport zjxl:zjxlReports) {
                zjDepreciationCostReportItemDong.setDeptName("东滩矿");
                zjDepreciationCostReportItemDong.setDeptType("1");
                this.monthCost("东滩矿",zjxl,zjDepreciationCostReportItemDong,month);
            }
            Double totalDong = totalheji(zjDepreciationCostReportItemDong);//东滩矿合计
            zjDepreciationCostReportItemDong.setDeptSum(totalDong);
            if(totalDong!=0.0){
                zjDepreciationCostReportItemListHome.add(zjDepreciationCostReportItemDong);
            }

            for(ZjxlReport zjxl:zjxlReports) {
                zjDepreciationCostReportItemJier.setDeptName("济二");
                zjDepreciationCostReportItemJier.setDeptType("1");
                this.monthCost("济二",zjxl,zjDepreciationCostReportItemJier,month);
            }
            Double totalJiEr = totalheji(zjDepreciationCostReportItemJier);//济二合计
            zjDepreciationCostReportItemJier.setDeptSum(totalJiEr);
            if(totalJiEr!=0.0){
                zjDepreciationCostReportItemListHome.add(zjDepreciationCostReportItemJier);
            }


            for(ZjxlReport zjxl:zjxlReports) {
                zjDepreciationCostReportItemJiSan.setDeptName("济三");
                zjDepreciationCostReportItemJiSan.setDeptType("1");
                this.monthCost("济三",zjxl,zjDepreciationCostReportItemJiSan,month);
            }
            Double totalJiSan = totalheji(zjDepreciationCostReportItemJiSan);//济三合计
            zjDepreciationCostReportItemJiSan.setDeptSum(totalJiSan);
            if(totalJiSan!=0.0){
                zjDepreciationCostReportItemListHome.add(zjDepreciationCostReportItemJiSan);
            }

            for(ZjxlReport zjxl:zjxlReports) {
                zjDepreciationCostReportItemYang.setDeptName("杨村");
                zjDepreciationCostReportItemYang.setDeptType("1");
                this.monthCost("杨村",zjxl,zjDepreciationCostReportItemYang,month);
            }
            Double totalYang = totalheji(zjDepreciationCostReportItemYang);//杨村合计
            zjDepreciationCostReportItemYang.setDeptSum(totalYang);
            if(totalYang!=0.0){
                zjDepreciationCostReportItemListHome.add(zjDepreciationCostReportItemYang);
            }
            zjDepreciationCostReport.setZjDepreciationCostReportItemsHome(zjDepreciationCostReportItemListHome);

            //计算本部矿小计
            List<ZjDepreciationCostReportItemSumHome> zjDepreciationCostReportItemSumHomes = this.totalxiaojihome(zjDepreciationCostReportItemListHome);
            zjDepreciationCostReport.setZjDepreciationCostReportItemSumHomes(zjDepreciationCostReportItemSumHomes);

            //外部矿
            ZjDepreciationCostReportItem zjDepreciationCostReportItemZhao = new ZjDepreciationCostReportItem();
            ZjDepreciationCostReportItem zjDepreciationCostReportItemTian = new ZjDepreciationCostReportItem();
            ZjDepreciationCostReportItem zjDepreciationCostReportItemZhuan = new ZjDepreciationCostReportItem();
            ZjDepreciationCostReportItem zjDepreciationCostReportItemAn = new ZjDepreciationCostReportItem();
            ZjDepreciationCostReportItem zjDepreciationCostReportItemShi = new ZjDepreciationCostReportItem();
            ZjDepreciationCostReportItem zjDepreciationCostReportItemYing = new ZjDepreciationCostReportItem();
            ZjDepreciationCostReportItem zjDepreciationCostReportItemLiu = new ZjDepreciationCostReportItem();
            ZjDepreciationCostReportItem zjDepreciationCostReportItemLong = new ZjDepreciationCostReportItem();
            for(ZjxlReport zjxl:zjxlReports) {
                zjDepreciationCostReportItemZhao.setDeptName("赵楼");
                zjDepreciationCostReportItemZhao.setDeptType("2");
                this.monthCost("赵楼",zjxl,zjDepreciationCostReportItemZhao,month);
            }
            Double totalzhao = totalheji(zjDepreciationCostReportItemZhao);//赵楼合计
            zjDepreciationCostReportItemZhao.setDeptSum(totalzhao);
            if(totalzhao!=0.0){
                zjDepreciationCostReportItemListEnter.add(zjDepreciationCostReportItemZhao);
            }

            for(ZjxlReport zjxl:zjxlReports) {
                zjDepreciationCostReportItemTian.setDeptName("天池");
                zjDepreciationCostReportItemTian.setDeptType("2");
                this.monthCost("天池",zjxl,zjDepreciationCostReportItemTian,month);
            }
            Double totaltian = totalheji(zjDepreciationCostReportItemTian);//天池合计
            zjDepreciationCostReportItemTian.setDeptSum(totaltian);
            if(totaltian!=0.0){
                zjDepreciationCostReportItemListEnter.add(zjDepreciationCostReportItemTian);
            }

            for(ZjxlReport zjxl:zjxlReports) {
                zjDepreciationCostReportItemZhuan.setDeptName("转龙湾");
                zjDepreciationCostReportItemZhuan.setDeptType("2");
                this.monthCost("转龙湾",zjxl,zjDepreciationCostReportItemZhuan,month);
            }
            Double totalzhuan = totalheji(zjDepreciationCostReportItemZhuan);//转龙湾合计
            zjDepreciationCostReportItemZhuan.setDeptSum(totalzhuan);
            if(totalzhuan!=0.0){
                zjDepreciationCostReportItemListEnter.add(zjDepreciationCostReportItemZhuan);
            }


            for(ZjxlReport zjxl:zjxlReports) {
                zjDepreciationCostReportItemAn.setDeptName("安源");
                zjDepreciationCostReportItemAn.setDeptType("2");
                this.monthCost("安源",zjxl,zjDepreciationCostReportItemAn,month);
            }
            Double totalAn = totalheji(zjDepreciationCostReportItemAn);//安源合计
            zjDepreciationCostReportItemAn.setDeptSum(totalAn);
            if(totalAn!=0.0){
                zjDepreciationCostReportItemListEnter.add(zjDepreciationCostReportItemAn);
            }

            for(ZjxlReport zjxl:zjxlReports) {
                zjDepreciationCostReportItemShi.setDeptName("石拉乌素");
                zjDepreciationCostReportItemShi.setDeptType("2");
                this.monthCost("石拉乌素",zjxl,zjDepreciationCostReportItemShi,month);
            }
            Double totalShi= totalheji(zjDepreciationCostReportItemShi);//石拉乌素合计
            zjDepreciationCostReportItemShi.setDeptSum(totalShi);
            if(totalShi!=0.0){
                zjDepreciationCostReportItemListEnter.add(zjDepreciationCostReportItemShi);
            }

            for(ZjxlReport zjxl:zjxlReports) {
                zjDepreciationCostReportItemYing.setDeptName("营盘壕");
                zjDepreciationCostReportItemYing.setDeptType("2");
                this.monthCost("营盘壕",zjxl,zjDepreciationCostReportItemYing,month);
            }
            Double totalYing= totalheji(zjDepreciationCostReportItemYing);//营盘壕合计
            zjDepreciationCostReportItemYing.setDeptSum(totalYing);
            if(totalYing!=0.0){
                zjDepreciationCostReportItemListEnter.add(zjDepreciationCostReportItemYing);
            }

            for(ZjxlReport zjxl:zjxlReports) {
                zjDepreciationCostReportItemLiu.setDeptName("硫磺沟");
                zjDepreciationCostReportItemLiu.setDeptType("2");
                this.monthCost("硫磺沟",zjxl,zjDepreciationCostReportItemLiu,month);
            }
            Double totalLiu = totalheji(zjDepreciationCostReportItemLiu);//硫磺沟合计
            zjDepreciationCostReportItemLiu.setDeptSum(totalLiu);
            if(totalLiu!=0.0){
                zjDepreciationCostReportItemListEnter.add(zjDepreciationCostReportItemLiu);
            }

            for(ZjxlReport zjxl:zjxlReports) {
                zjDepreciationCostReportItemLong.setDeptName("龙凤矿");
                zjDepreciationCostReportItemLong.setDeptType("2");
                this.monthCost("龙凤矿",zjxl,zjDepreciationCostReportItemLong,month);
            }
            Double totalLong = totalheji(zjDepreciationCostReportItemLong);//龙凤矿合计
            zjDepreciationCostReportItemLong.setDeptSum(totalLong);
            if(totalLong!=0.0){
                zjDepreciationCostReportItemListEnter.add(zjDepreciationCostReportItemLong);
            }

            zjDepreciationCostReport.setZjDepreciationCostReportItemsExternal(zjDepreciationCostReportItemListEnter);
            //计算外部矿小计
            List<ZjDepreciationCostReportItemSumExternal> zjDepreciationCostReportItemSumExternals = this.totalxiaojienter(zjDepreciationCostReportItemListEnter);
            zjDepreciationCostReport.setZjDepreciationCostReportItemSumExternals(zjDepreciationCostReportItemSumExternals);

            //计算累计
            List<ZjDepreciationCostReportItemAddUpSum> zjDepAddUpSums = this.addUpSum(zjDepreciationCostReportItemSumExternals,zjDepreciationCostReportItemSumHomes);

            zjDepreciationCostReport.setZjDepreciationCostReportItemAddUpSums(zjDepAddUpSums);

        }

        return zjDepreciationCostReport;
    }

    //计算累计
    private List<ZjDepreciationCostReportItemAddUpSum> addUpSum(List<ZjDepreciationCostReportItemSumExternal> zjDepreciationCostReportItemSumExternals,
                                                                List<ZjDepreciationCostReportItemSumHome> zjDepreciationCostReportItemSumHomes){

        List<ZjDepreciationCostReportItemAddUpSum> zjDepAddUpSums = new ArrayList<ZjDepreciationCostReportItemAddUpSum>();
        ZjDepreciationCostReportItemAddUpSum zjDepreciationCostReportItemAddUpSum = new ZjDepreciationCostReportItemAddUpSum();
        ZjDepreciationCostReportItemSumExternal zjDepreciationCostReportItemSumExternal = new ZjDepreciationCostReportItemSumExternal();
        ZjDepreciationCostReportItemSumHome zjDepreciationCostReportItemSumHome = new ZjDepreciationCostReportItemSumHome();
        for(ZjDepreciationCostReportItemSumExternal zjDepreciationCostReportItemSumExter:zjDepreciationCostReportItemSumExternals){
            zjDepreciationCostReportItemSumExternal.setMonth01xiaoji(zjDepreciationCostReportItemSumExter.getMonth01xiaoji());
            zjDepreciationCostReportItemSumExternal.setMonth02xiaoji(zjDepreciationCostReportItemSumExter.getMonth02xiaoji());
            zjDepreciationCostReportItemSumExternal.setMonth03xiaoji(zjDepreciationCostReportItemSumExter.getMonth03xiaoji());
            zjDepreciationCostReportItemSumExternal.setMonth04xiaoji(zjDepreciationCostReportItemSumExter.getMonth04xiaoji());
            zjDepreciationCostReportItemSumExternal.setMonth05xiaoji(zjDepreciationCostReportItemSumExter.getMonth05xiaoji());
            zjDepreciationCostReportItemSumExternal.setMonth06xiaoji(zjDepreciationCostReportItemSumExter.getMonth06xiaoji());
            zjDepreciationCostReportItemSumExternal.setMonth07xiaoji(zjDepreciationCostReportItemSumExter.getMonth07xiaoji());
            zjDepreciationCostReportItemSumExternal.setMonth08xiaoji(zjDepreciationCostReportItemSumExter.getMonth08xiaoji());
            zjDepreciationCostReportItemSumExternal.setMonth09xiaoji(zjDepreciationCostReportItemSumExter.getMonth09xiaoji());
            zjDepreciationCostReportItemSumExternal.setMonth10xiaoji(zjDepreciationCostReportItemSumExter.getMonth10xiaoji());
            zjDepreciationCostReportItemSumExternal.setMonth11xiaoji(zjDepreciationCostReportItemSumExter.getMonth11xiaoji());
            zjDepreciationCostReportItemSumExternal.setMonth12xiaoji(zjDepreciationCostReportItemSumExter.getMonth12xiaoji());
            zjDepreciationCostReportItemSumExternal.setMoreDeptSum(zjDepreciationCostReportItemSumExter.getMoreDeptSum());
        }
        for(ZjDepreciationCostReportItemSumHome zjDepreciationCostReportItemSumH:zjDepreciationCostReportItemSumHomes){
            zjDepreciationCostReportItemSumHome.setMonth01xiaoji(zjDepreciationCostReportItemSumH.getMonth01xiaoji());
            zjDepreciationCostReportItemSumHome.setMonth02xiaoji(zjDepreciationCostReportItemSumH.getMonth02xiaoji());
            zjDepreciationCostReportItemSumHome.setMonth03xiaoji(zjDepreciationCostReportItemSumH.getMonth03xiaoji());
            zjDepreciationCostReportItemSumHome.setMonth04xiaoji(zjDepreciationCostReportItemSumH.getMonth04xiaoji());
            zjDepreciationCostReportItemSumHome.setMonth05xiaoji(zjDepreciationCostReportItemSumH.getMonth05xiaoji());
            zjDepreciationCostReportItemSumHome.setMonth06xiaoji(zjDepreciationCostReportItemSumH.getMonth06xiaoji());
            zjDepreciationCostReportItemSumHome.setMonth07xiaoji(zjDepreciationCostReportItemSumH.getMonth07xiaoji());
            zjDepreciationCostReportItemSumHome.setMonth08xiaoji(zjDepreciationCostReportItemSumH.getMonth08xiaoji());
            zjDepreciationCostReportItemSumHome.setMonth09xiaoji(zjDepreciationCostReportItemSumH.getMonth09xiaoji());
            zjDepreciationCostReportItemSumHome.setMonth10xiaoji(zjDepreciationCostReportItemSumH.getMonth10xiaoji());
            zjDepreciationCostReportItemSumHome.setMonth11xiaoji(zjDepreciationCostReportItemSumH.getMonth11xiaoji());
            zjDepreciationCostReportItemSumHome.setMonth12xiaoji(zjDepreciationCostReportItemSumH.getMonth12xiaoji());
            zjDepreciationCostReportItemSumHome.setMoreDeptSum(zjDepreciationCostReportItemSumH.getMoreDeptSum());
        }
        BigDecimal totalxiaojihome = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumExternal.getMoreDeptSum()));
        BigDecimal totalxiaojiexter = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumHome.getMoreDeptSum()));
        zjDepreciationCostReportItemAddUpSum.setMoreDeptAddUpSum(totalxiaojihome.add(totalxiaojiexter).doubleValue());//合计本部外部累计
        BigDecimal b1 = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumExternal.getMonth01xiaoji()));
        BigDecimal b1E = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumHome.getMonth01xiaoji()));
        zjDepreciationCostReportItemAddUpSum.setAddUpSum01(b1.add(b1E).doubleValue());//一月份本部外部累计
        BigDecimal b2 = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumExternal.getMonth02xiaoji()));
        BigDecimal b2E = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumHome.getMonth02xiaoji()));
        zjDepreciationCostReportItemAddUpSum.setAddUpSum02(b2.add(b2E).doubleValue());//二月份本部外部累计
        BigDecimal b3 = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumExternal.getMonth03xiaoji()));
        BigDecimal b3E = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumHome.getMonth03xiaoji()));
        zjDepreciationCostReportItemAddUpSum.setAddUpSum03(b3.add(b3E).doubleValue());//三月份本部外部累计
        BigDecimal b4 = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumExternal.getMonth04xiaoji()));
        BigDecimal b4E = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumHome.getMonth04xiaoji()));
        zjDepreciationCostReportItemAddUpSum.setAddUpSum04(b4.add(b4E).doubleValue());//四月份本部外部累计
        BigDecimal b5 = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumExternal.getMonth05xiaoji()));
        BigDecimal b5E = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumHome.getMonth05xiaoji()));
        zjDepreciationCostReportItemAddUpSum.setAddUpSum05(b5.add(b5E).doubleValue());//五月份本部外部累计
        BigDecimal b6 = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumExternal.getMonth06xiaoji()));
        BigDecimal b6E = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumHome.getMonth06xiaoji()));
        zjDepreciationCostReportItemAddUpSum.setAddUpSum06(b6.add(b6E).doubleValue());//六月份本部外部累计
        BigDecimal b7 = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumExternal.getMonth07xiaoji()));
        BigDecimal b7E = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumHome.getMonth07xiaoji()));
        zjDepreciationCostReportItemAddUpSum.setAddUpSum07(b7.add(b7E).doubleValue());//七月份本部外部累计
        BigDecimal b8 = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumExternal.getMonth08xiaoji()));
        BigDecimal b8E = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumHome.getMonth08xiaoji()));
        zjDepreciationCostReportItemAddUpSum.setAddUpSum08(b8.add(b8E).doubleValue());//八月份本部外部累计
        BigDecimal b9 = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumExternal.getMonth09xiaoji()));
        BigDecimal b9E = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumHome.getMonth09xiaoji()));
        zjDepreciationCostReportItemAddUpSum.setAddUpSum09(b9.add(b9E).doubleValue());//九月份本部外部累计
        BigDecimal b10 = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumExternal.getMonth10xiaoji()));
        BigDecimal b10E = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumHome.getMonth10xiaoji()));
        zjDepreciationCostReportItemAddUpSum.setAddUpSum10(b10.add(b10E).doubleValue());//十月份本部外部累计
        BigDecimal b11 = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumExternal.getMonth11xiaoji()));
        BigDecimal b11E = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumHome.getMonth11xiaoji()));
        zjDepreciationCostReportItemAddUpSum.setAddUpSum11(b11.add(b11E).doubleValue());//十一月份本部外部累计
        BigDecimal b12 = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumExternal.getMonth12xiaoji()));
        BigDecimal b12E = new BigDecimal(Double.toString(zjDepreciationCostReportItemSumHome.getMonth12xiaoji()));
        zjDepreciationCostReportItemAddUpSum.setAddUpSum12(b12.add(b12E).doubleValue());//十二月份本部外部累计
        zjDepAddUpSums.add(zjDepreciationCostReportItemAddUpSum);
//        zjCostRepairReport.setZjDepreciationCostReportItemAddUpSums(zjDepAddUpSums);
       return zjDepAddUpSums;

    }

    //计算每个月的合计
    private double totalheji( ZjDepreciationCostReportItem zjDepreciationCostReportItem) {
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
        }
        if(zjDepreciationCostReportItem.getFebruaryRepairsCost()!= null ){
            b2 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getFebruaryRepairsCost()));
        }
        if(zjDepreciationCostReportItem.getMarchRepairsCost()!= null ){
            b3 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getMarchRepairsCost()));
        }
        if(zjDepreciationCostReportItem.getAprilRepairsCost()!= null ){
            b4 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getAprilRepairsCost()));
        }
        if(zjDepreciationCostReportItem.getMayRepairsCost()!= null ){
            b5 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getMayRepairsCost()));
        }
        if(zjDepreciationCostReportItem.getJuneRepairsCost()!= null ){
            b6 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getJuneRepairsCost()));
        }
        if(zjDepreciationCostReportItem.getJulyRepairsCost()!= null ){
            b7 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getJulyRepairsCost()));
        }
        if(zjDepreciationCostReportItem.getAugustRepairsCost()!= null ){
            b8 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getAugustRepairsCost()));
        }
        if(zjDepreciationCostReportItem.getSepRepairsCost()!= null ){
            b9 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getSepRepairsCost()));
        }
        if(zjDepreciationCostReportItem.getOctRepairsCost()!= null ){
            b10 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getOctRepairsCost()));
        }
        if(zjDepreciationCostReportItem.getNovRepairsCost()!= null ){
            b11 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getNovRepairsCost()));
        }
        if(zjDepreciationCostReportItem.getDecRepairsCost()!= null ){
            b12 = new BigDecimal(Double.toString(zjDepreciationCostReportItem.getDecRepairsCost()));
        }
        double totalT = b1.add(b2).add(b3).add(b4).add(b5).add(b6).add(b7).add(b8).add(b9).add(b10).add(b11).add(b12).doubleValue();
        return totalT;
    }

    //计算外部矿的小计
    private List<ZjDepreciationCostReportItemSumExternal> totalxiaojienter( List<ZjDepreciationCostReportItem> zjDepreciationCostReportItemListEnter) {

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
        for (ZjDepreciationCostReportItem zjDepreciationCostReportItem : zjDepreciationCostReportItemListEnter){
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

//            zjCostRepairReport.setZjDepreciationCostReportItemsHome(zjDepreciationCostReportItem);
        }

        List<ZjDepreciationCostReportItemSumExternal> moreDeptSumList = new ArrayList<ZjDepreciationCostReportItemSumExternal>();
        ZjDepreciationCostReportItemSumExternal zjDepreciationCostReportItemSum = new ZjDepreciationCostReportItemSumExternal();
        zjDepreciationCostReportItemSum.setMoreDeptSum(totalxiaoji);//合计的总合计
        zjDepreciationCostReportItemSum.setMonth01xiaoji(month01xiaoji);//小计(本部所有矿每个月的小计)
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
//        zjCostRepairReport.setZjDepreciationCostReportItemSumHomes(moreDeptSumList);
        return moreDeptSumList;

    }

    //计算本部矿的小计
    private List<ZjDepreciationCostReportItemSumHome> totalxiaojihome( List<ZjDepreciationCostReportItem> zjDepreciationCostReportItemListHome) {

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
        for (ZjDepreciationCostReportItem zjDepreciationCostReportItem : zjDepreciationCostReportItemListHome){
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

//            zjCostRepairReport.setZjDepreciationCostReportItemsHome(zjDepreciationCostReportItem);
         }

        List<ZjDepreciationCostReportItemSumHome> moreDeptSumList = new ArrayList<ZjDepreciationCostReportItemSumHome>();
        ZjDepreciationCostReportItemSumHome zjDepreciationCostReportItemSum = new ZjDepreciationCostReportItemSumHome();
        zjDepreciationCostReportItemSum.setMoreDeptSum(totalxiaoji);//合计的总合计
        zjDepreciationCostReportItemSum.setMonth01xiaoji(month01xiaoji);//小计(本部所有矿每个月的小计)
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
//        zjCostRepairReport.setZjDepreciationCostReportItemSumHomes(moreDeptSumList);
        return moreDeptSumList;

    }

    private ZjDepreciationCostReportItem monthCost(String deptName,ZjxlReport zjxl,
                                                   ZjDepreciationCostReportItem zjDepreciationCostReportItem,
                                                   String month){
        if(zjxl.getUseDepartment().equals(deptName)) {

            Integer zjxlMonth = Integer.parseInt(zjxl.getZjxlMonth());//从原始库中查出月份数据
            if(StringUtils.isEmpty(month)){
                month = "12";
            }
            Integer foreMonth =Integer.parseInt(month);//前台传过来的月份

            //判断传过来的月份，将所得的月份数据存到对应的字段上
            if (zjxl.getZjxlMonth().equals("1") && zjxlMonth <= foreMonth) {
                zjDepreciationCostReportItem.setJanuaryRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("2") && zjxlMonth <= foreMonth) {
                zjDepreciationCostReportItem.setFebruaryRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("3") && zjxlMonth <= foreMonth) {
                zjDepreciationCostReportItem.setMarchRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("4") && zjxlMonth <= foreMonth) {
                zjDepreciationCostReportItem.setAprilRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("5") && zjxlMonth <= foreMonth) {
                zjDepreciationCostReportItem.setMayRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("6") && zjxlMonth <= foreMonth) {
                zjDepreciationCostReportItem.setJuneRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("7") && zjxlMonth <= foreMonth) {
                zjDepreciationCostReportItem.setJulyRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("8") && zjxlMonth <= foreMonth) {
                zjDepreciationCostReportItem.setAugustRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("9") && zjxlMonth <= foreMonth) {
                zjDepreciationCostReportItem.setSepRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("10") && zjxlMonth <= foreMonth) {
                zjDepreciationCostReportItem.setOctRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("11") && zjxlMonth <= foreMonth) {
                zjDepreciationCostReportItem.setNovRepairsCost(zjxl.getSum());
            }
            if (zjxl.getZjxlMonth().equals("12") && zjxlMonth <= foreMonth) {
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
                zjDepreciationCostReportItemSum.setMonth01xiaoji(month01xiaoji);//小计(本部所有矿每个月的小计)
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
            zjDepreciationCostReportItemAddUpSum.setMoreDeptAddUpSum(totalxiaojihome.add(totalxiaojiexter).doubleValue());//合计本部外部累计
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

    public int creatDepCostReport() {
        int res = 1;
        //获取当前的年月
        Calendar cale = Calendar.getInstance();
        int month = cale.get(Calendar.MONTH) + 1;
        int year = cale.get(Calendar.YEAR);

        //查询当前年月的各矿的综机租赁费合计
        ZjxlReport zjxlReport = new ZjxlReport();
        zjxlReport.setZjxlYear(String.valueOf(year));
        zjxlReport.setZjxlMonth("2");
        List<ZjxlReport> list = zjxlReportMapper.list(zjxlReport);
        Map zjCostRepairMap = new HashMap();
        zjCostRepairMap.put("yearTime",String.valueOf(year));
        zjCostRepairMap.put("monthTime","2");
        if(list != null && list.size() > 0){
            ZjDepreciationCostReport zjCostRepairReport = zjDepreciationCostReportMapper.list(zjCostRepairMap);
            if(zjCostRepairReport != null){
                for(int i=0;i<list.size();i++) {
                    ZjxlReport zReport = list.get(i);
                    ZjDepreciationCostReportItem record = new ZjDepreciationCostReportItem();
//                    record.setReportId(zjCostRepairReport.getId());
//                    record.setDeptCode("1001");//需要传过来
                    Map zjCostRepairItemMap = new HashMap();
                    zjCostRepairItemMap.put("reportId",zjCostRepairReport.getId());
                    zjCostRepairItemMap.put("reportId","1100");

                    List<ZjDepreciationCostReportItem> items = zjDepCReportItemMapper.list(zjCostRepairItemMap);
                    //判断汇总的明细是否存在,存在则更新,不存在则添加
                    if(items != null && items.size() > 0){
                        ZjDepreciationCostReportItem zjDepreciationCostReportItem = items.get(0);
                        if(month == 1){zjDepreciationCostReportItem.setJanuaryRepairsCost(zReport.getSum());}
                        if(month == 2){zjDepreciationCostReportItem.setFebruaryRepairsCost(zReport.getSum());}
                        if(month == 3){zjDepreciationCostReportItem.setMarchRepairsCost(zReport.getSum());}
                        if(month == 4){zjDepreciationCostReportItem.setAprilRepairsCost(zReport.getSum());}
                        if(month == 5){zjDepreciationCostReportItem.setMayRepairsCost(zReport.getSum());}
                        if(month == 6){zjDepreciationCostReportItem.setJuneRepairsCost(zReport.getSum());}
                        if(month == 7){zjDepreciationCostReportItem.setJulyRepairsCost(zReport.getSum());}
                        if(month == 8){zjDepreciationCostReportItem.setAugustRepairsCost(zReport.getSum());}
                        if(month == 9){zjDepreciationCostReportItem.setSepRepairsCost(zReport.getSum());}
                        if(month == 10){zjDepreciationCostReportItem.setOctRepairsCost(zReport.getSum());}
                        if(month == 11){zjDepreciationCostReportItem.setNovRepairsCost(zReport.getSum());}
                        if(month == 12){zjDepreciationCostReportItem.setDecRepairsCost(zReport.getSum());}
//                        res = zjDepreciationCostReportItem.update(zjDepreciationCostReportItem);
                    }else{
                        ZjDepreciationCostReportItem zjDepreciationCostReportItem = new ZjDepreciationCostReportItem();
                        zjDepreciationCostReportItem.setDeptCode("1111");
                        zjDepreciationCostReportItem.setDeptName(zReport.getUseDepartment());
                        zjDepreciationCostReportItem.setDeptType(String.valueOf(zReport.getKb()));
                        zjDepreciationCostReportItem.setReportId(zjCostRepairReport.getId());
                        if(month == 1){zjDepreciationCostReportItem.setJanuaryRepairsCost(zReport.getSum());}
                        if(month == 2){zjDepreciationCostReportItem.setFebruaryRepairsCost(zReport.getSum());}
                        if(month == 3){zjDepreciationCostReportItem.setMarchRepairsCost(zReport.getSum());}
                        if(month == 4){zjDepreciationCostReportItem.setAprilRepairsCost(zReport.getSum());}
                        if(month == 5){zjDepreciationCostReportItem.setMayRepairsCost(zReport.getSum());}
                        if(month == 6){zjDepreciationCostReportItem.setJuneRepairsCost(zReport.getSum());}
                        if(month == 7){zjDepreciationCostReportItem.setJulyRepairsCost(zReport.getSum());}
                        if(month == 8){zjDepreciationCostReportItem.setAugustRepairsCost(zReport.getSum());}
                        if(month == 9){zjDepreciationCostReportItem.setSepRepairsCost(zReport.getSum());}
                        if(month == 10){zjDepreciationCostReportItem.setOctRepairsCost(zReport.getSum());}
                        if(month == 11){zjDepreciationCostReportItem.setNovRepairsCost(zReport.getSum());}
                        if(month == 12){zjDepreciationCostReportItem.setDecRepairsCost(zReport.getSum());}
//                        res = reportEquipmentZMonthItemMapper.insert(reportEquipmentZMonthItem);
                    }
                }
            }
        }
        return res;
    }





}
