package com.yankuang.equipment.web.restful;

import com.alibaba.dubbo.config.support.Parameter;
import com.yankuang.equipment.equipment.model.SbTypeInfo;
import com.yankuang.equipment.equipment.service.SbTypeInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.*;

@Api
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/equipment/typeinfo")
public class SbTypeInfoController {

    @RpcConsumer(version = "0.0.1",check = "false")
    private SbTypeInfoService sbTypeInfoService;

    @ApiOperation("select shebei by primary key")
    @RequestMapping(value = "/{id}")
    public SbTypeInfo selectByPrimaryKey(@PathVariable Long id){
        return sbTypeInfoService.selectByPrimaryKey(id);
    }

}
