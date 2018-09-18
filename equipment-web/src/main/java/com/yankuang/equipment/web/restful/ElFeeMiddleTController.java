package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.equipment.model.ElFeeMiddleT;
import com.yankuang.equipment.equipment.service.ElFeeMiddleTService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/elFeeDetailT")
public class ElFeeMiddleTController {

    @RpcConsumer
    ElFeeMiddleTService elFeeMiddleTService;

    public CommonResponse findElFeeMiddleTs(@RequestBody ElFeeMiddleT elFeeMiddleT) {



        return CommonResponse.ok();

    }

}
