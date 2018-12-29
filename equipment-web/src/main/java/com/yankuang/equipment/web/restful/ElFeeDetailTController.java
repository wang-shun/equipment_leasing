package com.yankuang.equipment.web.restful;

import com.alibaba.fastjson.JSON;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.equipment.model.ElFeeDetailT;
import com.yankuang.equipment.equipment.service.ElFeeDetailTService;
import com.yankuang.equipment.web.dto.UserDTO;
import com.yankuang.equipment.web.service.ReportTPlusService;
import com.yankuang.equipment.web.util.UserFromRedis;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/elFeeDetailT")
public class ElFeeDetailTController {

    @RpcConsumer
    ElFeeDetailTService elFeeDetailTService;
    @Autowired
    ReportTPlusService reportTPlusService;
    @Autowired
    UserFromRedis userFromRedis;

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
            List<ElFeeDetailT> historyList = elFeeDetailTService.findElFeeDetailTs(detailTs, null, null);
            if (historyList != null && historyList.size() > 0) {
                return CommonResponse.ok(historyList);
            }

            // 联表查询
            List<ElFeeDetailT> elFeeDetailTS = reportTPlusService.getElFeeDetailTS(detailTs.getPositionCode(), reportYear, reportMonth);

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
            if (list == null || list.size() == 0) {
                return CommonResponse.errorMsg("参数不得为空");
            }
            UserDTO userDTO = userFromRedis.findByToken();
            if (userDTO != null) {
                for (ElFeeDetailT feeDetail : list) {
                    feeDetail.setCreateBy(userDTO.getId());
                    feeDetail.setUpdateBy(userDTO.getId());
                }
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
