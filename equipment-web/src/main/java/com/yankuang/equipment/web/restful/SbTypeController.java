package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.Constants;
import com.yankuang.equipment.common.util.ResponseMeta;
import com.yankuang.equipment.equipment.model.SbType;
import com.yankuang.equipment.equipment.model.SbTypeInfo;
import com.yankuang.equipment.equipment.service.SbTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/sbtype")
public class SbTypeController {

    @RpcConsumer
    SbTypeService sbTypeService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseMeta list(){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            List<SbType> list = sbTypeService.list();
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备类型成功");
            responseMeta.setData(list);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备类型失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseMeta create(@Valid SbType sbType, SbTypeInfo sbTypeInfo, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbTypeService.create(sbType,sbTypeInfo);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"创建设备类型及信息成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"创建设备类型及信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ResponseMeta deleteById(@PathVariable("id") Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            sbTypeService.deleteById(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"删除设备类型信息成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"删除设备类型信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/listByPcodeOrLevel",method = RequestMethod.GET)
    public ResponseMeta listByPcodeOrLevel(@RequestParam String p_code,@RequestParam int level){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            List<SbType> list = sbTypeService.listByPcodeOrLevel(p_code,level);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备类型成功");
            responseMeta.setData(list);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备类型失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }
}
