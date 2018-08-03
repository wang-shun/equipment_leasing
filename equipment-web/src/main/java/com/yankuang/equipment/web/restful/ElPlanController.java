package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.leasing.model.ElPlan;
import com.yankuang.equipment.leasing.service.ElPlanService;
import com.yankuang.equipment.web.util.CommonResponse;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import io.terminus.common.model.Paging;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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
    @CrossOrigin(maxAge = 3600)
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
                elPlan.setPlanType("4");
            }
            if (!StringUtils.isEmpty(planType) && "month".equals(planType)) {
                elPlan.setPlanType("3");
            }
            if (!StringUtils.isEmpty(planType) && "quarter".equals(planType)) {
                elPlan.setPlanType("2");
            }
            if (!StringUtils.isEmpty(planType) && "year".equals(planType)) {
                elPlan.setPlanType("1");
            }

            if (!"2".equals(elPlan.getPlanEquipmentType())
                    && !"3".equals(elPlan.getPlanEquipmentType())) {
                return CommonResponse.errorMsg("设备租赁计划url有误");
            }
            if (!"1".equals(elPlan.getPlanType())
                    && !"2".equals(elPlan.getPlanType())
                    && !"3".equals(elPlan.getPlanType())
                    && !"4".equals(elPlan.getPlanType())) {
                return CommonResponse.errorMsg("设备租赁计划url有误");
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

    /**
     * 更新设备租赁计划
     * @param elPlan
     * @return
     */
    @CrossOrigin(maxAge = 3600)
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
     * 删除设备租赁计划
     * @param planId
     * @return
     */
    @CrossOrigin(maxAge = 3600)
    @RequestMapping(value = "/{planId}", method = RequestMethod.DELETE)
    public CommonResponse delete(@PathVariable(value = "planId") String planId) {

        Boolean res = false;
        try {
            if (StringUtils.isEmpty(planId)) {
                return CommonResponse.errorMsg("租赁计划ID不得为空");
            }
            res = elPlanService.deletePlan(planId);
            if (!res) {
                return CommonResponse.errorException("设备租赁计划删除失败");
            }
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("设备租赁计划删除服务异常");
        }

    }

    /**
     * 通过主键查询设备租赁计划
     * @param planId
     * @return
     */
    @CrossOrigin(maxAge = 3600)
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

    /**
     * 分页条件查询设备租赁计划
     * @param elPlan
     * @param pageSize
     * @param pageNum
     * @param equipmentType
     * @param planType
     * @return
     */
    @CrossOrigin(maxAge = 3600)
    @RequestMapping(value = "/{equipmentType}/{planType}", method = RequestMethod.POST)
    public CommonResponse getElPlans(@RequestBody ElPlan elPlan,
                                     @RequestParam(value = "pageSize") int pageSize,
                                     @RequestParam(value = "pageNum") int pageNum,
                                     @PathVariable(value = "equipmentType") String equipmentType,
                                     @PathVariable(value = "planType") String planType) {
        try {
            // 租赁设备类型
            if (!StringUtils.isEmpty(equipmentType) && "generic".equals(equipmentType)) {
                equipmentType = "2";
            }
            if (!StringUtils.isEmpty(equipmentType) && "integrated".equals(equipmentType)) {
                equipmentType = "3";
            }
            if (!"2".equals(equipmentType) && !"3".equals(equipmentType)) {
                return CommonResponse.errorException("请求路径书写有误");
            }

            // 租赁计划类型
            if (!StringUtils.isEmpty(planType) && "year".equals(planType)) {
                planType = "1";
            }
            if (!StringUtils.isEmpty(planType) && "quarter".equals(planType)) {
                planType = "2";
            }
            if (!StringUtils.isEmpty(planType) && "month".equals(planType)) {
                planType = "3";
            }
            if (!StringUtils.isEmpty(planType) && "urgent".equals(planType)) {
                planType = "4";
            }
            if (!"1".equals(planType) && !"2".equals(planType)
                    && "3".equals(planType) && "4".equals(planType)) {
                return CommonResponse.errorException("请求路径书写有误");
            }

            // 查询数据
            elPlan.setPlanEquipmentType(equipmentType);
            elPlan.setPlanType(planType);
            Paging page = elPlanService.findElPlanByCondition(elPlan,pageSize,pageNum);
            if (page == null) {
                return CommonResponse.build(201, "查询结果为空", null);
            }
            return CommonResponse.ok(page);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("租赁计划查询服务异常");
        }
    }

    /**
     * 审批、提交操作
     * @param approvalType
     * @param elPlan
     * @return
     */
    @RequestMapping(value = "/approve/{approvalType}", method = RequestMethod.POST)
    public CommonResponse approval(@PathVariable(value = "approvalType") String approvalType,
                                   @RequestBody ElPlan elPlan) {

        try {
            // 数据验证
            if (StringUtils.isEmpty(approvalType)) {
                return CommonResponse.errorException("请求路径书写有误");
            }
            if (elPlan == null || StringUtils.isEmpty(elPlan.getPlanId())) {
                return CommonResponse.errorException("请补充租赁计划ID");
            }

            // 填充数据
            if ("submit".equals(approvalType)) {
                elPlan.setPlanStatus("2");
                elPlan.setPlanUpdateTime(new Date().getTime());
            }
            if ("pass".equals(approvalType)) {
                elPlan.setPlanStatus("4");
                if (StringUtils.isEmpty(elPlan.getPlanApproverId())) {
                    return CommonResponse.errorMsg("请补充审批人ID");
                }
                elPlan.setPlanApproveTime(new Date().getTime());
                elPlan.setPlanUpdateTime(new Date().getTime());
            }
            if ("failPass".equals(approvalType)) {
                elPlan.setPlanStatus("3");
                if (StringUtils.isEmpty(elPlan.getPlanApproverId())) {
                    return CommonResponse.errorMsg("请补充审批人ID");
                }
                elPlan.setPlanApproveTime(new Date().getTime());
                elPlan.setPlanUpdateTime(new Date().getTime());
            }

            // 保存数据
            boolean res = elPlanService.approve(elPlan);
            if (!res) {
                return CommonResponse.errorException("操作失败");
            }
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常");
        }
    }
}
