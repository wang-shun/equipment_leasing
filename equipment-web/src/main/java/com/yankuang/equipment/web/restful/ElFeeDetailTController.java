package com.yankuang.equipment.web.restful;

import com.alibaba.fastjson.JSON;
import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.authority.service.DeptService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.FeeUtils;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.equipment.model.ElFeeDetailT;
import com.yankuang.equipment.equipment.model.ElPlanUse;
import com.yankuang.equipment.equipment.model.ElUseItem;
import com.yankuang.equipment.equipment.service.ElFeeDetailTService;
import com.yankuang.equipment.equipment.service.ElPlanUseService;
import com.yankuang.equipment.equipment.service.ElUseItemService;
import com.yankuang.equipment.equipment.service.SbElFeeService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/elFeeDetailT")
public class ElFeeDetailTController {

    @RpcConsumer
    ElFeeDetailTService elFeeDetailTService;
    @RpcConsumer
    DeptService deptService;
    @RpcConsumer
    ElUseItemService elUseItemService;
    @RpcConsumer
    SbElFeeService sbElFeeService;
    @RpcConsumer
    ElPlanUseService elPlanUseService;

    /**
     * 查询导出数据
     * @param detailTs
     * @return
     */
    @GetMapping
    public CommonResponse findElFeeDetailTs(ElFeeDetailT detailTs) {

        try {
            if (detailTs == null || StringUtils.isEmpty(detailTs.getPositionCode())) {
                return CommonResponse.errorMsg("请补充矿处单位");
            }
            if (detailTs == null || StringUtils.isEmpty(detailTs.getExportAtStr())) {
                return CommonResponse.errorMsg("请补充导出时间");
            }
            String reportYear = detailTs.getExportAtStr().split("-")[0];
            String reportMonth = detailTs.getExportAtStr().split("-")[1];
            detailTs.setReportYear(reportYear);
            detailTs.setReportMonth(reportMonth);
            List<ElFeeDetailT> historyList = elFeeDetailTService.findElFeeDetailTs(detailTs, detailTs.getPageNum(), detailTs.getPageSize());
            if (historyList != null && historyList.size() > 0) {
                return CommonResponse.ok(historyList);
            }

            Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(reportYear+"-"+(Integer.valueOf(reportMonth)-1)+"-"+21);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(reportYear+"-"+(Integer.valueOf(reportMonth))+"-"+20);
            HashMap<String, String> map = new HashMap<>();
            map.put("pcode", detailTs.getPositionCode());
            List<Dept> depts = deptService.findByPage(1, 1000, map).getList();
            List<ElUseItem> elUseItemList = new ArrayList<>();
            for (Dept d : depts) {
                if (d == null || d.getId() == null) {
                    continue;
                }
                ElUseItem elUseItem = new ElUseItem();
                elUseItem.setEquipmentPosition(d.getId());
                List<ElUseItem> useItems = elUseItemService.findElUseItemTL(elUseItem);
                if (useItems != null && useItems.size() > 0) {
                    elUseItemList.addAll(useItems);
                }
            }
            List<ElFeeDetailT> elFeeDetailTS = new ArrayList<>();
            Date now = new Date();
            for (ElUseItem uItem : elUseItemList) {
                Long days = 0L;
                if (uItem == null) {
                    continue;
                }
                if (uItem.getSign() == null) {
                    days = sbElFeeService.CalEquipmentElDays(uItem.getUseId(), null, uItem.getEquipmentId(), startDate, endDate);
                } else {
                    Long uItemTId = uItem.getSign();
                    ElUseItem uItemT = elUseItemService.findById(uItemTId);
                    if (uItemT != null && uItemT.getUseAt().after(startDate)) {
                        days = sbElFeeService.CalEquipmentElDays(uItem.getUseId(), uItemT.getUseId(), uItem.getEquipmentId(), startDate, endDate);
                    }
                }
                ElFeeDetailT detailT = new ElFeeDetailT();
                Dept de = deptService.findByCode(detailTs.getPositionCode());
                if (de == null) {
                    continue;
                }
                detailT.setPositionName(de.getName());
                detailT.setPositionCode(detailTs.getPositionCode());
                ElPlanUse planUse = elPlanUseService.findById(uItem.getPlanUseId());
                detailT.setMiddleTypeName(planUse.getMiddleTypeName());
                detailT.setMiddleTypeCode(planUse.getMiddleTypeCode());
                detailT.setSmallTypeCode(planUse.getSmallTypeCode());
                detailT.setSmallTypeName(planUse.getSmallTypeName());
                detailT.setEquipmentCode(planUse.getEquipmentCode());
                detailT.setTechCode(planUse.getTechCode());
                detailT.setModelName(planUse.getEquipmentSpecification());
                detailT.setModelCode(planUse.getEquipmentModel());
                detailT.setEffectName(planUse.getEffectName());
                detailT.setEffectCode(planUse.getEffectCode());
                detailT.setElDays(days);
                detailT.setCostA1(uItem.getCostA1());
                Double a3Rate = sbElFeeService.CalDayElFeeA3T_rate(uItem.getUseId(), uItem.getEquipmentId());
                double a3s = (a3Rate-1)*uItem.getCostA1();
                detailT.setCostA3(FeeUtils.scale(a3s, 3));
                double totalFees = uItem.getCostA1()*days+(a3Rate-1)*uItem.getCostA1();
                detailT.setTotalFee(FeeUtils.scale(totalFees, 3));
                detailT.setReportYear(reportYear);
                detailT.setReportMonth(reportMonth);
                //detailT.setExportAt(endDate);
                detailT.setExportAt(now);
                detailT.setEquipmentId(planUse.getEquipmentId());
                detailT.setPageNum(detailTs.getPageNum());
                detailT.setPageSize(detailTs.getPageSize());
                detailT.setStatus((byte)1);
                elFeeDetailTS.add(detailT);
            }

            if (elFeeDetailTS == null || elFeeDetailTS.size() <= 0) {
                return CommonResponse.build(200, "查询结果为空", null);
            }

            return CommonResponse.ok(elFeeDetailTS);
        } catch (ParseException e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常"+ JSON.toJSONString(e));
        }
    }

    /**
     * 保存通用设备租赁计费明细
     * @param jsonString
     * @return
     */
    @PostMapping
    public CommonResponse create(@RequestBody String jsonString) {
        try {
            if (StringUtils.isEmpty(jsonString)) {
                return CommonResponse.errorMsg("参数不得为空");
            }
            List<ElFeeDetailT> list = JsonUtils.jsonToList(jsonString, ElFeeDetailT.class);
            if (list == null && list.size() == 0) {
                return CommonResponse.errorMsg("参数不得为空");
            }

            boolean res = elFeeDetailTService.createBatch(list);
            if (!res) {
                return CommonResponse.errorMsg("添加失败");
            }
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常"+JSON.toJSONString(e));
        }
    }

}
