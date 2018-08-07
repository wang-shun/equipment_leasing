package com.yankuang.equipment.web.restful;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.common.util.Constants;
import com.yankuang.equipment.common.util.ResponseMeta;
import com.yankuang.equipment.equipment.model.SbModel;
import com.yankuang.equipment.equipment.service.SbModelService;
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
@RequestMapping("/v1/sbmodel")
public class SbModelController {

    @RpcConsumer(version = "0.0.1",check = "false")
    private SbModelService sbModelService;

    @ApiOperation("create shebei model")
    @RequestMapping(value = "/create")
    public ResponseMeta createSbModel(@Valid SbModel sbModel, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbModelService.createSbModel(sbModel);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"创建设备规格型号成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"创建设备规格型号失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("update shebei model")
    @RequestMapping(value = "/update")
    public ResponseMeta updateSbModel(@Valid SbModel sbModel, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbModelService.updateSbModel(sbModel);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"更新设备规格型号成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"更新设备规格型号失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("find shebei model by id")
    @RequestMapping(value = "/find")
    public ResponseMeta findSbModelByKey(Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            SbModel sbModel = sbModelService.findSbModelByKey(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"编辑设备规格型号信息成功");
            responseMeta.setData(sbModel);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"编辑设备规格型号信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("delete shebei model by id")
    @RequestMapping(value = "/delete")
    public ResponseMeta deleteSbModelByKey(Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            sbModelService.deleteSbModelByKey(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"删除设备规格型号信息成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"删除设备规格型号信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("list shebei model")
    @RequestMapping(value = "/list")
    public ResponseMeta listSbModels(String code,String name,int pageNum,int pageSize){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            PageInfo<SbModel> pageInfo = sbModelService.listAll(code,name,pageNum,pageSize);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备规格型号列表成功");
            responseMeta.setData(pageInfo);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备规格型号列表失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }
}
