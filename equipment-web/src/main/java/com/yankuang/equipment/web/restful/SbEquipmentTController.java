package com.yankuang.equipment.web.restful;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.common.util.Constants;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.ResponseMeta;
import com.yankuang.equipment.equipment.model.SbEquipmentT;
import com.yankuang.equipment.equipment.service.SbEquipmentTService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(value = "/v1/sbequipmentt")
public class SbEquipmentTController {

    @RpcConsumer
    SbEquipmentTService sbEquipmentTService;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseMeta create(@Valid @RequestBody SbEquipmentT sbEquipmentT, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            SbEquipmentT equipmentT = sbEquipmentTService.findByCode(sbEquipmentT.getCode());
            if(equipmentT!=null){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,"设备识别码已存在!");
            }

            sbEquipmentTService.create(sbEquipmentT);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"创建通用设备成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"创建通用设备失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public ResponseMeta update(@Valid @RequestBody SbEquipmentT sbEquipmentT, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbEquipmentTService.update(sbEquipmentT);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"更新通用设备成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"更新通用设备失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/find/{id}",method = RequestMethod.GET)
    public ResponseMeta findById(@PathVariable("id") Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            SbEquipmentT sbEquipmentT = sbEquipmentTService.findById(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"编辑通用设备信息成功");
            responseMeta.setData(sbEquipmentT);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"编辑通用设备信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ResponseMeta deleteById(@PathVariable("id") Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            sbEquipmentTService.deleteById(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"删除通用设备信息成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"删除通用设备信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseMeta list(SbEquipmentT sbEquipmentT,int pageNum,int pageSize){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            PageInfo<SbEquipmentT> pageInfo = sbEquipmentTService.list(sbEquipmentT,pageNum,pageSize);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询通用设备列表成功");
            responseMeta.setData(pageInfo);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询通用设备列表失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/deletes",method = RequestMethod.DELETE)
    public ResponseMeta deletes(@RequestBody String jsonString){
        ResponseMeta responseMeta = new ResponseMeta();
        if(StringUtils.isEmpty(jsonString)){
            responseMeta.setMeta(Constants.RESPONSE_ERROR,"参数不能为空!");
            return responseMeta;
        }
        List<Long> ids = JsonUtils.jsonToList(jsonString,Long.class);
        try{
            sbEquipmentTService.deletes(ids);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"删除通用设备信息成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"删除通用设备信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }
}
