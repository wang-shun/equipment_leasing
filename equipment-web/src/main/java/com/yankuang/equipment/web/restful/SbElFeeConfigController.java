package com.yankuang.equipment.web.restful;

import com.github.pagehelper.PageInfo;
import com.google.common.primitives.Longs;
import com.yankuang.equipment.common.util.Constants;
import com.yankuang.equipment.common.util.ResponseMeta;
import com.yankuang.equipment.equipment.model.SbElFeeConfig;
import com.yankuang.equipment.equipment.service.SbElFeeConfigService;
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
@RequestMapping("/v1/sbelfeeconfig")
public class SbElFeeConfigController {

    public static final Logger logger = Logger.getLogger(SbElFeeConfigController.class);

    @RpcConsumer
    SbElFeeConfigService sbElFeeConfigService;

    @Autowired
    UserFromRedis userFromRedis;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseMeta create(@Valid @RequestBody SbElFeeConfig sbElFeeConfig, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            SbElFeeConfig elFeeConfig = sbElFeeConfigService.findByYear(sbElFeeConfig.getYear());
            if(elFeeConfig!=null){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,"此年份的配置信息已存在!");
            }
            sbElFeeConfig.setCreateBy(userFromRedis.findByToken().getCode());
            sbElFeeConfigService.create(sbElFeeConfig);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"创建设备租赁费用配置成功");
        }catch (Exception e){
            logger.info("创建设备租赁费用配置失败:"+ExceptionUtils.getStackTrace(e));
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"创建设备租赁费用配置失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }

        return responseMeta;
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public ResponseMeta update(@Valid @RequestBody SbElFeeConfig sbElFeeConfig, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbElFeeConfig.setUpdateBy(userFromRedis.findByToken().getCode());
            sbElFeeConfigService.update(sbElFeeConfig);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"更新设备租赁费用配置成功");
        }catch (Exception e){
            logger.info("更新设备租赁费用配置失败:"+ExceptionUtils.getStackTrace(e));
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"更新设备租赁费用配置失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/find/{id}",method = RequestMethod.GET)
    public ResponseMeta findById(@PathVariable("id") Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            SbElFeeConfig sbElFeeConfig = sbElFeeConfigService.findById(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"编辑设备租赁费用配置成功");
            responseMeta.setData(sbElFeeConfig);
        }catch (Exception e){
            logger.info("编辑设备租赁费用配置失败:"+ExceptionUtils.getStackTrace(e));
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"编辑设备租赁费用配置失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public ResponseMeta deleteById(@PathVariable("id") Long id){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            sbElFeeConfigService.deleteById(id);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"删除设备租赁费用配置成功");
        }catch (Exception e){
            logger.info("删除设备租赁费用配置失败:"+ExceptionUtils.getStackTrace(e));
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"删除设备租赁费用配置失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ResponseMeta list(int pageNum,int pageSize){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            PageInfo<SbElFeeConfig> pageInfo = sbElFeeConfigService.list(pageNum,pageSize);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备租赁费用配置成功");
            responseMeta.setData(pageInfo);
        }catch (Exception e){
            logger.info("查询设备租赁费用配置失败:"+ExceptionUtils.getStackTrace(e));
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备租赁费用配置失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/deletes/{id}",method = RequestMethod.DELETE)
    public ResponseMeta deletes(@PathVariable("id") long[] id){
        ResponseMeta responseMeta = new ResponseMeta();
        List<Long> ids = Longs.asList(id);
        try{
            sbElFeeConfigService.deletes(ids);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"删除设备租赁费用配置成功");
        }catch (Exception e){
            logger.info("删除设备租赁费用配置失败:"+ExceptionUtils.getStackTrace(e));
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"删除设备租赁费用配置失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }
}
