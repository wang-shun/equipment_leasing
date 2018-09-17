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
public class ZjDepreciationCostController {

    @RpcConsumer
    ZjSbUseItemService zjSbUseItemService;

    /**
     * @method 查询记录
     * @param assetComp
     * @param
     * @return
     */
    //这边需要从前台传（1108,1730(资产公司)，月份）
    //折旧修理费分煤业，东华，和汇总（煤业东华之和）
    @GetMapping()
    public CommonResponse findListByPage(@RequestParam String assetComp,
                                          @RequestParam String repairMonth){
        Map zjSbUseItemMap = new HashMap();
        if (StringUtils.isEmpty(assetComp)){
            return CommonResponse.errorMsg("资产公司不能为空");
        }
        if (StringUtils.isEmpty(repairMonth)){
            return CommonResponse.errorMsg("月份不能为空");
        }

        return CommonResponse.ok();
    }



}
