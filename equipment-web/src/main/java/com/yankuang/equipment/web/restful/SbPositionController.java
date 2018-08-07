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
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/sbposition")
public class SbPositionController {

    @RpcConsumer(version = "0.0.1",check = "false")
    private SbPositionService sbPositionService;

    @ApiOperation("create shebei Position")
    @RequestMapping(value = "/create")
    public ResponseMeta createSbPosition(@Valid SbPosition sbPosition, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbPositionService.createSbPosition(sbPosition);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"创建设备位置成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"创建设备位置失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("update shebei Position")
    @RequestMapping(value = "/update")
    public ResponseMeta updateSbPosition(@Valid SbPosition sbPosition, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbPositionService.updateSbPosition(sbPosition);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"创建设备位置成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"创建设备位置失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("find shebei Position by id")
    @RequestMapping(value = "/find")
    public ResponseMeta findSbPositionByKey(Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            SbPosition sbPosition = sbPositionService.findSbPositionByKey(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备位置信息成功");
            responseMeta.setData(sbPosition);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备位置信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("delete shebei Position by id")
    @RequestMapping(value = "/delete")
    public ResponseMeta deleteSbPositionByKey(Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            sbPositionService.deleteSbPositionByKey(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"删除设备位置信息成功");
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"删除设备位置信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @ApiOperation("list shebei position")
    @RequestMapping(value = "/list")
    public ResponseMeta listSbPositions(String code,String name,int pageNum,int pageSize){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            PageInfo<SbPosition> list = sbPositionService.listAll(code,name,pageNum,pageSize);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备功能位置列表成功");
            responseMeta.setData(list);
        }catch (Exception e){
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备功能位置列表失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }
}
