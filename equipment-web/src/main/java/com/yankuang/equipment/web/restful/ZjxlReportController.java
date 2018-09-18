package com.yankuang.equipment.web.restful;

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
}
