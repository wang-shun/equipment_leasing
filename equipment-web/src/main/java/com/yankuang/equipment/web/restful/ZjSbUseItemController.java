package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.equipment.model.ZjSbUseItem;
import com.yankuang.equipment.equipment.service.ZjSbUseItemService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xhh
 *
 */

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/zjSbUse")
public class ZjSbUseItemController {

    @RpcConsumer
    ZjSbUseItemService zjSbUseItemService;


    /**
     * @method 分页查询领用记录
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/findByUseDate")
    public CommonResponse findListByPage(@RequestParam Integer page,
                                  @RequestParam Integer size,
                                  @RequestParam String jsonString){
        Map zjSbUseItemMap = new HashMap();
//        if (jsonString != null || "".equals(jsonString)){
//            ZjSbUseItem zjSbUseItem = JsonUtils.jsonToPojo(jsonString, ZjSbUseItem.class);
//            if (zjSbUseItem == null){
//                return CommonResponse.errorMsg("传入对象为空");
//            }
//
//            zjSbUseItemMap.put("useDate",zjSbUseItem.getUseDate());
//        }
        if (!StringUtils.isEmpty(jsonString)){
            ZjSbUseItem zjSbUseItem = JsonUtils.jsonToPojo(jsonString, ZjSbUseItem.class);
            zjSbUseItemMap.put("useAt",zjSbUseItem.getUseAt());
        }

        return CommonResponse.ok(zjSbUseItemService.listEquipmentReceipt(page, size, zjSbUseItemMap));
    }
}
