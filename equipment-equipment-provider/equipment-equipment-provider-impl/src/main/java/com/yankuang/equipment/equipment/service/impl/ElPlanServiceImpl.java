package com.yankuang.equipment.equipment.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.Constants;
import com.yankuang.equipment.common.util.UuidUtils;
import com.yankuang.equipment.equipment.mapper.*;
import com.yankuang.equipment.equipment.model.*;
import com.yankuang.equipment.equipment.service.ElPlanService;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import io.terminus.common.model.Paging;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by zhouy on 2018/7/30.
 */
@Service
@RpcProvider
@Transactional
public class ElPlanServiceImpl implements ElPlanService {

    public static final Logger logger = Logger.getLogger(ElPlanServiceImpl.class);

    @Autowired
    ElPlanMapper elPlanMapper;
    @Autowired
    ElPlanItemMapper elPlanItemMapper;
    @Autowired
    ElPlanUseMapper elPlanUseMapper;

    public ElPlanItem findEPlanItemByItemId(String itemId) {
        try {
            ElPlanItem elPlanItem = elPlanItemMapper.findByItemId(itemId);
            return elPlanItem;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean create(ElPlan elPlan) {
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
                    elPlanItem.setStatus("1");
                    itemRes = elPlanItemMapper.insert(elPlanItem) > 0;
                    if (!itemRes) {
                        break;
                    }
                }
            }
            res = elPlanMapper.insert(elPlan) > 0 && itemRes;
            if (!res) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            logger.info("create ElPlan:" + JSON.toJSONString(elPlan));
            return res;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            logger.info("create ElPlan exception:" + JSON.toJSONString(elPlan));
            return res;
        }
    }

    public ElPlan findElPlanById(String planId) {

        try {
            ElPlan elPlan = elPlanMapper.findById(planId);
            List<ElPlanItem> list = elPlanItemMapper.findByPlanId(planId);
            if (list != null && elPlan != null) {
                elPlan.setElPlanItemList(list);
            }
            logger.info("findElPlanById: " + JSON.toJSONString(elPlan));
            return elPlan;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("planId: " + planId + ";findElPlanById exception");
            return null;
        }

    }

    public Boolean update(ElPlan elPlan) {

        Boolean res = false;
        try {
            String planId = elPlan.getPlanId();
            if (StringUtils.isEmpty(planId)) {
                return res;
            }
            elPlan.setPlanUpdateTime(new Date().getTime());
            boolean itemRes = true;
            if (elPlan.getElPlanItemList() != null) {
                for (ElPlanItem elPlanItem : elPlan.getElPlanItemList()) {
                    String itemId = elPlanItem.getItemId();
                    if (StringUtils.isEmpty(itemId)) {
                        elPlanItem.setItemId(UuidUtils.newUuid());
                        elPlanItem.setPlanId(elPlan.getPlanId());
                        elPlanItem.setStatus("1");
                        itemRes = elPlanItemMapper.insert(elPlanItem) > 0;
                    } else {
                        itemRes = elPlanItemMapper.update(elPlanItem) >= 0;
                    }
                    if (!itemRes) {
                        break;
                    }
                }
            }
            //elPlanMapper.savePlanItemByPlanId(elPlan);
            logger.info("update elPlan: " + JSON.toJSONString(elPlan));
            res = elPlanMapper.update(elPlan) > 0 && itemRes;
            if (!res) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return res;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            logger.info("update elPlan exception: " + JSON.toJSONString(elPlan));
            return res;
        }
    }

    public Boolean deletePlan(String planId) {

        try {
            int res = elPlanMapper.delete(planId);
            if (res <= 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return false;
            }
            logger.info("delete elPlan byID: " + planId);
            return true;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            logger.info("delete elPlan exception: " + planId);
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
//                    List<ElPlanItem> list = elPlanItemMapper.findByPlanId(plan.getPlanId());
//                    String str = Constants.PLANSTATUS_UNCOMMITED.equals(plan.getPlanStatus()) ? Constants.PLANSTATUS_UNCOMMITED_VALUE
//                            : Constants.PLANSTATUS_COMMITED.equals(plan.getPlanStatus()) ? Constants.PLANSTATUS_COMMITED_VALUE
//                            : Constants.PLANSTATUS_FAILED.equals(plan.getPlanStatus()) ? Constants.PLANSTATUS_FAILED_VALUE
//                            : Constants.PLANSTATUS_PASSED.equals(plan.getPlanStatus()) ? Constants.PLANSTATUS_PASSED_VALUE
//                            : Constants.PLANSTATUS_OTHERS_VALUE;
//                    plan.setPlanStatus(str);
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

            logger.info("pageSize: " + pageSize + "; pageNum: " + pageNum + "; findElPlanByCondition: " + JSON.toJSONString(elPlan));
            logger.info("findElPlanByCondition result: " + JSON.toJSONString(page));
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("exception: pageSize: " + pageSize + "; pageNum: " + pageNum + "; findElPlanByCondition: " + JSON.toJSONString(elPlan));
            return null;
        }
    }

    public CommonResponse approve(ElPlan elPlan) {

        try {
            logger.info("approve elPlan: " + JSON.toJSONString(elPlan));

            boolean res = elPlanMapper.update(elPlan) > 0;
            if (!res) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            }
            return CommonResponse.ok();
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
            logger.info("approve elPlan exception: " + JSON.toJSONString(elPlan));
            return CommonResponse.errorException("服务异常");
        }
    }

}
