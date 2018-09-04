package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.Constants;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.equipment.model.ElPlanItem;
import com.yankuang.equipment.equipment.model.ElPlanUse;
import com.yankuang.equipment.equipment.service.ElPlanUseService;
import com.yankuang.equipment.equipment.service.ElUseService;
import com.yankuang.equipment.web.dto.ElPlanDTO;
import io.swagger.util.Json;
import org.springframework.util.StringUtils;
import com.yankuang.equipment.equipment.model.ElPlan;
import com.yankuang.equipment.equipment.service.ElPlanService;
import com.yankuang.equipment.web.dto.ElPlanUseDTO;
import com.yankuang.equipment.web.service.ElPlanPlusService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import io.terminus.common.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * Created by zhouy on 2018/7/31.
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/elplan")
public class ElPlanController {

    @RpcConsumer
    ElPlanService elPlanService;

    @Autowired
    ElPlanPlusService elPlanPlusService;

    @RpcConsumer
    ElPlanUseService elPlanUseService;

    @RpcConsumer
    ElUseService elUseService;

    /**
     * 创建通用设备月度租赁计划
     * @param elPlan
     * @return
     */
    @PostMapping("/{equipmentType}/{planType}")
    public CommonResponse create (@PathVariable(value = "equipmentType") String equipmentType,
                                  @PathVariable(value = "planType") String planType,
                                  @RequestBody ElPlan elPlan) {

        Boolean res = false;
        try {
            if (StringUtils.isEmpty(elPlan)) {
                return CommonResponse.errorMsg("设备租赁计划不得为空");
            }
            if (StringUtils.isEmpty(equipmentType) || StringUtils.isEmpty(planType)) {
                return CommonResponse.errorMsg("设备租赁计划url有误");
            }
            if (StringUtils.isEmpty(elPlan.getPlanCreatorId())) {
                return CommonResponse.errorMsg("计划提出人ID不得为空");
            }
            if (StringUtils.isEmpty(elPlan.getPlanCreatorName())) {
                return CommonResponse.errorMsg("计划提出人姓名不得为空");
            }
            if (StringUtils.isEmpty(elPlan.getPlanYear())) {
                return CommonResponse.errorMsg("需求年度不得为空");
            }
            if (StringUtils.isEmpty(elPlan.getPlanPosition())) {
                return CommonResponse.errorMsg("提出单位不得为空");
            }
            if (elPlan.getElPlanItemList() == null && elPlan.getElPlanItemList().size() <= 0) {
                return CommonResponse.errorMsg("设备列表记录不得为空");
            }

            // 获取租赁计划设备类型
            if (!StringUtils.isEmpty(equipmentType) && Constants.PLANEQUIPMENTTYPE_GENERIC_VALUE.equals(equipmentType)) {
                elPlan.setPlanEquipmentType(Constants.PLANEQUIPMENTTYPE_GENERIC);
            }
            if (!StringUtils.isEmpty(equipmentType) && Constants.PLANEQUIPMENTTYPE_INTEGRATED_VALUE.equals(equipmentType)) {
                elPlan.setPlanEquipmentType(Constants.PLANEQUIPMENTTYPE_INTEGRATED);
            }

            // 获取设备租赁计划类型
            if (!StringUtils.isEmpty(planType) && Constants.PLANTYPE_URGENT_VALUE.equals(planType)) {
                if (StringUtils.isEmpty(elPlan.getPlanMonth())) {
                    return CommonResponse.errorMsg("需求月度不得为空");
                }
                elPlan.setPlanType(Constants.PLANTYPE_URGENT);
            }
            if (!StringUtils.isEmpty(planType) && Constants.PLANTYPE_MONTH_VALUE.equals(planType)) {
                if (StringUtils.isEmpty(elPlan.getPlanMonth())) {
                    return CommonResponse.errorMsg("需求月度不得为空");
                }
                elPlan.setPlanType(Constants.PLANTYPE_MONTH);
            }
            if (!StringUtils.isEmpty(planType) && Constants.PLANTYPE_QUARTER_VALUE.equals(planType)) {
                if (StringUtils.isEmpty(elPlan.getPlanQuarter())) {
                    return CommonResponse.errorMsg("需求季度不得为空");
                }
                elPlan.setPlanType(Constants.PLANTYPE_QUARTER);
            }
            if (!StringUtils.isEmpty(planType) && Constants.PLANTYPE_YEAR_VALUE.equals(planType)) {
                elPlan.setPlanType(Constants.PLANTYPE_YEAR);
            }

            if (!Constants.PLANEQUIPMENTTYPE_GENERIC.equals(elPlan.getPlanEquipmentType())
                    && !Constants.PLANEQUIPMENTTYPE_INTEGRATED.equals(elPlan.getPlanEquipmentType())) {
                return CommonResponse.errorMsg("设备租赁计划url有误");
            }
            if (!Constants.PLANTYPE_YEAR.equals(elPlan.getPlanType())
                    && !Constants.PLANTYPE_QUARTER.equals(elPlan.getPlanType())
                    && !Constants.PLANTYPE_MONTH.equals(elPlan.getPlanType())
                    && !Constants.PLANTYPE_URGENT.equals(elPlan.getPlanType())) {
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
    @PutMapping("")
    public CommonResponse update (@RequestBody ElPlan elPlan) {

        Boolean res = false;
        try {
            if (elPlan == null || StringUtils.isEmpty(elPlan.getPlanId())) {
                return CommonResponse.errorMsg("通用设备租赁计划ID不得为空");
            }
            if (StringUtils.isEmpty(elPlan.getPlanUpdatorName())) {
                return CommonResponse.errorMsg("编辑修改人姓名不得为空");
            }
            if (StringUtils.isEmpty(elPlan.getPlanUpdatorId())) {
                return CommonResponse.errorMsg("编辑修改人ID不得为空");
            }
            ElPlan plan = elPlanService.findElPlanById(elPlan.getPlanId());
            if (plan == null) {
                return CommonResponse.errorMsg("该条租赁计划已过期");
            }
            if (Constants.PLANSTATUS_COMMITED.equals(plan.getPlanStatus())) {
                return CommonResponse.errorMsg("该条租赁计划已提交，不能编辑修改");
            }
            if (Constants.PLANSTATUS_FAILED.equals(plan.getPlanStatus())
                    || Constants.PLANSTATUS_PASSED.equals(plan.getPlanStatus())) {
                return CommonResponse.errorMsg("该条租赁计划已审核，不能编辑修改");
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
    @DeleteMapping("/{planId}")
    public CommonResponse delete(@PathVariable(value = "planId") String planId) {

        Boolean res = false;
        try {
            if (StringUtils.isEmpty(planId)) {
                return CommonResponse.errorMsg("租赁计划ID不得为空");
            }
            ElPlan plan = elPlanService.findElPlanById(planId);
            if (plan == null) {
                return CommonResponse.errorMsg("该条租赁计划已过期");
            }
            if (Constants.PLANSTATUS_COMMITED.equals(plan.getPlanStatus())) {
                return CommonResponse.errorMsg("该条租赁计划已提交，不能编辑修改");
            }
            if (Constants.PLANSTATUS_FAILED.equals(plan.getPlanStatus())
                    || Constants.PLANSTATUS_PASSED.equals(plan.getPlanStatus())) {
                return CommonResponse.errorMsg("该条租赁计划已审核，不能编辑修改");
            }
            res = elPlanService.deletePlan(planId);
            if (!res) {
                return CommonResponse.errorMsg("设备租赁计划删除失败");
            }
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("设备租赁计划删除服务异常");
        }
    }

    /**
     * 删除租赁计划明细记录
     * @return
     */
    @DeleteMapping("/planItem/{itemId}")
    public CommonResponse deleteItem(@PathVariable(value = "itemId") String itemId) {

        try {
            // 检验参数
            if (StringUtils.isEmpty(itemId)) {
                return CommonResponse.errorMsg("设备列表记录ID不得为空");
            }

            // 执行操作
            boolean res = elPlanService.deleteItemById(itemId);
            if (!res) {
                return CommonResponse.errorMsg("删除设备列表记录失败");
            }
            return CommonResponse.ok("删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.ok("删除失败");
        }
    }

    /**
     * 通过主键查询设备租赁计划
     * @param planId
     * @return
     */
    @GetMapping("/{planId}")
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
    @PostMapping("/list/{equipmentType}/{planType}")
    public CommonResponse getElPlans(@RequestBody ElPlan elPlan, @RequestParam(value = "pageSize", defaultValue = "30") Integer pageSize,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @PathVariable(value = "equipmentType") String equipmentType,
                                     @PathVariable(value = "planType") String planType) {
        try {
            // 租赁设备类型
            if (!StringUtils.isEmpty(equipmentType) && Constants.PLANEQUIPMENTTYPE_GENERIC_VALUE.equals(equipmentType)) {
                equipmentType = Constants.PLANEQUIPMENTTYPE_GENERIC;
            }
            if (!StringUtils.isEmpty(equipmentType) && Constants.PLANEQUIPMENTTYPE_INTEGRATED_VALUE.equals(equipmentType)) {
                equipmentType = Constants.PLANEQUIPMENTTYPE_INTEGRATED;
            }
            if (!Constants.PLANEQUIPMENTTYPE_GENERIC.equals(equipmentType)
                    && !Constants.PLANEQUIPMENTTYPE_INTEGRATED.equals(equipmentType)) {
                return CommonResponse.errorMsg("请求路径书写有误");
            }

            // 租赁计划类型
            if (!StringUtils.isEmpty(planType) && Constants.PLANTYPE_YEAR_VALUE.equals(planType)) {
                planType = Constants.PLANTYPE_YEAR;
            }
            if (!StringUtils.isEmpty(planType) && Constants.PLANTYPE_QUARTER_VALUE.equals(planType)) {
                planType = Constants.PLANTYPE_QUARTER;
            }
            if (!StringUtils.isEmpty(planType) && Constants.PLANTYPE_MONTH_VALUE.equals(planType)) {
                planType = Constants.PLANTYPE_MONTH;
            }
            if (!StringUtils.isEmpty(planType) && Constants.PLANTYPE_URGENT_VALUE.equals(planType)) {
                planType = Constants.PLANTYPE_URGENT;
            }
            if (!Constants.PLANTYPE_YEAR.equals(planType)
                    && Constants.PLANTYPE_QUARTER.equals(planType)
                    && Constants.PLANTYPE_MONTH.equals(planType)
                    && Constants.PLANTYPE_URGENT.equals(planType)) {
                return CommonResponse.errorMsg("请求路径书写有误");
            }

            // 查询数据
            pageSize = pageSize == null ? Constants.PAGE_SIZE : pageSize;
            pageNum = pageNum == null ? 1 : pageNum;
            elPlan.setPlanEquipmentType(equipmentType);
            elPlan.setPlanType(planType);
            Paging page = elPlanService.findElPlanByCondition(elPlan,pageSize,pageNum);
            if (page == null || page.getTotal() <= 0) {
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
    @PostMapping("/approve/{approvalType}")
    public CommonResponse approval(@PathVariable(value = "approvalType") String approvalType,
                                   @RequestBody ElPlan elPlan) {

        try {
            // 数据验证
            if (StringUtils.isEmpty(approvalType)) {
                return CommonResponse.errorMsg("请求路径书写有误");
            }
            if (elPlan == null || StringUtils.isEmpty(elPlan.getPlanId())) {
                return CommonResponse.errorMsg("请补充租赁计划ID");
            }

            // 填充数据
            ElPlan plan = elPlanService.findElPlanById(elPlan.getPlanId());
            if (plan == null ) {
                return CommonResponse.errorMsg("该条租赁计划已过期");
            }
            if (plan.getElPlanItemList() != null && plan.getElPlanItemList().size() > 0) {
                elPlan.setElPlanItemList(plan.getElPlanItemList());
            } else {
                return CommonResponse.errorMsg("设备列表不得为空");
            }
            if ("submit".equals(approvalType)) {
                if (Constants.PLANSTATUS_COMMITED.equals(plan.getPlanStatus())
                        || Constants.PLANSTATUS_PASSED.equals(plan.getPlanStatus())
                        || Constants.PLANSTATUS_FAILED.equals(plan.getPlanStatus())) {
                    return CommonResponse.errorMsg("该条租赁计划已提交,不能重复提交");
                }
                if (StringUtils.isEmpty(elPlan.getPlanUpdatorName())) {
                    return CommonResponse.errorMsg("编辑修改人姓名不得为空");
                }
                if (StringUtils.isEmpty(elPlan.getPlanUpdatorId())) {
                    return CommonResponse.errorMsg("编辑修改人ID不得为空");
                }
                elPlan.setPlanStatus(Constants.PLANSTATUS_COMMITED);
                elPlan.setPlanUpdateTime(new Date().getTime());
            }
            if ("passed".equals(approvalType)) {
                elPlan.setPlanStatus(Constants.PLANSTATUS_PASSED);
                if (StringUtils.isEmpty(elPlan.getPlanApproverId())) {
                    return CommonResponse.errorMsg("请补充审批人ID");
                }
                if (StringUtils.isEmpty(elPlan.getPlanApproverName())) {
                    return CommonResponse.errorMsg("请补充审批人姓名");
                }
                if (Constants.PLANSTATUS_UNCOMMITED.equals(plan.getPlanStatus())) {
                    return CommonResponse.errorMsg("该条租赁计划未提交，不能审核");
                }
                if (Constants.PLANSTATUS_FAILED.equals(plan.getPlanStatus())
                        || Constants.PLANSTATUS_PASSED.equals(plan.getPlanStatus())) {
                    return CommonResponse.errorMsg("该条租赁计划已审核，不能重复审核");
                }
                elPlan.setPlanApproveTime(new Date().getTime());
            }
            if ("failed".equals(approvalType)) {
                elPlan.setPlanStatus(Constants.PLANSTATUS_FAILED);
                if (StringUtils.isEmpty(elPlan.getPlanApproverId())) {
                    return CommonResponse.errorMsg("请补充审批人ID");
                }
                if (StringUtils.isEmpty(elPlan.getPlanApproverName())) {
                    return CommonResponse.errorMsg("请补充审批人姓名");
                }
                if (Constants.PLANSTATUS_UNCOMMITED.equals(plan.getPlanStatus())) {
                    return CommonResponse.errorMsg("该条租赁计划未提交，不能审核");
                }
                if (Constants.PLANSTATUS_FAILED.equals(plan.getPlanStatus())
                        || Constants.PLANSTATUS_PASSED.equals(plan.getPlanStatus())) {
                    return CommonResponse.errorMsg("该条租赁计划已审核，不能重复审核");
                }
                elPlan.setPlanApproveTime(new Date().getTime());
            }

            // 保存数据
            CommonResponse result = elPlanPlusService.approve(elPlan);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常");
        }
    }

    /**
     * 查询租赁计划备用设备
     * @param elPlanUse
     * @return
     */
    @PostMapping("/findElPlanUse")
    public CommonResponse findElPlanUseList(@RequestBody ElPlanUse elPlanUse) {
        try {

            if (StringUtils.isEmpty(elPlanUse.getEquipmentType())) {
                return CommonResponse.errorMsg("设备类型不得为空");
            }
            if (StringUtils.isEmpty(elPlanUse.getCenterYear())) {
                return CommonResponse.errorMsg("需求年度不得为空");
            }
            if ("1".equals(elPlanUse.getEquipmentType())) {
                if (StringUtils.isEmpty(elPlanUse.getCenterMonth())) {
                    return CommonResponse.errorMsg("需求月度不得为空");
                }
            }
            if (StringUtils.isEmpty(elPlanUse.getPositionId())) {
                return CommonResponse.errorMsg("使用单位不得为空");
            }

            List<ElPlanUseDTO> list = elPlanPlusService.findElPlanUseList(elPlanUse);
            if (list == null || list.size() == 0) {
                return CommonResponse.errorMsg("查询结果为空");
            }
            return CommonResponse.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常");
        }
    }

    /**
     * @method 分页查询在租设备列表
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/findByCreatorId")
    public CommonResponse findByCreatorId(@RequestParam( defaultValue = "1") Integer page,
                                          @RequestParam( defaultValue = "20" ) Integer size,
                                          @RequestParam String jsonString){
        Map elPlanUseMap = new HashMap();
        if (!StringUtils.isEmpty(jsonString)){
            ElPlanDTO elPlanDTO = JsonUtils.jsonToPojo(jsonString,ElPlanDTO.class);
            if (elPlanDTO == null){
                return CommonResponse.errorMsg("参数对象不能为空");
            }
            if (elPlanDTO.getPlanYear() == null){
                return CommonResponse.errorMsg("计划年度不能为空");
            }
            if (elPlanDTO.getPlanEquipmentType() == null){
                return CommonResponse.errorMsg("设备类型不能为空");
            }
            if ("1".equals(elPlanDTO.getPlanEquipmentType())){
                if (elPlanDTO.getPlanMonth() == null){
                    return CommonResponse.errorMsg("月份不能为空");
                }
                elPlanDTO.setPlanType("3");
            }else if ("2".equals(elPlanDTO.getPlanEquipmentType())){
                elPlanDTO.setPlanType("1");
            }
            ElPlan elPlan = new ElPlan();
            if (elPlanDTO.getPlanMonth() != null || "".equals(elPlanDTO.getPlanMonth())){
                elPlan.setPlanMonth(elPlanDTO.getPlanMonth());
            }
            elPlan.setPlanYear(elPlanDTO.getPlanYear());
            elPlan.setPlanEquipmentType(elPlanDTO.getPlanEquipmentType());
            elPlan.setPlanType(elPlanDTO.getPlanType());
            List<ElPlan> elPlans = elPlanService.findByCreatorId(elPlan);
            if (elPlans.size() <= 0 ){
                return CommonResponse.ok( );
            }
            List<String> planIds = new ArrayList<>();
            ElPlanItem elPlanItem = new ElPlanItem();
            for (ElPlan elPlan1:elPlans){
                elPlanItem.setPlanId(elPlan1.getPlanId());
                elPlanItem.setPositionId(0L);//TODO 此值暂时写死
                List<ElPlanItem> elPlanItems = elUseService.findByPlanId(elPlanItem);
                if (elPlanItems.size() <= 0){
                    return CommonResponse.errorMsg("没有该计划");
                }
                for (ElPlanItem elPlanItem1 :elPlanItems){
                    planIds.add(elPlanItem1.getItemId());
                }
            }
            if (planIds.size() < 1){
                return CommonResponse.errorMsg("不存在该计划");
            }
            elPlanUseMap.put("planItemId",planIds);
            elPlanUseMap.put("smallTypeCode",elPlanDTO.getSmallTypeCode());
            elPlanUseMap.put("middleTypeCode",elPlanDTO.getMiddleTypeCode());
            elPlanUseMap.put("bigTypeCode",elPlanDTO.getBigTypeCode());
            elPlanUseMap.put("equipmentCode",elPlanDTO.getEquipmentCode());
            elPlanUseMap.put("effectCode",elPlanDTO.getEffectCode());
            elPlanUseMap.put("equipmentModel",elPlanDTO.getEquipmentModel());
            elPlanUseMap.put("equipmentFactory",elPlanDTO.getEquipmentFactory());
            if(elPlanUseService.list(page,size,elPlanUseMap) == null){
                return CommonResponse.ok();
            }
        }
        return CommonResponse.ok( elPlanUseService.list(page,size,elPlanUseMap));
    }
}
