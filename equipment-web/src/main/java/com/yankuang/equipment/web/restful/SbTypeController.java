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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/sbtype")
public class SbTypeController {

    @RpcConsumer(version = "0.0.1",check = "false")
    private SbTypeService sbTypeService;

    @ApiOperation("select all shebei types")
    @RequestMapping(value = "/list")
    public ResponseMeta listSbTypes(){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            List<SbType> list = sbTypeService.listSbTypes();
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备类型成功");
            responseMeta.setData(list);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备类型失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("create shebei record")
    @RequestMapping(value = "/create")
    public ResponseMeta createSbType(@Valid SbType sbType, SbTypeInfo sbTypeInfo, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbTypeService.createSbType(sbType,sbTypeInfo);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"创建设备类型及信息成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"创建设备类型及信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("delete shebei type by id")
    @RequestMapping(value = "/delete")
    public ResponseMeta deleteSbTypeByKey(Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            sbTypeService.deleteSbTypeByKey(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"删除设备类型信息成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"删除设备类型信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("select shebei types by pcode or level")
    @RequestMapping(value = "/listByCondition")
    public ResponseMeta listSbTypesByPcodeOrLevel(String pcode,int level){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            List<SbType> list = sbTypeService.listSbTypesByPcodeOrLevel(pcode,level);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备类型成功");
            responseMeta.setData(list);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备类型失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }
}
