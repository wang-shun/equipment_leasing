package com.yankuang.equipment.web.restful;

import com.github.pagehelper.PageInfo;
import com.google.common.primitives.Longs;
import com.yankuang.equipment.common.util.Constants;
import com.yankuang.equipment.common.util.ResponseMeta;
import com.yankuang.equipment.equipment.model.SbModel;
import com.yankuang.equipment.equipment.service.SbModelService;
import com.yankuang.equipment.web.util.UserFromRedis;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
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
@RequestMapping("/v1/sbmodel")
public class SbModelController {

    Logger logger = LoggerFactory.getLogger(SbModelController.class);

    @RpcConsumer
    SbModelService sbModelService;

    @Autowired
    UserFromRedis userFromRedis;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    public ResponseMeta create(@Valid @RequestBody SbModel sbModel, BindingResult bindingResult){
        ResponseMeta responseMeta = new ResponseMeta();
        try{
            if (bindingResult.hasErrors()){
                return responseMeta.setMeta(Constants.RESPONSE_ERROR,bindingResult.getAllErrors().get(0).getDefaultMessage());
            }
            sbModel.setCreateBy(userFromRedis.findByToken().getCode());
            sbModelService.create(sbModel);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"创建设备规格型号成功");
        }catch (Exception e){
            logger.info("创建设备规格型号失败:"+ExceptionUtils.getStackTrace(e));
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
            sbModel.setUpdateBy(userFromRedis.findByToken().getCode());
            sbModelService.update(sbModel);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"更新设备规格型号成功");
        }catch (Exception e){
            logger.info("更新设备规格型号失败:"+ExceptionUtils.getStackTrace(e));
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
            logger.info("编辑设备规格型号失败:"+ExceptionUtils.getStackTrace(e));
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
            logger.info("删除设备规格型号失败:"+ExceptionUtils.getStackTrace(e));
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
            logger.info("查询设备规格型号失败:"+ExceptionUtils.getStackTrace(e));
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备规格型号列表失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @RequestMapping(value = "/deletes/{id}",method = RequestMethod.DELETE)
    public ResponseMeta deletes(@PathVariable("id") long[] id){
        ResponseMeta responseMeta = new ResponseMeta();
        List<Long> ids = Longs.asList(id);
        try{
            sbModelService.deletes(ids);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"删除设备规格型号信息成功");
        }catch (Exception e){
            logger.info("删除设备规格型号失败:"+ExceptionUtils.getStackTrace(e));
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"删除设备规格型号信息失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }
        return responseMeta;
    }

    @GetMapping("/listBySbtypeThree")
    public ResponseMeta listBySbtypeThree(@RequestParam(defaultValue = "") String sbtypeThree,
                                          @RequestParam(defaultValue = "") String sbtypeTwo){
        ResponseMeta responseMeta = new ResponseMeta();

        try{
            if (StringUtils.isEmpty(sbtypeThree) && StringUtils.isEmpty(sbTypeTwo)) {
                responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备规格型号列表为空");
                responseMeta.setData(null);
                return responseMeta;
            }
            List<SbModel> list = sbModelService.listBySbtypeThree(sbtypeThree,sbTypeTwo);
            responseMeta.setMeta(Constants.RESPONSE_SUCCESS,"查询设备规格型号列表成功");
            responseMeta.setData(list);
        }catch (Exception e){
            logger.info("查询设备规格型号列表失败:"+ExceptionUtils.getStackTrace(e));
            responseMeta.setMeta(Constants.RESPONSE_EXCEPTION,"查询设备规格型号列表失败");
            responseMeta.setData(ExceptionUtils.getStackTrace(e));
        }

        return responseMeta;
    }
}
