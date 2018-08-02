package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.leasing.model.ElPlan;
import com.yankuang.equipment.leasing.service.ElPlanService;
import com.yankuang.equipment.web.util.CommonResponse;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhouy on 2018/7/31.
 */
@RestController
@RequestMapping("/v1/elplan")
public class ElPlanController {

    @RpcConsumer(version = "0.0.1", check = "false")
    private ElPlanService elPlanService;

    /**
     * 创建通用设备月度租赁计划
     * @param elPlan
     * @return
     */
    @RequestMapping(value = "/{equipmentType}/{planType}", method = RequestMethod.POST)
    public CommonResponse create (@PathVariable(value = "equipmentType") String equipmentType,
                                  @PathVariable(value = "planType") String planType,
                                  @RequestBody ElPlan elPlan) {

        Boolean res = false;
        try {
            if (StringUtils.isNullObject(elPlan)) {
                return CommonResponse.errorMsg("设备租赁计划不得为空");
            }
            if (StringUtils.isEmpty(equipmentType) || StringUtils.isEmpty(planType)) {
                return CommonResponse.errorMsg("设备租赁计划url有误");
            }

            // 获取租赁计划设备类型
            if (!StringUtils.isEmpty(equipmentType) && "generic".equals(equipmentType)) {
                elPlan.setPlanEquipmentType("2");
            }
            if (!StringUtils.isEmpty(equipmentType) && "integrated".equals(equipmentType)) {
                elPlan.setPlanEquipmentType("3");
            }

            // 获取设备租赁计划类型
            if (!StringUtils.isEmpty(planType) && "urgent".equals(planType)) {
                elPlan.setPlanEquipmentType("4");
            }
            if (!StringUtils.isEmpty(planType) && "month".equals(planType)) {
                elPlan.setPlanEquipmentType("3");
            }
            if (!StringUtils.isEmpty(planType) && "quarter".equals(planType)) {
                elPlan.setPlanEquipmentType("2");
            }
            if (!StringUtils.isEmpty(planType) && "year".equals(planType)) {
                elPlan.setPlanEquipmentType("1");
            }

            // 存储数据
            res = elPlanService.create(elPlan);
            if (!res) {
                return CommonResponse.errorMsg("租赁计划创建失败");
            }
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("租赁计划服务异常");
        }
    }


    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public CommonResponse update (@RequestBody ElPlan elPlan) {

        Boolean res = false;
        try {
            if (elPlan == null || StringUtils.isEmpty(elPlan.getPlanId())) {
                return CommonResponse.errorMsg("通用设备租赁计划ID不得为空");
            }
            res = elPlanService.update(elPlan);
            if (!res) {
                return CommonResponse.errorMsg("租赁计划更新失败");
            }
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("租赁计划更新服务异常");
        }

    }

    /**
     * 通过主键查询设备租赁计划
     * @param planId
     * @return
     */
    @RequestMapping(value = "/{planId}",  method = RequestMethod.GET)
    public CommonResponse getElPlan (@PathVariable(value = "planId") String planId) {

        try {
            if (StringUtils.isEmpty(planId)) {
                return CommonResponse.errorMsg("设备租赁计划ID不得为空");
            }
            ElPlan elPlan = elPlanService.findElPlanById(planId);
            if (elPlan == null) {
                return CommonResponse.build(201, "设备租赁计划查询为空", null);
            }
            return CommonResponse.ok(elPlan);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return CommonResponse.errorException("设备租赁计划查询服务异常");
        }
    }
}
