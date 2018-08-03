package com.yankuang.equipment.leasing.serviceImpl;

import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.common.util.UuidUtils;
import com.yankuang.equipment.leasing.model.ElPlanItem;
import io.terminus.boot.rpc.common.annotation.RpcProvider;
import io.terminus.common.model.Paging;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.yankuang.equipment.common.base.BaseService;
import com.yankuang.equipment.leasing.mapper.ElPlanMapper;
import com.yankuang.equipment.leasing.model.ElPlan;
import com.yankuang.equipment.leasing.service.ElPlanService;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zhouy on 2018/7/30.
 */
@Service
@RpcProvider(version = "0.0.1")
public class ElPlanServiceImpl extends BaseService implements ElPlanService {

    @Autowired
    private ElPlanMapper elPlanMapper;

    public Boolean create (ElPlan elPlan) {
        Boolean res = false;
        try {
            if (StringUtils.isEmpty(elPlan.getPlanEquipmentType()) || StringUtils.isEmpty(elPlan.getPlanType())) {
                return res;
            }
            elPlan.setPlanId(UuidUtils.newUuid());
            elPlan.setPlanCreateTime(new Date().getTime());
            elPlan.setPlanUpdateTime(elPlan.getPlanCreateTime());
            elPlan.setPlanStatus("1");
            elPlan.setPlanCode(UuidUtils.newUuid());
            elPlan.setPlanUpdatorId(elPlan.getPlanCreatorId());
            elPlan.setPlanVersion(UuidUtils.newUuid());
            for (ElPlanItem elPlanItem : elPlan.getElPlanItemList()) {
                elPlanItem.setItemId(UuidUtils.newUuid());
            }
            res = elPlanMapper.create(elPlan);
            logger.debug("create ElPlan:"+JSON.toJSONString(elPlan));
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("create ElPlan exception:"+JSON.toJSONString(elPlan));
            rollback();
            return res;
        }
    }

    public ElPlan findElPlanById (String planId) {

        try {
            ElPlan elPlan = elPlanMapper.findById(planId);
            logger.debug("findElPlanById: " + JSON.toJSONString(elPlan));
            return elPlan;
        } catch (Exception e) {
            e.printStackTrace();
            logger.debug("planId: "+planId+";findElPlanById exception");
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
            elPlanMapper.savePlanItemByPlanId(elPlan);
            logger.debug("update elPlan: "+JSON.toJSONString(elPlan));
            res = elPlanMapper.update(elPlan);
            return res;
        } catch (Exception e) {
            rollback();
            e.printStackTrace();
            logger.debug("update elPlan exception: "+JSON.toJSONString(elPlan));
            return res;
        }

    }

    public Boolean deletePlan(String planId) {

        try {
            int res = elPlanMapper.deletePlanByPlanId(planId);
            if (res <= 0) {
                return false;
            }
            //logger.debug("delete elPlan byID: "+planId);
            return true;
        } catch (Exception e) {
            //rollback();
            e.printStackTrace();
            //logger.debug("delete elPlan exception: "+planId);
            return false;
        }
    }

    public Paging findElPlanByCondition(ElPlan elPlan, int pageSize, int pageNum) {

        try {
            int maxResult = (pageNum - 1) * pageSize;
            Paging page = elPlanMapper.paging(maxResult, pageNum, elPlan);
            //logger.debug("pageSize: "+pageSize+"pageNum: "+pageNum+";findElPlanByCondition: "+JSON.toJSONString(elPlan));
            return page;
        } catch (Exception e) {
            e.printStackTrace();
            //logger.debug("exception: pageSize: "+pageSize+"pageNum: "+pageNum+";findElPlanByCondition: "+JSON.toJSONString(elPlan));
            return null;
        }
    }

    public boolean approve(ElPlan elPlan) {
        return elPlanMapper.update(elPlan);
    }
}
