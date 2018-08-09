package com.yankuang.equipment.web.restful;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.common.util.Constants;
import com.yankuang.equipment.common.util.ResponseMeta;
import com.yankuang.equipment.equipment.model.SbEquipmentT;
import com.yankuang.equipment.equipment.service.SbEquipmentTService;
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
@RequestMapping(value = "/v1/sbequipmentt")
public class SbEquipmentTController {

    @RpcConsumer(version = "0.0.1",check = "false")
    private SbEquipmentTService sbEquipmentTService;

    @ApiOperation("create shebei t")
    @RequestMapping(value = "/create")
    public ResponseMeta createSbEquipmentT(@Valid SbEquipmentT sbEquipmentT, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbEquipmentTService.createSbEquipmentT(sbEquipmentT);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"创建通用设备成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"创建通用设备失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("update shebei t")
    @RequestMapping(value = "/update")
    public ResponseMeta updateSbModel(@Valid SbEquipmentT sbEquipmentT, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbEquipmentTService.updateSbEquipmentT(sbEquipmentT);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"更新通用设备成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"更新通用设备失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("find shebei t by id")
    @RequestMapping(value = "/find")
    public ResponseMeta findSbModelByKey(Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            SbEquipmentT sbEquipmentT = sbEquipmentTService.findSbEquipmentByKey(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"编辑通用设备信息成功");
            responseMeta.setData(sbEquipmentT);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"编辑通用设备信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("delete shebei t by id")
    @RequestMapping(value = "/delete")
    public ResponseMeta deleteSbEquipmentByKey(Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            sbEquipmentTService.deleteSbEquipmentByKey(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"删除通用设备信息成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"删除通用设备信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("list shebei t")
    @RequestMapping(value = "/list")
    public ResponseMeta listSbEquipments(SbEquipmentT sbEquipmentT,int pageNum,int pageSize){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            PageInfo<SbEquipmentT> pageInfo = sbEquipmentTService.listAll(sbEquipmentT,pageNum,pageSize);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询通用设备列表成功");
            responseMeta.setData(pageInfo);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询通用设备列表失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

}
