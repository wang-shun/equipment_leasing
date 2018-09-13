package com.yankuang.equipment.web.restful;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.equipment.model.DtkList;
import com.yankuang.equipment.equipment.service.ElUseItemService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/report")
public class ZEquipmentReportController {

    @RpcConsumer
    ElUseItemService elUseItemService;

    /**
     * @method 查询报表列表
     * @param page
     * @param size
     * @param jsonString
     * @return
     */
    @GetMapping()
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

    @PostMapping()
    public CommonResponse ZReportCreate(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.build(500,"传入参数不能为空！",null);
        }

        return CommonResponse.ok();
    }
}
