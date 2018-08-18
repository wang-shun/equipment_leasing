package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.equipment.model.*;
import com.yankuang.equipment.equipment.service.*;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/v1/elUses")
public class ElUseController {
    @RpcConsumer
    ElUseService elUseService;

    @RpcConsumer
    ElUseItemService elUseItemService;

    @RpcConsumer
    SbEquipmentTService sbEquipmentTService;

    @RpcConsumer
    ElPlanUseService elPlanUseService;

    /**
     * @method 领用申请添加功能
     * @param jsonString
     * @return
     */
    @PostMapping
    CommonResponse create(@RequestBody String jsonString){

        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorMsg("传入的参数不能为空");
        }

        ElUse elUse = JsonUtils.jsonToPojo(jsonString,ElUse.class);

        if (elUseService.create(elUse) == false){
            return CommonResponse.build(500,"创建失败",null);
        }

        return CommonResponse.ok();
    }

    /**
     * @method 设备领用更新功能
     * @param jsonString
     * @return
     */
    @PutMapping
    CommonResponse update(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorMsg("参数不能为空");
        }
        ElUse elUse = JsonUtils.jsonToPojo(jsonString,ElUse.class);
        if (elUse == null){
            return CommonResponse.errorMsg("不存在该记录");
        }
        if (elUse.getId() == null){
            return CommonResponse.errorMsg("id不能为空");
        }
        elUse.setUpdateAt(new Date());
        if (elUseService.update(elUse) == false){
            return CommonResponse.build(500,"更新失败",null);
        }

        List<ElUseItem> elUseItems = elUse.getElUseItems();

        if (elUseItems == null){
            return CommonResponse.ok();
        }
        //更新领用明细表
        for (ElUseItem elUseItem:elUseItems){
            if (elUseItem.getItemId() == null){
                return CommonResponse.build(520,"id不能为空",null);
            }

            if (elUseItemService.update(elUseItem) == false){
                return CommonResponse.build(500,"更新失败",null);
            }
        }
        return CommonResponse.ok();
    }

    /**
     * @method 通过id查询设备领用记录
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    CommonResponse select(@PathVariable Long id){
        if (id == null){
            return CommonResponse.errorMsg("id不能为空");
        }

        return CommonResponse.ok(elUseService.select(id));
    }

    /**
     * @method 通过id批量删除领用记录
     * @param jsonString
     * @return
     */
    @DeleteMapping
    CommonResponse delete(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorMsg("参数不能为空");
        }

        List<Long> ids = JsonUtils.jsonToList(jsonString,Long.class);

        for(Long id:ids) {
            if (elUseService.delete(id) == false){
                return CommonResponse.build(500,"删除失败",null);
            }
            if(elUseItemService.delete(id) == false){
                return CommonResponse.build(520,"明细表删除失败",null);
            }
        }

        return CommonResponse.ok();
    }

    /**
     * @method 分页查询领用记录
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    CommonResponse findListByPage(@RequestParam Integer page,
                                  @RequestParam Integer size){
        Map elUseMap = new HashMap();
        return CommonResponse.ok(elUseService.list(page, size, elUseMap));
    }

    /**
     * @method 领用明细表分页查询功能
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/elUseItem")
    CommonResponse findElUseItemByPage(@RequestParam Integer page,
                                       @RequestParam Integer size){
        Map elUseItemMap = new HashMap();
        return CommonResponse.ok(elUseItemService.list(page,size,elUseItemMap));
    }

    /**
     * @method 领用明细表通过id查询功能
     * @param itemId
     * @return
     */
    @GetMapping("/findById/{itemId}")
    CommonResponse findByItemId(@PathVariable Long itemId){
        if (itemId == null){
            return CommonResponse.errorMsg("id不能为空");
        }
        ElUseItem elUseItem = elUseItemService.findById(itemId);
        if (elUseItem == null){
            return CommonResponse.errorMsg("该记录不存在");
        }
        SbEquipmentT sbEquipmentT = sbEquipmentTService.findById(elUseItem.getEquipmentId());
        elUseItem.setSbEquipmentT(sbEquipmentT);
        return CommonResponse.ok(elUseItem);
    }

    /**
     * @method 删除设备明细表记录功能
     * @param jsonString
     * @return
     */
    @DeleteMapping("/deleteById")
    CommonResponse deleteById(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorMsg("参数不能为空");
        }

        List<Long> itemIds = JsonUtils.jsonToList(jsonString,Long.class);
        for (Long itemId:itemIds){
            if (elUseItemService.deleteById(itemId) == false){
                return CommonResponse.errorMsg("删除失败");
            }
            ElUseItem elUseItem = elUseItemService.findById(itemId);
        }

        return CommonResponse.ok("删除成功");
    }

    /**
     * @method 提交审核
     * @param id
     * @return
     */
    @PutMapping("/status/{id}")
    CommonResponse open(@PathVariable Long id){
        if (id == null){
            return CommonResponse.errorMsg("id不能为空");
        }
        //在提交前判断一下是否领用的设备，如果没有就不允许提交
        List<ElUseItem> elUseItems = elUseItemService.findByUseId(id);
        if (elUseItems == null){
            return CommonResponse.errorMsg("请设置领用设备");
        }
        for (ElUseItem elUseItem:elUseItems){
            if (elUseItem.getItemId() <= 0){
                return CommonResponse.errorMsg("请设置领用设备");
            }
        }
        return CommonResponse.ok(elUseService.open(id));
    }

    @PutMapping("/successStatus")
    @Transactional
    CommonResponse successStatus(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorMsg("参数不能为空");
        }

        ElUse elUse = JsonUtils.jsonToPojo(jsonString,ElUse.class);

        if (elUse.getId() == null){
            return CommonResponse.errorMsg("id不能为空");
        }
        elUse.setApproveBy(1L);//TODO 待redis开发完，先写死
        elUse.setApproveAt(new Date());
        elUse.setUpdateAt(new Date());
        elUse.setStatus("4");
        List<ElUseItem> elUseItems = elUseItemService.findByUseId(elUse.getId());
        ElPlanUse elPlanUse = new ElPlanUse();
        //由于同意租用所以将租用的设备状态更改成在租状态
        for (ElUseItem elUseItem:elUseItems){
            elPlanUse.setUpdateBy(1L);//TODO 待redis开发完，先写死
            elPlanUse.setUpdateAt(new Date());
            elPlanUse.setId(elUseItem.getPlanUseId());
            elPlanUse.setStatus("2");//更改设备状态
            if(elPlanUseService.update(elPlanUse)==null){
                return CommonResponse.build(500,"更新失败",null);
            }
        }
        if(elUseService.update(elUse)==false){
            return CommonResponse.build(500,"领用明细更新失败",null);
        }
        return  CommonResponse.ok();
    }
}
