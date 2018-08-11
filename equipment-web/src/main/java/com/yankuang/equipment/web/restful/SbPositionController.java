package com.yankuang.equipment.web.restful;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.common.util.Constants;
import com.yankuang.equipment.common.util.ResponseMeta;
import com.yankuang.equipment.equipment.model.SbPosition;
import com.yankuang.equipment.equipment.service.SbPositionService;
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
@RequestMapping("/v1/sbposition")
public class SbPositionController {

    @RpcConsumer
    SbPositionService sbPositionService;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseMeta create(@Valid SbPosition sbPosition, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbPositionService.create(sbPosition);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"创建设备位置成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"创建设备位置失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public ResponseMeta update(@Valid SbPosition sbPosition, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbPositionService.update(sbPosition);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"创建设备位置成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"创建设备位置失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/find/{id}",method = RequestMethod.GET)
    public ResponseMeta findById(@PathVariable("id") Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            SbPosition sbPosition = sbPositionService.findById(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备位置信息成功!");
            responseMeta.setData(sbPosition);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备位置信息失败!");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ResponseMeta deleteById(@PathVariable("id") Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            sbPositionService.deleteById(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"删除设备位置信息成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"删除设备位置信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseMeta list(String code,String name,int pageNum,int pageSize){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            PageInfo<SbPosition> list = sbPositionService.list(code,name,pageNum,pageSize);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备功能位置列表成功");
            responseMeta.setData(list);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备功能位置列表失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }
}
