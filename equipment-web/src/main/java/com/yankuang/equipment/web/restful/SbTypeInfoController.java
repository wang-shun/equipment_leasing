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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/sbtypeinfo")
public class SbTypeInfoController {

    @RpcConsumer
    SbTypeInfoService sbTypeInfoService;

    @RequestMapping(value = "/find/{id}",method = RequestMethod.GET)
    public ResponseMeta findById(@PathVariable("id") Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            SbTypeInfo sbTypeInfo = sbTypeInfoService.findById(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备类型信息成功");
            responseMeta.setData(sbTypeInfo);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备类型信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseMeta create(@Valid SbTypeInfo sbTypeInfo, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbTypeInfoService.create(sbTypeInfo);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"创建设备类型信息成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"创建设备类型信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public ResponseMeta update(@Valid SbTypeInfo sbTypeInfo,BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbTypeInfoService.update(sbTypeInfo);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"更新设备类型信息成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"更新设备类型信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

}
