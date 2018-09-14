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
import com.yankuang.equipment.web.util.DateConverterConfig;
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
        //实体类属性数据转换到dto中
        BeanUtils.copyProperties(zEquipmentDTO,listZReport);
        if (zEquipmentReportService.create(listZReport,ListZReportItems)) {
            return CommonResponse.ok("创建成功");
        }
        return CommonResponse.build(500,"创建失败",null);
    }

    /**
     * 分页查询报表历史
     * @param page
     * @param size
     * @param jsonString
     * @return
     */
    @GetMapping("/findByPage")
    public CommonResponse findByPage(@RequestParam Integer page,
                                        @RequestParam Integer size,
                                        @RequestParam String jsonString) {
        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.build(500,"查询条件不能为空",null);
        }
        ZEquipmentDTO zEquipmentDTO = JsonUtils.jsonToPojo(jsonString,ZEquipmentDTO.class);

        //传入查询条件
        Map listZReportMap = new HashMap();
        listZReportMap.put("id",zEquipmentDTO.getId());
        listZReportMap.put("useDeptName",zEquipmentDTO.getUseDeptName());
        listZReportMap.put("number",zEquipmentDTO.getNumber());
        listZReportMap.put("createExcelName",zEquipmentDTO.getCreateExcelName());
        listZReportMap.put("statusName",zEquipmentDTO.getStatusName());
        listZReportMap.put("sureName",zEquipmentDTO.getSureName());
        listZReportMap.put("sum",zEquipmentDTO.getSum());
        listZReportMap.put("useDeptCode",zEquipmentDTO.getUseDeptCode());
        listZReportMap.put("createExcelCode",zEquipmentDTO.getCreateExcelCode());
        listZReportMap.put("statusCode",zEquipmentDTO.getStatusCode());
        listZReportMap.put("sureCode",zEquipmentDTO.getSureCode());
        listZReportMap.put("equipmentPosition",zEquipmentDTO.getEquipmentPosition());
        listZReportMap.put("useYear",zEquipmentDTO.getUseYear());
        listZReportMap.put("useMonth",zEquipmentDTO.getUseMonth());
        listZReportMap.put("type",zEquipmentDTO.getType());
        listZReportMap.put("listZReportItem",zEquipmentDTO.getListZReportItem());

        String useAtString = zEquipmentDTO.getUseAtString();
        if (useAtString != null && !"".equals(useAtString)){
            DateConverterConfig dateConverterConfig = new DateConverterConfig();
            dateConverterConfig.convert(useAtString);
            listZReportMap.put("useAt",useAtString);
        }else {
            listZReportMap.put("useAt",zEquipmentDTO.getUseAt());
        }

        return CommonResponse.ok(zEquipmentReportService.findByPage(page,size,listZReportMap));
    }

}
