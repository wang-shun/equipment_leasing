package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.equipment.service.ZjSbUseItemService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xhh
 *  综机折旧修理费
 */

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/zjZjRepairFee")
public class ZjZjRepairFeeController {

    @RpcConsumer
    ZjSbUseItemService zjSbUseItemService;

    /**
     * @method 查询记录
     * @param page
     * @param size
     * @return
     */
    @GetMapping()
    public CommonResponse findListByPage(@RequestParam Integer page,
                                  @RequestParam Integer size,
                                  @RequestParam String useAt){
        Map zjSbUseItemMap = new HashMap();
        if (!StringUtils.isEmpty(useAt)){
            zjSbUseItemMap.put("useAt",useAt);
        }

        return CommonResponse.ok(zjSbUseItemService.listEquipmentReceipt(page, size, zjSbUseItemMap));
    }



}
