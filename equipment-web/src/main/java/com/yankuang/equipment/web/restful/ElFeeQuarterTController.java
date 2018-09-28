package com.yankuang.equipment.web.restful;

import com.alibaba.fastjson.JSON;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.equipment.model.ElFeeQuarterT;
import com.yankuang.equipment.equipment.service.ElFeeQuarterTService;
import com.yankuang.equipment.web.service.ReportTPlusService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/elFeeQuarterT")
public class ElFeeQuarterTController {

    @RpcConsumer
    ElFeeQuarterTService elFeeQuarterTService;
    @Autowired
    ReportTPlusService reportTPlusService;

    @GetMapping
    public CommonResponse findList(ElFeeQuarterT elFeeQuarterT){

        try {
            // 查询历史记录
            if (elFeeQuarterT == null || StringUtils.isEmpty(elFeeQuarterT.getPositionCode())) {
                return CommonResponse.errorMsg("矿处单位不得为空");
            }
            if (elFeeQuarterT == null || StringUtils.isEmpty(elFeeQuarterT.getReportYear())) {
                return CommonResponse.errorMsg("报表年度不得为空");
            }
            if (elFeeQuarterT == null || StringUtils.isEmpty(elFeeQuarterT.getReportQuarter())) {
                return CommonResponse.errorMsg("报表季度不得为空");
            }
            String reportYear = elFeeQuarterT.getReportYear();
            String reportQuarter = elFeeQuarterT.getReportQuarter();
            List<ElFeeQuarterT> historyList = elFeeQuarterTService.findList(elFeeQuarterT);
            if (historyList != null && historyList.size() > 0) {
                return CommonResponse.ok(historyList);
            }

            // 联表查询
            List<ElFeeQuarterT> elFeeQuarterTiS = reportTPlusService.getElFeeQuarterTS(elFeeQuarterT.getPositionCode(), reportYear, reportQuarter);

            if (elFeeQuarterTiS == null || elFeeQuarterTiS.size() <= 0) {
                return CommonResponse.build(200, "查询结果为空", null);
            }
            return CommonResponse.ok(elFeeQuarterTiS);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常:"+JSON.toJSONString(e));
        }
    }

    @GetMapping(value = "/refresh")
    public CommonResponse refresh(ElFeeQuarterT elFeeQuarterT) {
        try {
            if (elFeeQuarterT == null || StringUtils.isEmpty(elFeeQuarterT.getPositionCode())) {
                return CommonResponse.errorMsg("矿处单位不得为空");
            }
            if (elFeeQuarterT == null || StringUtils.isEmpty(elFeeQuarterT.getReportYear())) {
                return CommonResponse.errorMsg("报表年度不得为空");
            }
            if (elFeeQuarterT == null || StringUtils.isEmpty(elFeeQuarterT.getReportQuarter())) {
                return CommonResponse.errorMsg("报表季度不得为空");
            }
            String reportYear = elFeeQuarterT.getReportYear();
            String reportQuarter = elFeeQuarterT.getReportQuarter();
            List<ElFeeQuarterT> elFeeQuarterTiS = reportTPlusService.getElFeeQuarterTS(elFeeQuarterT.getPositionCode(), reportYear, reportQuarter);
            if (elFeeQuarterTiS != null && elFeeQuarterTiS.size() > 0) return CommonResponse.ok(elFeeQuarterTiS);
            List<ElFeeQuarterT> historyList = elFeeQuarterTService.findList(elFeeQuarterT);
            if (historyList == null || historyList.size() <= 0) return CommonResponse.build(200, "查询结果为空", null);
            return CommonResponse.ok(historyList);
        } catch (ParseException e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

    @PostMapping
    public CommonResponse createBatch(@RequestBody String jsonString) {

        try {
            if (org.springframework.util.StringUtils.isEmpty(jsonString)) {
                return CommonResponse.errorMsg("参数不得为空");
            }
            List<ElFeeQuarterT> list = JsonUtils.jsonToList(jsonString, ElFeeQuarterT.class);
            if (list == null || list.size() <= 0) {
                return CommonResponse.errorMsg("参数不得为空");
            }

            boolean res = elFeeQuarterTService.createBatch(list);
            if (!res) {
                return CommonResponse.errorMsg("创建失败");
            }
            return CommonResponse.ok();
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }

    }

}
