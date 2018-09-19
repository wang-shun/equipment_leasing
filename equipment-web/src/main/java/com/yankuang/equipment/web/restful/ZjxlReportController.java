package com.yankuang.equipment.web.restful;

import com.fasterxml.jackson.databind.JsonSerializable;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.equipment.model.ZjxlReport;
import com.yankuang.equipment.equipment.service.ZjxlReportService;
import com.yankuang.equipment.web.dto.ZjxlZReportDTO;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
@RequestMapping("/v1/zjxl")
public class ZjxlReportController {

    @RpcConsumer
    ZjxlReportService zjxlReportService;

    /**
     * 在导出时向数据库中存入数据
     * @param jsonString
     * @return
     */
    @PostMapping
    public CommonResponse ZjxlReportCreate(@RequestBody String jsonString) {
        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.build(500, "传入参数不能为空", null);
        }

        ZjxlZReportDTO zjxlZReportDTO = JsonUtils.jsonToPojo(jsonString, ZjxlZReportDTO.class);
        //将dto转化成实体类
        List<ZjxlReport> zjxlReports = zjxlZReportDTO.getZjxlReports();
        if (zjxlReports == null || zjxlReports.size() <= 0){
            return CommonResponse.build(500,"传入参数不能为空",null);
        }
        for (ZjxlReport zjxlReport:zjxlReports){
            BeanUtils.copyProperties(zjxlZReportDTO, zjxlReport);

            if (zjxlReportService.create(zjxlReport)){
                continue;
            }
        }

        return CommonResponse.ok();
    }

    /**
     * 综机折旧修理费查询
     * @param page
     * @param size
     * @param jsonString
     * @return
     */
    @GetMapping
    public CommonResponse ZjxlReportSelect(@RequestParam Integer page,
                                           @RequestParam Integer size,
                                           @RequestParam String jsonString){
        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.build(500,"传入参数不能为空",null);
        }
        ZjxlReport zjxlReport = JsonUtils.jsonToPojo(jsonString,ZjxlReport.class);

        if (zjxlReport == null){
            return CommonResponse.build(500,"传入参数不能为空",null);
        }

        if(zjxlReport.getUseDepartment() == null || "".equals(zjxlReport.getUseDepartment())){
            return CommonResponse.build(500,"使用单位不能为空",null);
        }

        if (zjxlReport.getZjxlYear() == null || "".equals(zjxlReport.getZjxlYear())){
            return CommonResponse.build(500,"修理年限不能为空",null);
        }

        if (zjxlReport.getZjxlMonth() == null || "".equals(zjxlReport.getZjxlMonth())){
            return CommonResponse.build(500,"修理月份不能为空",null);
        }

        if (zjxlReport.getZjxlDay() == null || "".equals(zjxlReport.getZjxlDay())){
            return CommonResponse.build(500,"修理日期不能为空",null);
        }

        List<ZjxlReport> zjxlReports = zjxlReportService.list(zjxlReport);

        if (zjxlReports != null && zjxlReports.size() > 0){
            return CommonResponse.ok(zjxlReports);
        }


        return CommonResponse.ok(zjxlReports);
    }
}
