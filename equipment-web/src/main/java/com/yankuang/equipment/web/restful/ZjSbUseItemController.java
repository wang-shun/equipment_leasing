package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.equipment.model.ZjSbUseItem;
import com.yankuang.equipment.equipment.model.ZjSbUseItemSign;
import com.yankuang.equipment.equipment.service.ZjSbUseItemService;
import com.yankuang.equipment.equipment.service.ZjSbUseItemSignService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xhh
 * 综机设备使用交接单
 */

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/zjSbUse")
public class ZjSbUseItemController {

    @RpcConsumer
    ZjSbUseItemService zjSbUseItemService;

    @RpcConsumer
    ZjSbUseItemSignService zjSbUseItemSignService;

    /**
     * @method 分页查询使用交接单
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/findByUseDate")
    public CommonResponse findListByPage(@RequestParam Integer page,
                                  @RequestParam Integer size,@RequestParam String jsonString){

        Map zjSbUseItemMap = new HashMap();
        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorMsg("传入的参数不能为空");
        }
        ZjSbUseItem zjSbUseItem = JsonUtils.jsonToPojo(jsonString,ZjSbUseItem.class);

        if(StringUtils.isEmpty(zjSbUseItem.getUseAt())){
            return CommonResponse.errorMsg("交接日期不能为空");
        }
        zjSbUseItemMap.put("useAt",zjSbUseItem.getUseAt());

        if(!StringUtils.isEmpty(zjSbUseItem.getStubUnit())){
            zjSbUseItemMap.put("stubUnit",zjSbUseItem.getStubUnit());
        }
        zjSbUseItemMap.put("isUse",zjSbUseItem.getIsUse());//传入使用或者是停用

        //需要进行判断，进入哪个方法，先判断综机设备交接单中有这个日期没，有的话就直接从交接单中的表查询
        //通过日期查询
        Integer count = zjSbUseItemSignService.findByHandoverTime(zjSbUseItem.getUseAt());
        if(count>0){
            return CommonResponse.ok(zjSbUseItemService.list(page,size,zjSbUseItemMap));
        }else{

            return CommonResponse.ok(zjSbUseItemService.listEquipmentReceipt(page,size,zjSbUseItemMap));
        }

    }

    /**
     * @method 设备使用交接单保存功能
     * @param jsonString
     * @return
     */
    @PostMapping("/saveZjSbUse")
    public CommonResponse saveZjSbUse(@RequestBody String jsonString){
        if (com.yankuang.equipment.common.util.StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorMsg("参数不能为空");
        }
        ZjSbUseItemSign zjSbUseItemSignReport = JsonUtils.jsonToPojo(jsonString,ZjSbUseItemSign.class);

        zjSbUseItemSignService.create(zjSbUseItemSignReport);
        return CommonResponse.ok();
    }


}
