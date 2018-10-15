package com.yankuang.equipment.web.restful;

import com.alibaba.fastjson.JSON;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.UuidUtils;
import com.yankuang.equipment.equipment.model.ElEquipmentGroup;
import com.yankuang.equipment.equipment.model.ElProducePlan;
import com.yankuang.equipment.equipment.model.ElProduceSurface;
import com.yankuang.equipment.equipment.service.ElEquipmentGroupService;
import com.yankuang.equipment.equipment.service.ElProducePlanService;
import com.yankuang.equipment.equipment.service.ElProduceSurfaceService;
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
 * @Date: 2018-10-08 11:47
 * @Description:
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/elProducePlan")
public class ElProducePlanController {

    @RpcConsumer
    ElProducePlanService elProducePlanService;
    @RpcConsumer
    ElProduceSurfaceService elProduceSurfaceService;
    @RpcConsumer
    ElEquipmentGroupService elEquipmentGroupService;
    @Autowired
    UserFromRedis userFromRedis;

    /**
     * 添加生产接续计划
     * @param jsonString
     * @return
     */
    @PostMapping
    public CommonResponse create(@RequestBody String jsonString) {
        try {
            if (StringUtils.isEmpty(jsonString)) return CommonResponse.errorMsg("参数不得为空");
            ElProducePlan elProducePlan = JsonUtils.jsonToPojo(jsonString, ElProducePlan.class);
            if (elProducePlan == null) return CommonResponse.errorMsg("参数不得为空");
            String positionCode = elProducePlan.getPlanPosition();
            if (StringUtils.isEmpty(positionCode)) return CommonResponse.errorMsg("矿单位不得为空");
            ElProducePlan producePlan = new ElProducePlan();
            producePlan.setPlanPosition(positionCode);
            producePlan.setStartTime(elProducePlan.getStartTime());
            producePlan.setEndTime(elProducePlan.getEndTime());
            List<Map<String,Object>> plans = elProducePlanService.findByCondition(producePlan);
            if (plans != null && plans.size() > 0) return CommonResponse.errorMsg("该矿单位已添加接续计划，请使用编辑功能");
            // 获取当前登录用户信息
            UserDTO userDTO = userFromRedis.findByToken();
            if (userDTO != null) {
                elProducePlan.setCreateBy(userDTO.getCode());
                elProducePlan.setUpdateBy(userDTO.getCode());
            }

            elProducePlan.setAssetCode("1180");
            boolean res = elProducePlanService.create(elProducePlan);
            if (!res) return CommonResponse.errorMsg("添加接续计划失败");
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+ JSON.toJSONString(e));
        }
    }

    /**
     * 查询生产接续计划
     * @param assetCode
     * @param startTime
     * @param endTime
     * @return
     */
    @GetMapping
    public CommonResponse findByCondition(@RequestParam String assetCode,
                                          @RequestParam String startTime,
                                          @RequestParam String endTime) {
        try {
            if (StringUtils.isEmpty(assetCode)) return CommonResponse.errorMsg("请补充资产公司");
            if (StringUtils.isEmpty(startTime)) return CommonResponse.errorMsg("请补充开始时间");
            if (StringUtils.isEmpty(endTime)) return CommonResponse.errorMsg("请补充截止时间");

            ElProducePlan elProducePlan = new ElProducePlan();
            elProducePlan.setAssetCode(assetCode);
            elProducePlan.setStartTime(startTime);
            elProducePlan.setEndTime(endTime);
            List<Map<String,Object>> elProducePlans = elProducePlanService.findByCondition(elProducePlan);
            if (elProducePlans == null || elProducePlans.size() <= 0) return CommonResponse.build(200, "查询结果为空", null);

            return CommonResponse.ok(elProducePlans);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

    /**
     * 删除接续计划记录
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public CommonResponse delete(@PathVariable(value = "id") Long id) {
        try {
            if (id == null) return CommonResponse.errorMsg("参数参数不得为空");
            boolean res = elProducePlanService.delete(id);
            if (!res) return CommonResponse.errorMsg("删除失败");
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

    /**
     * 主键查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CommonResponse findById(@PathVariable("id") Long id) {
        try {
            if (id == null) return CommonResponse.errorMsg("id不得为空");
            ElProducePlan producePlan = elProducePlanService.findById(id);
            if (producePlan == null) return CommonResponse.build(200, "查询结果为空", null);
            return CommonResponse.ok(producePlan);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

    /**
     * 添加工作面
     * @param jsonString
     * @return
     */
    @PostMapping("/surface")
    public CommonResponse createSurface(@RequestBody String jsonString){
        try {
            if (StringUtils.isEmpty(jsonString)) return CommonResponse.errorMsg("参数不得为空");
            ElProduceSurface surface = JsonUtils.jsonToPojo(jsonString, ElProduceSurface.class);
            if (surface == null) return CommonResponse.errorMsg("参数不得为空");
            ElProduceSurface surfaceI = new ElProduceSurface();
            surfaceI.setPlanCode(surface.getPlanCode());
            surfaceI.setEffectCode(surface.getEffectCode());
            List<ElProduceSurface> surfaces = elProduceSurfaceService.findByCondition(surfaceI);
            if (surfaces != null && surfaces.size() > 0) return CommonResponse.errorMsg("该工作面存在，请使用编辑功能");
            // 获取当前登录用户信息
            UserDTO userDTO = userFromRedis.findByToken();
            if (userDTO != null) {
                surface.setCreateBy(userDTO.getCode());
                surface.setUpdateBy(userDTO.getCode());
            }
            surface.setCode(UuidUtils.newUuid());
            boolean res = elProduceSurfaceService.create(surface);
            if (!res) return CommonResponse.errorMsg("添加失败");
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

    /**
     * 添加设备组明细
     * @param jsonString
     * @return
     */
    @PostMapping("/group")
    public CommonResponse createGroup(@RequestBody String jsonString) {
        try {
            if (StringUtils.isEmpty(jsonString)) return CommonResponse.errorMsg("参数不得为空");
            ElEquipmentGroup group = JsonUtils.jsonToPojo(jsonString, ElEquipmentGroup.class);
            if (group == null) return CommonResponse.errorMsg("参数不得为空");
            ElEquipmentGroup groupI = new ElEquipmentGroup();
            groupI.setSurfaceCode(group.getSurfaceCode());
            groupI.setSbModelCode(group.getSbModelCode());
            groupI.setParamName(group.getParamName());
            groupI.setParamValue(group.getParamValue());
            List<ElEquipmentGroup> groups = elEquipmentGroupService.findByCondition(groupI);
            if (groups != null && groups.size() > 0) return CommonResponse.errorMsg("该设备已存在，修改设备数量完成操作");
            // 获取当前登录用户信息
            UserDTO userDTO = userFromRedis.findByToken();
            if (userDTO != null) {
                group.setCreateBy(userDTO.getCode());
                group.setUpdateBy(userDTO.getCode());
            }
            boolean res = elEquipmentGroupService.create(group);
            if (!res) return CommonResponse.errorMsg("添加失败");
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

    /**
     * 更新工作面
     * @param jsonString
     * @return
     */
    @PutMapping("/surface")
    public CommonResponse updateSurface(@RequestBody String jsonString) {
        try {
            if (StringUtils.isEmpty(jsonString)) return CommonResponse.errorMsg("参数不得为空");
            ElProduceSurface surface = JsonUtils.jsonToPojo(jsonString, ElProduceSurface.class);
            if (surface == null) return CommonResponse.errorMsg("参数不得为空");
            if (surface.getId() == null) return CommonResponse.errorMsg("id不得为空");
            // 获取当前登录用户信息
            UserDTO userDTO = userFromRedis.findByToken();
            if (userDTO != null) {
                surface.setUpdateBy(userDTO.getCode());
            }
            boolean res = elProduceSurfaceService.update(surface);
            if (!res) return CommonResponse.errorMsg("更新失败");
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

    /**
     * 更新设备组明细
     * @param jsonString
     * @return
     */
    @PutMapping("/group")
    public CommonResponse updateGroup(@RequestBody String jsonString) {
        try {
            if (StringUtils.isEmpty(jsonString)) return CommonResponse.errorMsg("参数不得为空");
            ElEquipmentGroup group = JsonUtils.jsonToPojo(jsonString, ElEquipmentGroup.class);
            if (group == null) return CommonResponse.errorMsg("参数不得为空");
            if (group.getId() == null) return CommonResponse.errorMsg("id不得为空");
            // 获取当前登录用户信息
            UserDTO userDTO = userFromRedis.findByToken();
            if (userDTO != null) {
                group.setUpdateBy(userDTO.getCode());
            }
            boolean res = elEquipmentGroupService.update(group);
            if (!res) return CommonResponse.errorMsg("更新失败");
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

    /**
     * 切换工作面获取设备组
     * @param planYear
     * @param effectCode
     * @return
     */
    @GetMapping("/switchSurface")
    public CommonResponse switchSurface(@RequestParam(defaultValue = "") String planYear,
                                        @RequestParam(defaultValue = "") String effectCode) {
        try {
            if (StringUtils.isEmpty(planYear)) return CommonResponse.errorMsg("计划年度不得为空");
            if (StringUtils.isEmpty(effectCode)) return CommonResponse.errorMsg("工作面不得为空");
            List<ElEquipmentGroup> groups = elProducePlanService.findBySurfaceCode(planYear, effectCode);
            if (groups == null || groups.size() <= 0) return CommonResponse.build(200, "查询结果为空", null);
            return CommonResponse.ok(groups);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }
}
