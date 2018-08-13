package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.equipment.model.ElOre;
import com.yankuang.equipment.equipment.service.OreService;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
