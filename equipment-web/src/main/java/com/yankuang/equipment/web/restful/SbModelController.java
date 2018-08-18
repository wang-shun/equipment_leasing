package com.yankuang.equipment.web.restful;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.common.util.Constants;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.ResponseMeta;
import com.yankuang.equipment.equipment.model.SbModel;
import com.yankuang.equipment.equipment.service.SbModelService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/sbmodel")
public class SbModelController {

    @RpcConsumer
    SbModelService sbModelService;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseMeta create(@Valid @RequestBody SbModel sbModel, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbModelService.create(sbModel);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"创建设备规格型号成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"创建设备规格型号失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public ResponseMeta update(@Valid @RequestBody SbModel sbModel, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbModelService.update(sbModel);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"更新设备规格型号成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"更新设备规格型号失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/find/{id}",method = RequestMethod.GET)
    public ResponseMeta findById(@PathVariable("id") Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            SbModel sbModel = sbModelService.findById(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"编辑设备规格型号信息成功");
            responseMeta.setData(sbModel);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"编辑设备规格型号信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ResponseMeta deleteById(@PathVariable("id") Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            sbModelService.deleteById(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"删除设备规格型号信息成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"删除设备规格型号信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseMeta list(String code,String name,int pageNum,int pageSize){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            PageInfo<SbModel> pageInfo = sbModelService.list(code,name,pageNum,pageSize);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备规格型号列表成功");
            responseMeta.setData(pageInfo);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备规格型号列表失败");
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
            sbModelService.deletes(ids);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"删除设备规格型号信息成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"删除设备规格型号信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/listBySbtypeThree",method = RequestMethod.GET)
    public ResponseMeta listBySbtypeThree(String sbtypeThree){
        ResponseMeta responseMeta = new ResponseMeta();

        try{
            List<SbModel> list = sbModelService.listBySbtypeThree(sbtypeThree);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备规格型号列表成功");
            responseMeta.setData(list);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备规格型号列表失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }

        return responseMeta;
    }
}
