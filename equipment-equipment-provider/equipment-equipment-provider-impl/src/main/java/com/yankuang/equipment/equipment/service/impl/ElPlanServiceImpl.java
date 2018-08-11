package com.yankuang.equipment.equipment.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.common.util.Constants;
import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;
import com.yankuang.equipment.common.util.UuidUtils;
import com.yankuang.equipment.equipment.mapper.ElPlanItemMapper;
import com.yankuang.equipment.equipment.mapper.ElPlanMapper;
import com.yankuang.equipment.equipment.model.ElPlan;
import com.yankuang.equipment.equipment.model.ElPlanItem;
import com.yankuang.equipment.equipment.service.ElPlanService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import io.terminus.common.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ElPlanMapper elPlanMapper;
    @Autowired
    private ElPlanItemMapper elPlanItemMapper;

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
            elPlan.setPlanVersion(UuidUtils.newUuid());
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
            if (elPlan.getElPlanItemList() != null) {
                for (ElPlanItem elPlanItem : elPlan.getElPlanItemList()) {
                    elPlanItem.setItemId(UuidUtils.newUuid());
                    elPlanItem.setPlanId(elPlan.getPlanId());
                }
            }
            elPlanMapper.savePlanItemByPlanId(elPlan);
            logger.info("update elPlan: "+JSON.toJSONString(elPlan));
            res = elPlanMapper.updateByPrimarykey(elPlan) > 0;
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
            boolean res = elPlanMapper.updateByPrimarykey(elPlan) > 0;
            return res;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            logger.info("approve elPlan exception: "+JSON.toJSONString(elPlan));
            return false;
        }
    }
}
