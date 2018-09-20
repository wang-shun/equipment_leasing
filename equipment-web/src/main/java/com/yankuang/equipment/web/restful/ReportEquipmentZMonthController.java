package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.equipment.model.ReportEquipmentZMonth;
import com.yankuang.equipment.equipment.model.ReportEquipmentZMonthItem;
import com.yankuang.equipment.equipment.service.ReportEquipmentZMonthService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/reportEquipmentZMonth")
public class ReportEquipmentZMonthController {

    public static final Logger logger = Logger.getLogger(ReportEquipmentZMonthController.class);

    @RpcConsumer
    ReportEquipmentZMonthService reportEquipmentZMonthService;

    @RequestMapping(value = "/findByYear/{year}",method = RequestMethod.GET)
    public CommonResponse findByYear(@PathVariable("year") String year){
        try{
            if (StringUtils.isEmpty(year)) {
                return CommonResponse.errorTokenMsg("年份不能为空");
            }
            return CommonResponse.ok(reportEquipmentZMonthService.findByYear(year));
        }catch (Exception e){
            return CommonResponse.errorException("查询综机折旧修理费月报失败!");
        }
    }

    @RequestMapping(value = "/findSumByYear/{year}",method = RequestMethod.GET)
    public CommonResponse findSumByYear(@PathVariable("year") String year){
        try{
            if (StringUtils.isEmpty(year)) {
                return CommonResponse.errorTokenMsg("年份不能为空");
            }
            return CommonResponse.ok(reportEquipmentZMonthService.findSumByYear(year));
        }catch (Exception e){
            return CommonResponse.errorException("查询综机折旧修理费月报(汇总)失败!");
        }
    }

    @RequestMapping(value = "/updateRemarkById",method = RequestMethod.PUT)
    public CommonResponse updateRemarkById(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.build(500, "传入参数不能为空", null);
        }
        try{
            ReportEquipmentZMonth reportEquipmentZMonth = JsonUtils.jsonToPojo(jsonString,ReportEquipmentZMonth.class);
            return CommonResponse.ok(reportEquipmentZMonthService.updateRemarkById(reportEquipmentZMonth));
        }catch (Exception e){
            return CommonResponse.errorException("更新综机折旧修理费月报的备注失败!");
        }
    }

    @RequestMapping(value = "/updateYearPlanValById",method = RequestMethod.PUT)
    public CommonResponse updateYearPlanValById(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.build(500, "传入参数不能为空", null);
        }
        try{
            ReportEquipmentZMonthItem reportEquipmentZMonthItem = JsonUtils.jsonToPojo(jsonString,ReportEquipmentZMonthItem.class);
            return CommonResponse.ok(reportEquipmentZMonthService.updateYearPlanValById(reportEquipmentZMonthItem));
        }catch (Exception e){
            return CommonResponse.errorException("更新综机折旧修理费月报的年度计划值失败!");
        }
    }
}
