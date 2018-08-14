package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.equipment.model.ElOre;
import com.yankuang.equipment.equipment.service.OreService;
import io.netty.util.internal.StringUtil;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/ores")
public class OreController {
    @RpcConsumer
    OreService oreService;

    /**
     * @method 添加矿组织
     * @param jsonString
     * @return
     */
    @PostMapping
    public CommonResponse create(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString) == true){
            return CommonResponse.errorTokenMsg("参数不能为空");
        }
        //将json转化成对象
        ElOre elOre = JsonUtils.jsonToPojo(jsonString,ElOre.class);

        if (elOre.getName() == null || " ".equals(elOre)){
            return CommonResponse.errorTokenMsg("名字不能为空");
        }

        //判断是否有一下值没有赋默认值
        String code = (elOre.getCode() == null || " ".equals(elOre.getCode()))?"1":elOre.getCode();
        elOre.setCode(code);
        Long level = elOre.getLevel() == null?1:elOre.getLevel();
        elOre.setLevel(level);
        Long pId = elOre.getpId() == null ? 1:elOre.getLevel();
        elOre.setpId(pId);
        Long sorting = elOre.getSorting() == null ? 1:elOre.getSorting();
        elOre.setSorting(sorting);
        Byte status = elOre.getStatus() == null?1:elOre.getStatus();
        elOre.setStatus(status);
        Byte type = elOre.getType() == null?1:elOre.getType();
        elOre.setType(type);
        Long version = elOre.getVersion() == null?1:elOre.getVersion();
        elOre.setVersion(version);
        elOre.setUpdateBy("admin");//TODO 用户模块暂未开发完，先写死
        elOre.setCreateBy("admin");

        return CommonResponse.ok(oreService.create(elOre));
    }

    /**
     * @method 矿组织更新方法
     * @param jsonString
     * @return
     */
    @PutMapping
    public CommonResponse update(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorTokenMsg("参数不能为空");
        }

        ElOre elOre = JsonUtils.jsonToPojo(jsonString,ElOre.class);

        if (elOre.getId() == null){
            return CommonResponse.errorTokenMsg("更新id不能为空");
        }

        return CommonResponse.ok(oreService.update(elOre));
    }

    /**
     * @method 矿组织删除功能
     * @param jsonString
     * @return
     */
    @DeleteMapping
    public CommonResponse delete(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorTokenMsg("参数不能为空");
        }

        List<Long> ids = JsonUtils.jsonToList(jsonString,Long.class);

        for (Long id:ids){
            //判断矿类型
            if (oreService.findType(id) == (byte)1){
                List<Long> pids = oreService.getId(id);
                for (Long pid:pids){
                    if (oreService.deleteOre(pid) == false){
                        return CommonResponse.build(200,"删除失败",pid);
                    }
                    if (oreService.delete(pid) == false) {
                        return CommonResponse.build(200, "删除失败", pid);
                    }
                }
            }
            if (oreService.findType(id) == (byte)2){
                if (oreService.deleteOre(id) == false){
                    return CommonResponse.build(200,"删除失败",id);
                }
            }
            if (oreService.delete(id) == false) {
                return CommonResponse.build(200, "删除失败", id);
            }
        }

        return CommonResponse.ok();
    }

    /**
     * @method 通过id查找
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public CommonResponse findById(@PathVariable Long id){
        if (id == null){
            return CommonResponse.errorTokenMsg("id不能为空");
        }
        return CommonResponse.ok(oreService.findById(id));
    }

    /**
     * @method 通过p_id查询列表
     * @param id
     * @return
     */
    @GetMapping("/findByPId/{id}")
    public CommonResponse findByPId(@PathVariable Long id){
        if (id == null){
            return CommonResponse.errorTokenMsg("id不能为空");
        }

        List<ElOre> ElOres = oreService.findByPId(id);
        return CommonResponse.ok(ElOres);
    }
}
