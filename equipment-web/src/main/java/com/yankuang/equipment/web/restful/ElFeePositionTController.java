package com.yankuang.equipment.web.restful;

import com.alibaba.fastjson.JSON;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.equipment.model.ElFeePositionT;
import com.yankuang.equipment.equipment.service.ElFeePositionTService;
import com.yankuang.equipment.web.dto.UserDTO;
import com.yankuang.equipment.web.service.ReportTPlusService;
import com.yankuang.equipment.web.util.UserFromRedis;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/elFeePositionT")
public class ElFeePositionTController {

    @RpcConsumer
    ElFeePositionTService elFeePositionTService;
    @Autowired
    ReportTPlusService reportTPlusService;
    @Autowired
    UserFromRedis userFromRedis;

    @GetMapping
    public CommonResponse findList(ElFeePositionT elFeePositionT) {

        try {
            // 查询历史记录
            if (elFeePositionT == null || StringUtils.isEmpty(elFeePositionT.getExportAtStr())) {
                return CommonResponse.errorMsg("导出时间不得为空");
            }
            String reportYear = elFeePositionT.getExportAtStr().split("-")[0];
            String reportMonth = elFeePositionT.getExportAtStr().split("-")[1];
            if (StringUtils.isEmpty(reportYear)) {
                return CommonResponse.errorMsg("年度不得为空");
            }
            if (StringUtils.isEmpty(reportMonth)) {
                return CommonResponse.errorMsg("月度不得为空");
            }
            if (StringUtils.isEmpty(elFeePositionT.getPoStatus())) {
                return CommonResponse.errorMsg("矿状态不得为空");
            }
            List<ElFeePositionT> historyList = reportTPlusService.getElFeePositionTSHistory(elFeePositionT, reportYear, reportMonth);
            if (historyList != null || historyList.size() > 0) {
                return CommonResponse.ok(historyList);
            }

            // 联表查询
            List<ElFeePositionT> positionTs = reportTPlusService.getElFeePositionTS(elFeePositionT, reportYear, reportMonth);

            if (positionTs == null || positionTs.size() <= 0) {
                return CommonResponse.build(200, "查询结果为空", null);
            }

            return CommonResponse.ok(positionTs);
        } catch (Exception e) {
            e.printStackTrace();
            return CommonResponse.errorException("服务异常："+JSON.toJSONString(e));
        }
    }

    @GetMapping(value = "/refresh")
    public CommonResponse refresh(ElFeePositionT elFeePositionT) {
        try {
            if (elFeePositionT == null || StringUtils.isEmpty(elFeePositionT.getExportAtStr())) {
                return CommonResponse.errorMsg("导出时间不得为空");
            }
            String reportYear = elFeePositionT.getExportAtStr().split("-")[0];
            String reportMonth = elFeePositionT.getExportAtStr().split("-")[1];
            if (StringUtils.isEmpty(reportYear)) {
                return CommonResponse.errorMsg("年度不得为空");
            }
            if (StringUtils.isEmpty(reportMonth)) {
                return CommonResponse.errorMsg("月度不得为空");
            }
            if (StringUtils.isEmpty(elFeePositionT.getPoStatus())) {
                return CommonResponse.errorMsg("矿状态不得为空");
            }
            // 联表查询
            List<ElFeePositionT> positionTs = reportTPlusService.getElFeePositionTS(elFeePositionT, reportYear, reportMonth);
            if (positionTs != null && positionTs.size() > 0) return CommonResponse.ok(positionTs);
            List<ElFeePositionT> historyList = reportTPlusService.getElFeePositionTSHistory(elFeePositionT, reportYear, reportMonth);
            if (historyList == null || historyList.size() <= 0) return CommonResponse.build(200,"查询结果为空",null);
            return CommonResponse.ok(historyList);
        } catch (Exception e) {
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
            List<ElFeePositionT> list = JsonUtils.jsonToList(jsonString, ElFeePositionT.class);
            if (list == null || list.size() <= 0) {
                return CommonResponse.errorMsg("参数不得为空");
            }
            UserDTO userDTO = userFromRedis.findByToken();
            if (userDTO != null) {
                for (ElFeePositionT feePositionT : list) {
                    feePositionT.setCreateBy(userDTO.getId());
                }
            }

            boolean res = elFeePositionTService.createBatch(list);
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
