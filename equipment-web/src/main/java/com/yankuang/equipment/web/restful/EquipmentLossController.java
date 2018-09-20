package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.equipment.model.EquipmentLoss;
import com.yankuang.equipment.equipment.service.EquipmentLossService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/eloss")
public class EquipmentLossController {

    @RpcConsumer
    EquipmentLossService equipmentLossService;

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

        EquipmentLoss equipmentLoss = JsonUtils.jsonToPojo(jsonString,EquipmentLoss.class);

        if (equipmentLoss == null){
            return CommonResponse.build(500,"传入对象为空",null);
        }

        if (equipmentLoss.getUseDeptCode() == null || "".equals(equipmentLoss.getUseDeptCode())){
            return CommonResponse.build(500,"使用单位Code不能为空",null);
        }

        if (equipmentLoss.getUseMonth() == null || "".equals(equipmentLoss.getUseDeptName())){
            return CommonResponse.build(500,"领用月份不能为空",null);
        }

        if (equipmentLoss.getUseYear() == null || "".equals(equipmentLoss.getUseYear())){
            return CommonResponse.build(500,"领用年份不能为空",null);
        }

        if (equipmentLoss.getUseDeptName() == null || "".equals(equipmentLoss.getUseDeptName())){
            return CommonResponse.build(500,"传入单位不能为空",null);
        }

        if (equipmentLossService.create(equipmentLoss)){
            return CommonResponse.ok();
        }

        return CommonResponse.build(500,"创建失败",null);
    }

    /**
     * 查询设备新度清单
     * @param page
     * @param size
     * @param jsonString
     * @return
     */
    @GetMapping
    public CommonResponse ZNewReportList(@RequestParam Integer page,
                                         @RequestParam Integer size,
                                         @RequestParam String jsonString){
        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.build(500,"传入参数不能为空",null);
        }

        EquipmentLoss equipmentLoss = JsonUtils.jsonToPojo(jsonString,EquipmentLoss.class);

        if (equipmentLoss == null){
            return CommonResponse.build(500,"传入对象为空",null);
        }

        if (equipmentLoss.getUseDeptCode() == null || "".equals(equipmentLoss.getUseDeptCode())){
            return CommonResponse.build(500,"使用单位Code不能为空",null);
        }

        if (equipmentLoss.getUseMonth() == null || "".equals(equipmentLoss.getUseMonth())){
            return CommonResponse.build(500,"领用月份不能为空",null);
        }

        if (equipmentLoss.getUseYear() == null || "".equals(equipmentLoss.getUseYear())){
            return CommonResponse.build(500,"领用年份不能为空",null);
        }

        if (equipmentLoss.getUseDeptName() == null || "".equals(equipmentLoss.getUseDeptName())){
            return CommonResponse.build(500,"传入单位不能为空",null);
        }

        return CommonResponse.ok(equipmentLossService.list(equipmentLoss));
    }
}
