package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.Constants;
import com.yankuang.equipment.common.util.ResponseMeta;
import com.yankuang.equipment.equipment.model.SbType;
import com.yankuang.equipment.equipment.model.SbTypeInfo;
import com.yankuang.equipment.equipment.service.SbTypeService;
import com.yankuang.equipment.web.dto.SbTypeDTO;
import com.yankuang.equipment.web.util.UserFromRedis;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/sbtype")
public class SbTypeController {

    Logger logger = LoggerFactory.getLogger(SbTypeController.class);

    @RpcConsumer
    SbTypeService sbTypeService;

    @Autowired
    UserFromRedis userFromRedis;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseMeta list(){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            List<SbType> list = sbTypeService.list();
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备类型成功");
            responseMeta.setData(list);
        }catch (Exception e){
            logger.info("查询设备类型失败:"+ExceptionUtils.getStackTrace(e));
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备类型失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseMeta create(@Valid @RequestBody SbTypeDTO sbTypeDTO, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }

            SbType type = sbTypeService.findByCode(sbTypeDTO.getCode());
            if(type != null){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,"设备类型编码已存在!");
            }

            SbType sbType = new SbType();
            BeanUtils.copyProperties(sbType,sbTypeDTO);
            SbTypeInfo sbTypeInfo = new SbTypeInfo();
            BeanUtils.copyProperties(sbTypeInfo,sbTypeDTO);
            sbType.setCreateBy(userFromRedis.findByToken().getCode());
            sbTypeInfo.setCreateBy(userFromRedis.findByToken().getCode());
            sbTypeService.create(sbType,sbTypeInfo);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"创建设备类型及信息成功");
        }catch (Exception e){
            logger.info("创建设备类型及信息失败:"+ExceptionUtils.getStackTrace(e));
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
            logger.info("删除设备类型及信息失败:"+ExceptionUtils.getStackTrace(e));
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
            logger.info("查询设备类型及信息失败:"+ExceptionUtils.getStackTrace(e));
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备类型失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }
}
