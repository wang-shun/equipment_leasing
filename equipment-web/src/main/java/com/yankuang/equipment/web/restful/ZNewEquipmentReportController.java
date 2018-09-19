package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.equipment.model.ZNewReport;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/newReport")
public class ZNewEquipmentReportController {

    /**
     * 保存新设备
     * @param jsonString
     * @return
     */
    @PostMapping
    public CommonResponse ZNewReportCreate(@RequestBody String jsonString) {

        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.build(500,"传入参数不能为空",null);
        }

        ZNewReport zNewReport = JsonUtils.jsonToPojo(jsonString,ZNewReport.class);

        if (zNewReport == null){
            return CommonResponse.build(500,"传入对象为空",null);
        }

        if (zNewReport.getUseDeptCode() == null || "".equals(zNewReport.getUseDeptCode())){
            return CommonResponse.build(500,"使用单位Code不能为空",null);
        }

        if (zNewReport.getUseMonth() == null || "".equals(zNewReport.getUseDeptName())){
            return CommonResponse.build(500,"领用月份不能为空",null);
        }

        if (zNewReport.getUseYear() == null || "".equals(zNewReport.getUseYear())){
            return CommonResponse.build(500,"领用年份不能为空",null);
        }

        if (zNewReport.getUseDeptName() == null || "".equals(zNewReport.getUseDeptName())){
            return CommonResponse.build(500,"传入单位不能为空",null);
        }

        return CommonResponse.ok();
    }
}
