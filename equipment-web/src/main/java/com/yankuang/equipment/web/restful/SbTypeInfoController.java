package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.Constants;
import com.yankuang.equipment.common.util.ResponseMeta;
import com.yankuang.equipment.equipment.model.SbTypeInfo;
import com.yankuang.equipment.equipment.service.SbTypeInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/sbtypeinfo")
public class SbTypeInfoController {

    @RpcConsumer(version = "0.0.1",check = "false")
    private SbTypeInfoService sbTypeInfoService;

    @ApiOperation("select shebei by primary key")
    @RequestMapping(value = "/find")
    public ResponseMeta findSbTypeInfoByKey(Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            SbTypeInfo sbTypeInfo = sbTypeInfoService.findSbTypeInfoByKey(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备类型信息成功");
            responseMeta.setData(sbTypeInfo);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备类型信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("create shebei typeinfo record")
    @RequestMapping(value = "/create")
    public ResponseMeta createSbTypeInfo(@Valid SbTypeInfo sbTypeInfo, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbTypeInfoService.createSbTypeInfo(sbTypeInfo);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"创建设备类型信息成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"创建设备类型信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("update shebei typeinfo record")
    @RequestMapping(value = "/update")
    public ResponseMeta updateSbTypeInfo(@Valid SbTypeInfo sbTypeInfo,BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbTypeInfoService.updateSbTypeInfo(sbTypeInfo);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"更新设备类型信息成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"更新设备类型信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

}
