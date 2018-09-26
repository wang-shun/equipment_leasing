package com.yankuang.equipment.web.restful;

import com.alibaba.fastjson.JSON;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.FeeUtils;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.equipment.model.ElFeeMiddleT;
import com.yankuang.equipment.equipment.service.ElFeeMiddleTService;
import com.yankuang.equipment.web.service.ReportTPlusService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/elFeeMiddleT")
public class ElFeeMiddleTController {

    @RpcConsumer
    ElFeeMiddleTService elFeeMiddleTService;
    @Autowired
    ReportTPlusService reportTPlusService;

    @GetMapping
    public CommonResponse findElFeeMiddleTs(ElFeeMiddleT elFeeMiddleT) {

        try {
            if (elFeeMiddleT == null || StringUtils.isEmpty(elFeeMiddleT.getPositionCode())) {
                return CommonResponse.errorMsg("请补充矿处单位");
            }
            if (elFeeMiddleT == null || StringUtils.isEmpty(elFeeMiddleT.getExportAtStr())) {
                return CommonResponse.errorMsg("请补充导出时间");
            }
            String reportYear = elFeeMiddleT.getExportAtStr().split("-")[0];
            String reportMonth = elFeeMiddleT.getExportAtStr().split("-")[1];
            List<ElFeeMiddleT> historyList = reportTPlusService.getElFeeMiddleTSHistory(elFeeMiddleT, reportYear, reportMonth);
            if (historyList != null && historyList.size() > 0) return CommonResponse.ok(historyList);
            // 联表查询
            List<ElFeeMiddleT> middleTs = reportTPlusService.getElFeeMiddleTS(elFeeMiddleT.getPositionCode(), reportYear, reportMonth);

            if (middleTs == null || middleTs.size() <= 0) {
                return CommonResponse.build(200, "查询结果为空", null);
            }

            return CommonResponse.ok(middleTs);
        } catch (ParseException e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+ JSON.toJSONString(e));
        }
    }

    @GetMapping(value = "/refresh")
    public CommonResponse refresh(ElFeeMiddleT elFeeMiddleT) {
        try {
            if (elFeeMiddleT == null || StringUtils.isEmpty(elFeeMiddleT.getPositionCode())) {
                return CommonResponse.errorMsg("请补充矿处单位");
            }
            if (elFeeMiddleT == null || StringUtils.isEmpty(elFeeMiddleT.getExportAtStr())) {
                return CommonResponse.errorMsg("请补充导出时间");
            }
            String reportYear = elFeeMiddleT.getExportAtStr().split("-")[0];
            String reportMonth = elFeeMiddleT.getExportAtStr().split("-")[1];
            // 联表查询
            List<ElFeeMiddleT> middleTs = reportTPlusService.getElFeeMiddleTS(elFeeMiddleT.getPositionCode(), reportYear, reportMonth);
            if (middleTs != null && middleTs.size() > 0) return CommonResponse.ok(middleTs);

            List<ElFeeMiddleT> middleTsHistory = reportTPlusService.getElFeeMiddleTSHistory(elFeeMiddleT, reportYear, reportMonth);
            if (middleTsHistory == null || middleTsHistory.size() <= 0) {
                return CommonResponse.build(200, "查询结果为空", null);
            }
            return CommonResponse.ok(middleTsHistory);
        } catch (ParseException e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常: "+JSON.toJSONString(e));
        }

    }

    @PostMapping
    public CommonResponse createBatch(@RequestBody String jsonString) {
        try {
            if (StringUtils.isEmpty(jsonString)) {
                return CommonResponse.errorMsg("参数不得为空");
            }
            List<ElFeeMiddleT> list = JsonUtils.jsonToList(jsonString, ElFeeMiddleT.class);
            if (list == null || list.size() <= 0) {
                return CommonResponse.errorMsg("参数不得为空");
            }

            boolean res = elFeeMiddleTService.createBatch(list);
            if (!res) {
                return CommonResponse.errorMsg("添加失败");
            }
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

}
