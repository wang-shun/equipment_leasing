package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.equipment.model.ZjDepreciationCostReport;
import com.yankuang.equipment.equipment.service.ZjDepreciationCostReportService;
import com.yankuang.equipment.web.task.ReportZjDepreciationCostTask;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xhh
 *  综机折旧修理费
 */

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/zjZjRepairFee")
public class ReportZjDepreciationCostController {

    public static final Logger logger = Logger.getLogger(ReportZjDepreciationCostTask.class);

    @RpcConsumer
    ZjDepreciationCostReportService zjDepreciationCostReportService;


    //这边需要从前台传（1180,1730(资产公司)，月份） 年月，煤业或东华或是汇总
    //折旧修理费分年汇总煤业，东华，和汇总（煤业东华之和）  assetComp,（1180(煤业),1730(东华)，1100（汇总）)
    @GetMapping("/findCostRepairList")
    public CommonResponse findCostRepairList(@RequestParam String jsonString) {

        if (com.yankuang.equipment.common.util.StringUtils.isEmpty(jsonString)) {
            return CommonResponse.build(500, "传入参数不能为空", null);
        }
        Map zjCostRepairMap = new HashMap();
        ZjDepreciationCostReport zjDepreciationCostReport = JsonUtils.jsonToPojo(jsonString, ZjDepreciationCostReport.class);
        if (StringUtils.isEmpty(zjDepreciationCostReport.getYearTime())) {
            return CommonResponse.errorMsg("年份不能为空");
        }if (StringUtils.isEmpty(zjDepreciationCostReport.getAssetComp())) {
            return CommonResponse.errorMsg("所属资产公司不能为空");
        }

        //查询全年
        if (!StringUtils.isEmpty(zjDepreciationCostReport.getYearTime()) && StringUtils.isEmpty(zjDepreciationCostReport.getMonthTime())) {
            //需要有个方法去查询新的报表中是否有这个年份这个月份的数据，有的话走新的，没有的话通过查询小伯的数据获得
           // 查询当前月
            Calendar cale = Calendar.getInstance();
            int month = cale.get(Calendar.MONTH) + 1;
            int day = cale.get(Calendar.DATE);

            Integer count = zjDepreciationCostReportService.findCostRepairList(zjDepreciationCostReport.getYearTime(),String.valueOf(month),zjDepreciationCostReport.getAssetComp());
            if((day>21 && day<31)){
                zjDepreciationCostReport.setMonthTime(String.valueOf(month));
                return CommonResponse.ok(zjDepreciationCostReportService.listzjxl(zjDepreciationCostReport));//连表查询
            }
            if(count>0){
                zjCostRepairMap.put("yearTime",zjDepreciationCostReport.getYearTime());
                zjCostRepairMap.put("monthTime",month);
                zjCostRepairMap.put("assetComp",zjDepreciationCostReport.getAssetComp());
                return CommonResponse.ok(zjDepreciationCostReportService.list(zjCostRepairMap));//从新表中查
            }else{
                zjDepreciationCostReport.setMonthTime(String.valueOf(month));
                return CommonResponse.ok(zjDepreciationCostReportService.listzjxl(zjDepreciationCostReport));//连表查询
            }
        }
        //查询有月份的
        if (!StringUtils.isEmpty(zjDepreciationCostReport.getYearTime()) && !StringUtils.isEmpty(zjDepreciationCostReport.getMonthTime())) {
            //需要有个方法去查询新的报表中是否有这个年份这个月份的数据，有的话走新的，没有的话通过查询小伯的数据获得
            Calendar cale = Calendar.getInstance();
            int month = cale.get(Calendar.MONTH) + 1;
            int day = cale.get(Calendar.DATE);
            Integer count = zjDepreciationCostReportService.findCostRepairList(zjDepreciationCostReport.getYearTime(),zjDepreciationCostReport.getMonthTime(),zjDepreciationCostReport.getAssetComp());
            if((day>21 && day<31)){
                return CommonResponse.ok(zjDepreciationCostReportService.listzjxl(zjDepreciationCostReport));//连表查询
            }
            if(count>0){
                zjCostRepairMap.put("yearTime",zjDepreciationCostReport.getYearTime());
                zjCostRepairMap.put("monthTime",month);
                zjCostRepairMap.put("assetComp",zjDepreciationCostReport.getAssetComp());
                return CommonResponse.ok(zjDepreciationCostReportService.list(zjCostRepairMap));//从新表中查
            }else{
                return CommonResponse.ok(zjDepreciationCostReportService.listzjxl(zjDepreciationCostReport));//连表查询
            }
        }
        return CommonResponse.ok();

    }


    /**
     * @method 设备使用交接单保存功能
     * @param jsonString
     * @return
     */
    @PostMapping("/saveZjDeCost")
    public CommonResponse saveZjDeCost(@RequestBody String jsonString){

        try{
            if (StringUtils.isEmpty(jsonString)) {
                return CommonResponse.errorTokenMsg("参数不能为空");
            }
            ZjDepreciationCostReport zjDepreciationCostReport = JsonUtils.jsonToPojo(jsonString, ZjDepreciationCostReport.class);

            zjDepreciationCostReportService.create(zjDepreciationCostReport);
            return CommonResponse.ok();
        }catch (Exception e){
            return CommonResponse.errorException("保存综机折旧修理费失败!");
        }
    }

    @PostMapping(value = "/creatDepCost")
    public CommonResponse creatDepCost(){
        try{
            logger.info("......................重新汇总各矿上月的综机租赁费开始......................");

            //获取当前的日期,只有在每月21号~31号之间才可以重新计算
            Calendar cale = Calendar.getInstance();
            int day = cale.get(Calendar.DATE);
//            if(day >= 21 && day <= 31){
                return CommonResponse.ok(zjDepreciationCostReportService.creatDepCostReport());
//            }else{
//                return CommonResponse.errorMsg("每月21号~31号之间才可以重新计算");
//            }
        }catch (Exception e){
            logger.info("......................重新汇总各矿上月的综机租赁费异常......................");
            return CommonResponse.errorException("重新汇总各矿上月的综机租赁费异常!");
        }
    }


}
