package com.yankuang.equipment.web.restful;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.equipment.model.DtkList;
import com.yankuang.equipment.equipment.model.ListZReport;
import com.yankuang.equipment.equipment.model.ListZReportItem;
import com.yankuang.equipment.equipment.service.ElUseItemService;
import com.yankuang.equipment.equipment.service.ZEquipmentReportService;
import com.yankuang.equipment.web.dto.ZEquipmentDTO;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/report")
public class ZEquipmentReportController {

    @RpcConsumer
    ElUseItemService elUseItemService;

    @RpcConsumer
    ZEquipmentReportService zEquipmentReportService;

    /**
     * @method 查询报表列表
     * @param page
     * @param size
     * @param jsonString
     * @return
     */
    @GetMapping
    public CommonResponse ZReportSelect(@RequestParam Integer page,
                                        @RequestParam Integer size,
                                        @RequestParam String jsonString) {
        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.build(500,"返回对象不能为空",null);
        }
        Map elUseItemMap = new HashMap();
        DtkList dtkList = JsonUtils.jsonToPojo(jsonString,DtkList.class);
        elUseItemMap.put("dtkList",dtkList);
        PageInfo pageInfo = elUseItemService.dtkReport(page,size,elUseItemMap);

        return CommonResponse.ok(elUseItemMap);
    }

    /**
     * 在导出时向数据库中存入数据
     * @param jsonString
     * @return
     */
    @PostMapping
    public CommonResponse ZReportCreate(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.build(500,"传入参数不能为空！",null);
        }

        ZEquipmentDTO zEquipmentDTO = JsonUtils.jsonToPojo(jsonString,ZEquipmentDTO.class);
        if (zEquipmentDTO.getUseDeptName() == null || "".equals(zEquipmentDTO.getUseDeptName())) {
            return CommonResponse.build(500, "部门名称不能为空！", null);
        }

        if (zEquipmentDTO.getNumber() == null || "".equals(zEquipmentDTO.getNumber())) {
            return CommonResponse.build(500, "编号不能为空！", null);
        }

        List<ListZReportItem> ListZReportItems = zEquipmentDTO.getListZReportItems();

        //判断清单列表是否有数据
        if (ListZReportItems == null || ListZReportItems.size() <= 0) {
            return CommonResponse.build(200, "报表没有数据", null);
        }
        ListZReport listZReport = new ListZReport();
        BeanUtils.copyProperties(zEquipmentDTO,listZReport);
        if (zEquipmentReportService.create(listZReport,ListZReportItems)) {
            return CommonResponse.ok("创建成功");
        }
        return CommonResponse.build(500,"创建失败",null);
    }
}
