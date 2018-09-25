package com.yankuang.equipment.web.restful;

import com.alibaba.fastjson.JSON;
import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.authority.service.DeptService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.equipment.model.ElFeeMiddleT;
import com.yankuang.equipment.equipment.model.ElFeePositionT;
import com.yankuang.equipment.equipment.service.ElFeeMiddleTService;
import com.yankuang.equipment.equipment.service.ElFeePositionTService;
import com.yankuang.equipment.web.dto.GenericDTO;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/elFeePositionT")
public class ElFeePositionTController {

    @RpcConsumer
    ElFeePositionTService elFeePositionTService;
    @RpcConsumer
    ElFeeMiddleTService elFeeMiddleTService;
    @RpcConsumer
    DeptService deptService;

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
            elFeePositionT.setReportYear(reportYear);
            elFeePositionT.setReportMonth(reportMonth);
            List<ElFeePositionT> historyList = elFeePositionTService.list(elFeePositionT);
            for (ElFeePositionT elPositionT : historyList) {
                if (elPositionT != null && !StringUtils.isEmpty(elPositionT.getPositionMap())) {
                    Map<String, Double> positionMap = JsonUtils.jsonToPojo(elPositionT.getPositionMap(), Map.class);
                    if (positionMap == null) {
                        continue;
                    }
                    elPositionT.setDepositMap(positionMap);
                    List<GenericDTO> genericDTOList = new ArrayList<>();
                    for (String positionName : positionMap.keySet()) {
                        GenericDTO genericDTO = new GenericDTO(positionName, positionMap.get(positionName));
                        genericDTOList.add(genericDTO);
                    }
                    elPositionT.setDepositList(genericDTOList);
                }
            }
            if (historyList != null && historyList.size() > 0) {
                return CommonResponse.ok(historyList);
            }

            // 联表查询
            ElFeeMiddleT elFeeMiddleT = new ElFeeMiddleT();
            elFeeMiddleT.setReportYear(reportYear);
            elFeeMiddleT.setReportMonth(reportMonth);
            List<ElFeeMiddleT> elFeeMiddleTs = elFeeMiddleTService.findElFeeMiddleTs(elFeeMiddleT, null, null);
            Map<String, List<ElFeeMiddleT>> middleTMap = new HashMap<>();
            Map<String, String> positionMap = new HashMap<>();
            for (ElFeeMiddleT middleT : elFeeMiddleTs) {
                if (middleT == null) {
                    continue;
                }
                List<ElFeeMiddleT> tempList = middleTMap.get(middleT.getMiddleName());
                String positionName = positionMap.get(middleT.getPositionCode());
                if (tempList == null) {
                    tempList = new ArrayList<>();
                    tempList.add(middleT);
                    middleTMap.put(middleT.getMiddleName(), tempList);
                } else {
                    tempList.add(middleT);
                }
                if (positionName == null) {
                    positionMap.put(middleT.getPositionCode(), middleT.getPositionName());
                }
            }

            for (String poCode : positionMap.keySet()) {
                if (poCode == null) {
                    positionMap.remove(poCode);
                    continue;
                }
                Dept dept = deptService.findByCode(poCode);
                if (dept == null) {
                    positionMap.remove(poCode);
                }
//                else if(dept.getAddress() == null || !dept.getAddress().contains("济宁")) {
//                    positionMap.remove(poCode);
//                }
            }
            if (positionMap.size() <= 0) {
                return CommonResponse.build(200, "查询结果为空", null);
            }
            List<ElFeePositionT> positionTs = new ArrayList<>();
            Date now = new Date();
            for (String middleName : middleTMap.keySet()) {
                if (middleName == null) {
                    continue;
                }
                List<ElFeeMiddleT> middleTs = middleTMap.get(middleName);
                if (middleTs == null) {
                    continue;
                }
                ElFeePositionT positionT = new ElFeePositionT();
                for (ElFeeMiddleT middleT : middleTs) {
                    if ((positionT.getMiddleCode() == null
                            || positionT.getMiddleName() == null)
                            && (middleT != null && !StringUtils.isEmpty(middleT.getMiddleName())
                            && !StringUtils.isEmpty(middleT.getMiddleCode()))) {
                        positionT.setMiddleCode(middleT.getMiddleCode());
                        positionT.setMiddleName(middleT.getMiddleName());
                        break;
                    }
                }
                Map<String, Double> positionMapi = new HashMap<>();
                List<GenericDTO> genericDTOs = new ArrayList<>();
                double totalFeei = 0D;
                for (String poCodei : positionMap.keySet()) {
                    if (poCodei == null) {
                        continue;
                    }
                    boolean res = false;
                    for (ElFeeMiddleT middleTi: middleTs) {
                        if (poCodei != null && middleTi != null && poCodei.equals(middleTi.getPositionCode())) {
                            positionMapi.put(positionMap.get(poCodei), middleTi.getTotalFee());
                            GenericDTO genericDTO = new GenericDTO(positionMap.get(poCodei), middleTi.getTotalFee());
                            genericDTOs.add(genericDTO);
                            res = true;
                        }
                    }
                    if (!res) {
                        positionMapi.put(positionMap.get(poCodei), 0D);
                        GenericDTO genericDTO = new GenericDTO(positionMap.get(poCodei), 0D);
                        genericDTOs.add(genericDTO);
                    }
                    totalFeei += positionMapi.get(positionMap.get(poCodei));
                }
                positionT.setDepositMap(positionMapi);
                positionT.setPositionMap(JsonUtils.objectToJson(positionMapi));
                positionT.setDepositList(genericDTOs);
                positionT.setTotalFee(totalFeei);
                positionT.setReportYear(reportYear);
                positionT.setReportMonth(reportMonth);
                positionT.setExportAt(now);
                positionT.setVersion(0L);
                positionT.setStatus((byte)1);
                positionT.setRemarks("");
                positionT.setPoStatus(elFeePositionT.getPoStatus());
                positionTs.add(positionT);
            }

            if (positionTs == null && positionTs.size() <= 0) {
                return CommonResponse.build(200, "查询结果为空", null);
            }

            return CommonResponse.ok(positionTs);
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
