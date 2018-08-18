package com.yankuang.equipment.equipment.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.mapper.DeptMapper;
import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.common.util.Constants;
import com.yankuang.equipment.equipment.mapper.*;
import com.yankuang.equipment.equipment.model.*;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import com.yankuang.equipment.common.util.UuidUtils;
import com.yankuang.equipment.equipment.service.ElPlanService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import io.terminus.common.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhouy on 2018/7/30.
 */
@Service
@RpcProvider(version = "0.0.1")
@Transactional
public class ElPlanServiceImpl implements ElPlanService {

    public static final Logger logger = Logger.getLogger(ElPlanServiceImpl.class);

    @Autowired
    ElPlanMapper elPlanMapper;
    @Autowired
    ElPlanItemMapper elPlanItemMapper;
    @Autowired
    DeptMapper deptMapper;
    @Autowired
    SbPositionMapper sbPositionMapper;
    @Autowired
    SbTypeMapper sbTypeMapper;
    @Autowired
    SbModelMapper sbModelMapper;
    @Autowired
    SbEquipmentTMapper sbEquipmentTMapper;
    @Autowired
    ElPlanUseMapper elPlanUseMapper;

    public Boolean create (ElPlan elPlan) {
        Boolean res = false;
        try {
            // 验证数据
            if (StringUtils.isEmpty(elPlan.getPlanEquipmentType())
                    || StringUtils.isEmpty(elPlan.getPlanType())) {
                return res;
            }

            elPlan.setPlanId(UuidUtils.newUuid());
            elPlan.setPlanCreateTime(new Date().getTime());
            elPlan.setPlanUpdateTime(elPlan.getPlanCreateTime());
            elPlan.setPlanUpdatorId(elPlan.getPlanCreatorId());
            elPlan.setPlanUpdatorName(elPlan.getPlanCreatorName());
            elPlan.setPlanStatus(Constants.PLANSTATUS_UNCOMMITED);
            elPlan.setPlanCode(UuidUtils.newUuid());
            elPlan.setPlanVersion("1");
            List<ElPlanItem> elPlanItemList = elPlan.getElPlanItemList();
            boolean itemRes = true;
            if (elPlanItemList != null) {
                for (ElPlanItem elPlanItem : elPlanItemList) {
                    elPlanItem.setItemId(UuidUtils.newUuid());
                    elPlanItem.setPlanId(elPlan.getPlanId());
                    itemRes = elPlanItemMapper.saveByPrimaryKey(elPlanItem) > 0;
                    if (!itemRes) {
                        break;
                    }
                }
            }
            res = elPlanMapper.insertByPrimaryKey(elPlan) > 0 && itemRes;
            if (!res) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            logger.info("create ElPlan:"+JSON.toJSONString(elPlan));
            return res;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            logger.info("create ElPlan exception:"+JSON.toJSONString(elPlan));
            return res;
        }
    }

    public ElPlan findElPlanById (String planId) {

        try {
            ElPlan elPlan = elPlanMapper.findById(planId);
            List<ElPlanItem> list = elPlanItemMapper.findByPlanId(planId);
            if (list != null) {
                elPlan.setElPlanItemList(list);
            }
            logger.info("findElPlanById: " + JSON.toJSONString(elPlan));
            return elPlan;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("planId: "+planId+";findElPlanById exception");
            return null;
        }

    }

    public Boolean update (ElPlan elPlan) {

        Boolean res = false;
        try {
            String planId = elPlan.getPlanId();
            if (StringUtils.isEmpty(planId)) {
                return res;
            }
            elPlanMapper.deletePlanItemByPlanId(planId);
            elPlan.setPlanUpdateTime(new Date().getTime());
            boolean itemRes = true;
            if (elPlan.getElPlanItemList() != null) {
                for (ElPlanItem elPlanItem : elPlan.getElPlanItemList()) {
                    elPlanItem.setItemId(UuidUtils.newUuid());
                    elPlanItem.setPlanId(elPlan.getPlanId());
                    itemRes = elPlanItemMapper.saveByPrimaryKey(elPlanItem) > 0;
                    if (!itemRes) {
                        break;
                    }
                }
            }
            //elPlanMapper.savePlanItemByPlanId(elPlan);
            logger.info("update elPlan: "+JSON.toJSONString(elPlan));
            res = elPlanMapper.updateByPrimarykey(elPlan) > 0 && itemRes;
            if (!res) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return res;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            logger.info("update elPlan exception: "+JSON.toJSONString(elPlan));
            return res;
        }
    }

    public Boolean deletePlan(String planId) {

        try {
            int res = elPlanMapper.deletePlanByPlanId(planId);
            if (res <= 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
            logger.info("delete elPlan byID: "+planId);
            return true;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            logger.info("delete elPlan exception: "+planId);
            return false;
        }
    }

    public Paging findElPlanByCondition(ElPlan elPlan, int pageSize, int pageNum) {

        try {
            PageHelper.startPage(pageNum, pageSize);
            List<ElPlan> lists = elPlanMapper.listByCondition(elPlan);
            PageInfo<ElPlan> pageInfo = new PageInfo<ElPlan>(lists);
            Paging page = new Paging();
            if (pageInfo.getList() != null) {
                page.setData(pageInfo.getList());
                page.setTotal(pageInfo.getTotal());
                for (ElPlan plan : pageInfo.getList()) {
                    //List<ElPlanItem> list = elPlanItemMapper.findByPlanId(plan.getPlanId());
                    String str = Constants.PLANSTATUS_UNCOMMITED.equals(plan.getPlanStatus()) ? Constants.PLANSTATUS_UNCOMMITED_VALUE
                            : Constants.PLANSTATUS_COMMITED.equals(plan.getPlanStatus()) ? Constants.PLANSTATUS_COMMITED_VALUE
                            : Constants.PLANSTATUS_FAILED.equals(plan.getPlanStatus()) ? Constants.PLANSTATUS_FAILED_VALUE
                            : Constants.PLANSTATUS_PASSED.equals(plan.getPlanStatus()) ? Constants.PLANSTATUS_PASSED_VALUE
                            : Constants.PLANSTATUS_OTHERS_VALUE;
                    plan.setPlanStatus(str);
                    String planType = Constants.PLANTYPE_YEAR.equals(plan.getPlanType()) ? "年度计划"
                            : Constants.PLANTYPE_QUARTER.equals(plan.getPlanType()) ? "季度计划"
                            : Constants.PLANTYPE_MONTH.equals(plan.getPlanType()) ? "月度计划"
                            : Constants.PLANTYPE_URGENT.equals(plan.getPlanType()) ? "应急计划" : "服务维护中";
                    plan.setPlanType(planType);
                    String equipmentType = Constants.PLANEQUIPMENTTYPE_GENERIC.equals(plan.getPlanEquipmentType()) ? "通用设备"
                            : Constants.PLANEQUIPMENTTYPE_INTEGRATED.equals(plan.getPlanEquipmentType()) ? "综机设备" : "其他设备";
                    plan.setPlanEquipmentType(equipmentType);
                    plan.setElPlanItemList(null);
                }
            }

            logger.info("pageSize: "+pageSize+"; pageNum: "+pageNum+"; findElPlanByCondition: "+JSON.toJSONString(elPlan));
            logger.info("findElPlanByCondition result: "+JSON.toJSONString(page));
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("exception: pageSize: "+pageSize+"; pageNum: "+pageNum+"; findElPlanByCondition: "+JSON.toJSONString(elPlan));
            return null;
        }
    }

    public boolean approve(ElPlan elPlan) {

        try {
            logger.info("approve elPlan: "+JSON.toJSONString(elPlan));

            // 租赁计划审核通过，仓库开始备货
            Boolean resT = false;
            if (Constants.PLANSTATUS_PASSED.equals(elPlan.getPlanStatus())
                    && !StringUtils.isEmpty(elPlan.getPlanId())) {
                ElPlan plan = elPlanMapper.findById(elPlan.getPlanId());
                List<ElPlanItem> itemList = plan.getElPlanItemList();
                if (itemList == null || itemList.size() == 0) {
                    // TODO
                }
                for (ElPlanItem item : itemList) {
                    // 设备集合
                    List<SbEquipmentT> sbList = new ArrayList<SbEquipmentT>();

                    // 获取矿分区信息
                    Long deptId = null;
                    String itemPosition = item.getItemPosition();
                    if (!StringUtils.isEmpty(itemPosition)) {
                        Dept dept = deptMapper.findByName(itemPosition);
                        deptId = dept.getId();
                    } else {
                        // TODO 备货异常
                    }
                    // 获取设备小类
                    String smallType = item.getEquipmentSmallType();
                    Long sbTypeId =null;
                    if (!StringUtils.isEmpty(smallType)) {
                        SbType sbType = sbTypeMapper.findByName(smallType);
                        sbTypeId = sbType.getId();
                    } else {
                        // TODO 备货异常
                    }
                    // 获取设备规格号
                    String sbModelStr = item.getEquipmentSpecification();
                    Long sbModelId = null;
                    if (!StringUtils.isEmpty(sbModelStr)) {
                        SbModel sbModel = sbModelMapper.findByName(sbModelStr);
                        sbModelId = sbModel.getId();
                    }
                    // 设备主要参数值
                    Integer paramValue = item.getEquipmentParamValue();
                    if (paramValue == null || paramValue == 0) {
                        paramValue = null;
                    }
                    // 设备名称
                    String equipmentName = item.getEquipmentName();
                    if (StringUtils.isEmpty(equipmentName)) {
                        equipmentName = null;
                    }
                    List<SbPosition> sbPositions = sbPositionMapper.findByPosition(deptId.toString());
                    for (SbPosition sbPosition : sbPositions) {
                        SbEquipmentT sbEquipmentT = new SbEquipmentT();
                        if (!StringUtils.isEmpty(sbPosition.getCode())) {
                            sbEquipmentT.setWare(sbPosition.getCode());
                        }
                        if (!StringUtils.isEmpty(sbTypeId)) {
                            sbEquipmentT.setSbtypeThree(sbTypeId.toString());
                        }
                        if (!StringUtils.isEmpty(sbModelId)) {
                            sbEquipmentT.setSbmodelName(sbModelId.toString());
                        }
                        if (!StringUtils.isEmpty(paramValue)) {
                            // TODO
                            // sbEquipmentT.setMainPara(paramValue);
                        }
                        if (!StringUtils.isEmpty(equipmentName)) {
                            sbEquipmentT.setName(equipmentName);
                        }
                        List<SbEquipmentT> sbListT = sbEquipmentTMapper.listByCondition(sbEquipmentT);
                        if (sbListT != null && sbListT.size() > 0) {
                            sbList.addAll(sbListT);
                        }
                    }
                    for (SbEquipmentT sbT : sbList) {
                        ElPlanUse elPlanUse = new ElPlanUse();
                        elPlanUse.setCenterYear(plan.getPlanYear());
                        elPlanUse.setCenterMonth(plan.getPlanMonth());
                        elPlanUse.setPositionId(deptId);
                        elPlanUse.setPlanType(plan.getPlanType());
                        elPlanUse.setPlanId(plan.getPlanId());
                        elPlanUse.setPlanItemId(item.getItemId());
                        elPlanUse.setCreateAt(new Date());
                        elPlanUse.setIsDel((byte)1);
                        elPlanUse.setVersion(0l);
                        elPlanUse.setEquipmentId(sbT.getId());
                        resT = elPlanUseMapper.insert(elPlanUse) > 0;
                    }
                }
            }

            boolean res = elPlanMapper.updateByPrimarykey(elPlan) > 0 && resT;
            if (!res) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return res;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            logger.info("approve elPlan exception: "+JSON.toJSONString(elPlan));
            return false;
        }
    }

    public List<ElPlanUse> findElPlanUse(ElPlanUse elPlanUse) {

        return elPlanUseMapper.findByCondition(elPlanUse);
    }
}
