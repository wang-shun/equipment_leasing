package com.yankuang.equipment.web.restful;

import com.alibaba.fastjson.JSON;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.UuidUtils;
import com.yankuang.equipment.equipment.model.ElEquipmentGroupConfig;
import com.yankuang.equipment.equipment.service.ElEquipmentGroupConfigService;
import com.yankuang.equipment.web.dto.UserDTO;
import com.yankuang.equipment.web.util.UserFromRedis;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Auther: zyy
 * @Date: 2018-10-11 16:54
 * @Description:
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/elGroupConfig")
public class ElEquipmentGroupConfigController {

    @RpcConsumer
    ElEquipmentGroupConfigService elEquipmentGroupConfigService;
    @Autowired
    UserFromRedis userFromRedis;

    /**
     * 添加配置设备组
     * @param jsonString
     * @return
     */
    @PostMapping
    public CommonResponse createBatch(@RequestBody String jsonString) {

        try {
            if (StringUtils.isEmpty(jsonString)) return CommonResponse.errorMsg("请补充参数");
            List<ElEquipmentGroupConfig> groupConfigs = JsonUtils.jsonToList(jsonString, ElEquipmentGroupConfig.class);
            if (groupConfigs == null || groupConfigs.size() <= 0) return CommonResponse.errorMsg("请补充参数");
            String configYear = "";
            String assetCode = "1180";
            for (ElEquipmentGroupConfig config : groupConfigs) {
                // 获取当前登录用户信息
                UserDTO userDTO = userFromRedis.findByToken();
                if (userDTO != null) {
                    config.setCreateBy(userDTO.getCode());
                    config.setUpdateBy(userDTO.getCode());
                }
                config.setCode(UuidUtils.newUuid());
                if (StringUtils.isEmpty(configYear)) configYear = config.getConfigYear();
            }
            if (!StringUtils.isEmpty(configYear)) {
                ElEquipmentGroupConfig groupConfig = new ElEquipmentGroupConfig();
                groupConfig.setConfigYear(configYear);
                groupConfig.setAssetCode(assetCode);
                List<Map<String, Object>> configs = elEquipmentGroupConfigService.findByCondition(groupConfig);
                if (configs != null && configs.size() > 0) return CommonResponse.errorMsg(configYear+"年的设备组已经配置，如需改动请使用编辑功能");
            }
            boolean res = elEquipmentGroupConfigService.createBatch(groupConfigs);
            if (!res) return CommonResponse.errorMsg("添加失败");

            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+ JSON.toJSONString(e));
        }
    }

    /**
     * 查询配置设备组列表
     * @param configYear
     * @param assetCode
     * @return
     */
    @GetMapping
    public CommonResponse findByCondition(@RequestParam(defaultValue = "") String configYear,
                                          @RequestParam(defaultValue = "1180") String assetCode) {
        try {
            ElEquipmentGroupConfig groupConfig = new ElEquipmentGroupConfig();
            groupConfig.setConfigYear(configYear);
            groupConfig.setAssetCode(assetCode);
            List<Map<String, Object>> groupConfigs = elEquipmentGroupConfigService.findByCondition(groupConfig);
            if (groupConfigs == null || groupConfigs.size() <= 0) return CommonResponse.build(200, "查询结果为空", null);
            return CommonResponse.ok(groupConfigs);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

    /**
     * 删除配套设备组
     * @param ids
     * @return
     */
    @DeleteMapping
    public CommonResponse delete(@RequestParam String ids) {
        try {
            if (StringUtils.isEmpty(ids)) return CommonResponse.errorMsg("参数不得为空");
            String[] idsI = ids.split(",");
            if (idsI.length <= 0) return CommonResponse.errorMsg("参数不得为空");
            boolean res = false;
            for (String id : idsI) {
                res = elEquipmentGroupConfigService.delete(Long.valueOf(id));
                if (!res) break;
            }
            if (!res) return CommonResponse.errorMsg("删除失败");
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

    /**
     * 主键查询
     * @param ids
     * @return
     */
    @GetMapping("/find")
    public CommonResponse findById(@RequestParam String ids) {
        try {
            if (StringUtils.isEmpty(ids)) return CommonResponse.errorMsg("参数不得为空");
            String[] idsI = ids.split(",");
            if (idsI.length <= 0) return CommonResponse.errorMsg("参数不得为空");
            Map<String,Object> groupConfig = elEquipmentGroupConfigService.findByIds(idsI);
            if (groupConfig == null || groupConfig.size() == 0) return CommonResponse.errorMsg("查询结果为空");
            return CommonResponse.ok(groupConfig);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorMsg("服务异常："+JSON.toJSONString(e));
        }
    }

    /**
     * 编辑更新配置设备组
     * @param jsonString
     * @return
     */
    @PutMapping
    public CommonResponse update(@RequestBody String jsonString) {
        try {
            if (StringUtils.isEmpty(jsonString)) return CommonResponse.errorMsg("参数不得为空");
            ElEquipmentGroupConfig groupConfigs = JsonUtils.jsonToPojo(jsonString, ElEquipmentGroupConfig.class);
            if (groupConfigs == null) return CommonResponse.errorMsg("参数不得为空");
            if (groupConfigs.getId() == null) return CommonResponse.errorMsg("主键ID不得为空");
            ElEquipmentGroupConfig config = elEquipmentGroupConfigService.findById(groupConfigs.getId());
            if (config == null) return CommonResponse.errorMsg("该条配置记录已过期");
            boolean res = elEquipmentGroupConfigService.update(groupConfigs);
            if (!res) return CommonResponse.errorMsg("更新失败");
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

}
