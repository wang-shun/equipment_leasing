package com.yankuang.equipment.web.restful;

import com.github.pagehelper.PageInfo;
import com.google.common.primitives.Longs;
import com.yankuang.equipment.common.util.Constants;
import com.yankuang.equipment.common.util.ResponseMeta;
import com.yankuang.equipment.equipment.model.SbEquipmentT;
import com.yankuang.equipment.equipment.service.SbEquipmentTService;
import com.yankuang.equipment.web.util.UserFromRedis;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping(value = "/v1/sbequipmentt")
public class SbEquipmentTController {

    public static final Logger logger = Logger.getLogger(SbModelController.class);

    @RpcConsumer
    SbEquipmentTService sbEquipmentTService;

    @Autowired
    UserFromRedis userFromRedis;

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
            sbEquipmentT.setCreateBy(userFromRedis.findByToken().getCode());
            sbEquipmentTService.create(sbEquipmentT);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"创建通用设备成功");
        }catch (Exception e){
            logger.info("创建通用设备失败:"+ExceptionUtils.getStackTrace(e));
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
            sbEquipmentT.setUpdateBy(userFromRedis.findByToken().getCode());
            sbEquipmentTService.update(sbEquipmentT);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"更新通用设备成功");
        }catch (Exception e){
            logger.info("更新通用设备失败:"+ExceptionUtils.getStackTrace(e));
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
            logger.info("编辑通用设备失败:"+ExceptionUtils.getStackTrace(e));
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
            logger.info("删除通用设备失败:"+ExceptionUtils.getStackTrace(e));
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
            logger.info("查询通用设备失败:"+ExceptionUtils.getStackTrace(e));
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询通用设备列表失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/deletes/{id}",method = RequestMethod.DELETE)
    public ResponseMeta deletes(@PathVariable("id") long[] id){
        ResponseMeta responseMeta = new ResponseMeta();
        List<Long> ids = Longs.asList(id);
        try{
            sbEquipmentTService.deletes(ids);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"删除通用设备信息成功");
        }catch (Exception e){
            logger.info("删除通用设备失败:"+ExceptionUtils.getStackTrace(e));
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"删除通用设备信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }
}
