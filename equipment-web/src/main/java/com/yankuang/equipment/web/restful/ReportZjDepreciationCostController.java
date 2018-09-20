package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.equipment.model.ZjDepreciationCostReport;
import com.yankuang.equipment.equipment.model.ZjxlReport;
import com.yankuang.equipment.equipment.service.ZjDepreciationCostReportItemService;
import com.yankuang.equipment.equipment.service.ZjDepreciationCostReportService;
import com.yankuang.equipment.equipment.service.ZjxlReportService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    @RpcConsumer
    ZjDepreciationCostReportService zjDepreciationCostReportService;

    @RpcConsumer
    ZjxlReportService zjxlReportService;


    //这边需要从前台传（1180,1730(资产公司)，月份） 年月，煤业或东华或是汇总
    //折旧修理费分年汇总煤业，东华，和汇总（煤业东华之和）  assetComp,（1180(煤业),1730(东华)，1110（汇总）)
    @GetMapping("/findCostRepairList")
    public CommonResponse findCostRepairList(@RequestParam String jsonString) {

        if (com.yankuang.equipment.common.util.StringUtils.isEmpty(jsonString)) {
            return CommonResponse.build(500, "传入参数不能为空", null);
        }
        ZjDepreciationCostReport zjDepreciationCostReport = JsonUtils.jsonToPojo(jsonString, ZjDepreciationCostReport.class);
        if (StringUtils.isEmpty(zjDepreciationCostReport.getYearTime())) {
            return CommonResponse.errorMsg("年份不能为空");
        }if (StringUtils.isEmpty(zjDepreciationCostReport.getAssetComp())) {
            return CommonResponse.errorMsg("所属资产公司不能为空");
        }

        if (!StringUtils.isEmpty(zjDepreciationCostReport.getYearTime())) {
            //需要有个方法去查询新的报表中是否有这个年份这个月份的数据，有的话走新的，没有的话通过查询小伯的数据获得
//            if(){
//
//            }
            return CommonResponse.ok(zjDepreciationCostReportService.listzjxl(zjDepreciationCostReport));
        }
        //需要进行判断，进入哪个方法，先判断综机折旧修理费中有这个年份没，有的话就直接从综机折旧修理费的表查询
//        Integer count = zjDepreciationCostReportService.findCostRepairList(zjxlReport.getZjxlYear());
//        if(count>0){
//            zjCostRepairMap.put("assetComp",assetComp);
//            zjCostRepairMap.put("yearTime",zjxlReport.getZjxlYear());
//            return CommonResponse.ok(zjDepreciationCostReportService.list(zjCostRepairMap));
//        }else{

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


}
