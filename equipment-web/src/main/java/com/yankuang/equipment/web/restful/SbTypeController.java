package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.equipment.model.SbType;
import com.yankuang.equipment.equipment.service.SbTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/equipment/type")
public class SbTypeController {

    @RpcConsumer(version = "0.0.1",check = "false")
    private SbTypeService sbTypeService;

    @ApiOperation("select all shebei types")
    @RequestMapping(value = "/types")
    public List<SbType> selectAllSbTypes(){
        return sbTypeService.selectAllSbTypes();
    }

    @ApiOperation("insert into shebei record")
    @RequestMapping(value = "/insert")
    public void insertSbType(SbType sbType){
        sbTypeService.insertSbType(sbType);
    }
}
