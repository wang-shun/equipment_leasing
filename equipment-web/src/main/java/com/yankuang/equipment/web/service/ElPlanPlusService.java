package com.yankuang.equipment.web.service;

import com.alibaba.fastjson.JSON;
import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.authority.service.DeptService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.Constants;
import com.yankuang.equipment.equipment.model.*;
import com.yankuang.equipment.equipment.service.*;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ElPlanPlusService {

    public static final Logger logger = Logger.getLogger(ElPlanPlusService.class);

    @RpcConsumer
    private ElPlanService elPlanService;

    @RpcConsumer
    DeptService deptService;

    @RpcConsumer
    SbPositionService sbPositionService;

    @RpcConsumer
    SbEquipmentTService sbEquipmentTService;

    @RpcConsumer
    SbEquipmentZService sbEquipmentZService;

    @RpcConsumer
    ElPlanUseService elPlanUseService;

    public CommonResponse approve(ElPlan elPlan) {
        try {
            logger.info("approve elPlan: " + JSON.toJSONString(elPlan));

            // 租赁计划审核通过，仓库开始备货
            Boolean resT = true;
            if (Constants.PLANSTATUS_PASSED.equals(elPlan.getPlanStatus())
                    && !StringUtils.isEmpty(elPlan.getPlanId())) {
                ElPlan plan = elPlanService.findElPlanById(elPlan.getPlanId());
                List<ElPlanItem> itemList = plan.getElPlanItemList();
                if (itemList == null || itemList.size() == 0) {
                    return CommonResponse.errorException("备货异常");
                }
                for (ElPlanItem item : itemList) {
                    // 设备集合
                    List<SbEquipmentT> sbListT = new ArrayList<SbEquipmentT>();
                    List<SbEquipmentZ> sbListZ = new ArrayList<SbEquipmentZ>();

                    // 获取矿分区信息
                    Long deptId = null;
                    String itemPosition = item.getItemPosition();
                    if (!StringUtils.isEmpty(itemPosition)) {
                        Dept dept = deptService.getByName(itemPosition);
                        deptId = dept.getId();
                    } else {
                        return CommonResponse.errorException("备货异常");
                    }
                    // 获取设备小类
                    String smallType = item.getSmallTypeCode();
                    // 获取设备规格号
                    String sbModelStr = item.getSpecificationCode();
                    // 设备主要参数值
                    String paramValue = item.getEquipmentParamValue();
                    if (StringUtils.isEmpty(paramValue) || "0".equals(paramValue)) {
                        paramValue = null;
                    }
                    // 设备名称
                    String equipmentName = item.getEquipmentName();
                    if (StringUtils.isEmpty(equipmentName)) {
                        equipmentName = null;
                    }
                    if (Constants.PLANEQUIPMENTTYPE_GENERIC.equals(plan.getPlanEquipmentType())) {
                        SbPosition position = new SbPosition();
                        position.setPosition(deptId.toString());
                        List<SbPosition> sbPositions = sbPositionService.list(position, 1, 1000).getList();
                        for (SbPosition sbPosition : sbPositions) {
                            SbEquipmentT sbEquipmentT = new SbEquipmentT();
                            if (!StringUtils.isEmpty(sbPosition.getCode())) {
                                sbEquipmentT.setWare(sbPosition.getCode());
                            }
                            if (!StringUtils.isEmpty(smallType)) {
                                sbEquipmentT.setSbtypeThree(smallType);
                            }
                            if (!StringUtils.isEmpty(sbModelStr)) {
                                sbEquipmentT.setSbmodelCode(sbModelStr);
                            }
                            if (!StringUtils.isEmpty(paramValue)) {
                                sbEquipmentT.setMainPara(paramValue);
                            }
                            if (!StringUtils.isEmpty(equipmentName)) {
                                sbEquipmentT.setName(equipmentName);
                            }
                            List<SbEquipmentT> sbListTI = sbEquipmentTService.list(sbEquipmentT, 1, 1000).getList();
                            if (sbListTI != null && sbListTI.size() > 0) {
                                sbListT.addAll(sbListTI);
                            }
                        }
                        for (SbEquipmentT sbT : sbListT) {
                            ElPlanUse elPlanUse = new ElPlanUse();
                            elPlanUse.setCenterYear(plan.getPlanYear());
                            elPlanUse.setCenterMonth(plan.getPlanMonth());
                            elPlanUse.setPositionId(deptId);
                            elPlanUse.setPlanType(plan.getPlanType());
                            elPlanUse.setPlanId(plan.getPlanId());
                            elPlanUse.setPlanItemId(item.getItemId());
                            elPlanUse.setCreateAt(new Date());
                            elPlanUse.setIsDel((byte) 1);
                            elPlanUse.setVersion(0l);
                            elPlanUse.setEquipmentId(sbT.getId());
                            elPlanUse.setEquipmentType(Constants.PLANEQUIPMENTTYPE_GENERIC);
                            elPlanUse.setStatus("1");
                            elPlanUse.setUpdateAt(new Date());
                            elPlanUse.setCreateBy(0L);
                            elPlanUse.setUpdateBy(0L);
                            elPlanUse.setRemarks("");
                            resT = elPlanUseService.create(elPlanUse) > 0;
                        }
                    }
                    if (Constants.PLANEQUIPMENTTYPE_INTEGRATED.equals(plan.getPlanEquipmentType())) {
                        // TODO 设备管理中心编码暂定
                        SbPosition position = new SbPosition();
                        position.setPosition("8");
                        List<SbPosition> sbPositions = sbPositionService.list(position, 1, 1000).getList();
                        for (SbPosition sbPosition : sbPositions) {
                            SbEquipmentZ sbEquipmentZ = new SbEquipmentZ();
                            if (!StringUtils.isEmpty(sbPosition.getCode())) {
                                sbEquipmentZ.setWare(sbPosition.getCode());
                            }
                            if (!StringUtils.isEmpty(smallType)) {
                                sbEquipmentZ.setSbtypeThree(smallType);
                            }
                            if (!StringUtils.isEmpty(sbModelStr)) {
                                sbEquipmentZ.setSbmodelCode(sbModelStr);
                            }
                            if (!StringUtils.isEmpty(paramValue)) {
                                sbEquipmentZ.setMainPara(paramValue.toString());
                            }
                            if (!StringUtils.isEmpty(equipmentName)) {
                                sbEquipmentZ.setName(equipmentName);
                            }
                            List<SbEquipmentZ> sbListZI = sbEquipmentZService.list(sbEquipmentZ, 1, 1000).getList();
                            if (sbListZI != null && sbListZI.size() > 0) {
                                sbListZ.addAll(sbListZI);
                            }
                        }
                        for (SbEquipmentZ sbZ : sbListZ) {
                            ElPlanUse elPlanUse = new ElPlanUse();
                            elPlanUse.setCenterYear(plan.getPlanYear());
                            elPlanUse.setCenterMonth(plan.getPlanMonth());
                            elPlanUse.setPositionId(deptId);
                            elPlanUse.setPlanType(plan.getPlanType());
                            elPlanUse.setPlanId(plan.getPlanId());
                            elPlanUse.setPlanItemId(item.getItemId());
                            elPlanUse.setCreateAt(new Date());
                            elPlanUse.setUpdateAt(new Date());
                            elPlanUse.setCreateBy(0L);
                            elPlanUse.setUpdateBy(0L);
                            elPlanUse.setRemarks("");
                            elPlanUse.setIsDel((byte) 1);
                            elPlanUse.setVersion(0l);
                            elPlanUse.setEquipmentId(sbZ.getId());
                            elPlanUse.setEquipmentType(Constants.PLANEQUIPMENTTYPE_INTEGRATED);
                            elPlanUse.setStatus("1");
                            resT = elPlanUseService.create(elPlanUse) > 0;
                        }
                    }
                }
            }

            boolean res = elPlanService.update(elPlan) && resT;
            if (!res) {
                return CommonResponse.errorMsg("审批失败");
            }
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("approve elPlan exception: " + JSON.toJSONString(elPlan));
            return CommonResponse.errorException("服务异常");
        }
    }
}
