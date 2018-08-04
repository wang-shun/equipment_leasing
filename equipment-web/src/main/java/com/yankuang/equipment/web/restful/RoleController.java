package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.Role;
import com.yankuang.equipment.authority.service.RolService;
import com.yankuang.equipment.common.util.CommonResponse;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/rols")
public class RoleController {
    @RpcConsumer
    private RolService rolService;

    @GetMapping(value = "/{id}")
    CommonResponse getById(@PathVariable Long id) {
        if (id == null || id ==0){
            return CommonResponse.ok("系统错误");
        }
        return CommonResponse.ok(rolService.getById(id));
    }

    @PutMapping(value = "/update")
    CommonResponse updateById(Role role){
        if (role.getId() == null || role.getId() == 0){
            return CommonResponse.errorMsg("系统错误");
        }
        if (role.getName() == null || role.getName().equals(" ")){
            return CommonResponse.errorMsg("角色不能为空");
        }
        if (rolService.getByName(role.getName()) != null){
            return CommonResponse.errorMsg("此角色名称已存在");
        }
        if (role.getPcode() == null || role.getPcode().equals(" ")){
            return CommonResponse.errorMsg("系统错误");
        }
        if (role.getType() != 1 && role.getType() != 2){
            return CommonResponse.errorMsg("类型不能为空");
        }
        if (role.getSorting() == null){
            return CommonResponse.errorMsg("系统错误");
        }
        if (role.getLevel() == null){
            return CommonResponse.errorMsg("系统错误");
        }
        return CommonResponse.ok(rolService.update(role));
    }

    @PostMapping(value = "/add")
    CommonResponse add(Role role){
        if (role.getName() == null || role.getName().equals(" ")){
            return CommonResponse.errorMsg("角色不能为空");
        }

        if (rolService.getByName(role.getName()) != null){
            return CommonResponse.errorMsg("此角色名称已存在");
        }

        String pcode = (role.getPcode() == null || role.getPcode().equals(" ")) ?role.getPcode():"0";
        role.setPcode(pcode);

        Long type = (role.getType() != 1 && role.getType() != 2) ?role.getType():1;
        role.setType(type);

        Long sort = role.getSorting() == null ?role.getSorting():1;
        role.setSorting(sort);

        Long level = role.getLevel() == null ?role.getLevel():0;
        role.setLevel(level);

        return CommonResponse.ok(rolService.add(role));
    }

    @PutMapping(value = "/del")
    CommonResponse del(Long id){
        if (id == null || id ==0){
            return CommonResponse.ok("系统错误");
        }
        return CommonResponse.ok(rolService.del(id));
    }

    @GetMapping(value = "/sel")
    CommonResponse getAll(List<Long> ids){
        return  CommonResponse.ok(rolService.getAll(ids));
    }

    @PostMapping(value = "/find")
    CommonResponse getByName(String name){
        if (name == null || name.equals(" ")){
            return CommonResponse.errorMsg("角色名称不能为空");
        }
        return CommonResponse.ok(rolService.getByName(name));
    }

    @PostMapping(value = "/findAll")
    CommonResponse getAll(  ){
        return CommonResponse.ok(rolService.getAll( ));
    }
}
