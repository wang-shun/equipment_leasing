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
     * @method 分页查询领用记录
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
 //需要的参数
    /**
     * 矿的名称，需要公示去计算综机折旧修理费
     * 需要判断月份，
     * 1、年固定资产折旧费：设备原值/设备折旧年限
     * 2.年修理费：设备原值*1.5%
     */

}
