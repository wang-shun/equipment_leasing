package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.Authority;
import com.yankuang.equipment.authority.service.AuthorityService;
import com.yankuang.equipment.common.util.CommonResponse;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/acls")
public class AuthorityController{
    @RpcConsumer
    private AuthorityService authorityService;

    @GetMapping(value = "/{id}")
    CommonResponse getById(@PathVariable Long id) {
        if (id == null || id == 0){
            return CommonResponse.errorMsg("系统错误");
        }
        return CommonResponse.ok(authorityService.getById(id));
    }

    @PutMapping
    CommonResponse updateById(Authority authority){
        if (authority.getName().equals(" ") || authority.getName() == null){
            return CommonResponse.errorMsg("用户不能为空");
        }else if (authority.getUrl() == null || authority.getUrl().equals(" ")){
            return CommonResponse.errorMsg("请求路径不能为空");
        }else if (authority.getType() != 1 && authority.getType() != 2 && authority.getType() != 3){
            return CommonResponse.errorMsg("请选择类型");
        }else if (authority.getSorting() == null){
            return CommonResponse.errorMsg("系统错误");
        }else if (authority.getId() == null || authority.getId() == 0){
            return CommonResponse.errorMsg("系统错误");
        }else if (authorityService.getByName(authority.getName()) != null){
            return CommonResponse.errorMsg("此权限名称已存在");
        }
        return CommonResponse.ok(authorityService.update(authority));
    }

    @PostMapping("/add")
    CommonResponse add(Authority authority){
        if (authority.getName().equals(" ") || authority.getName() == null){
            return CommonResponse.errorMsg("用户不能为空");
        }else if (authority.getUrl() == null || authority.getUrl().equals(" ")){
            return CommonResponse.errorMsg("请求路径不能为空");
        }else if (authority.getType() != 1 && authority.getType() != 2 && authority.getType() != 3){
            return CommonResponse.errorMsg("请选择类型");
        }else if (authority.getSorting() == null){
            return CommonResponse.errorMsg("系统错误");
        }else if (authority.getStatus() != 1 && authority.getStatus() != 2 && authority.getStatus() != 3){
            return CommonResponse.errorMsg("请选择状态");
        }else if (authorityService.getByName(authority.getName()) != null){
            return CommonResponse.errorMsg("此权限名称已存在");
        }
        return CommonResponse.ok(authorityService.add(authority));
    }
    @DeleteMapping
    CommonResponse del(Long id){
        if (id == null || id == 0){
            return  CommonResponse.errorMsg("系统错误");
        }
        return CommonResponse.ok(authorityService.del(id));
    }

    @PostMapping
    CommonResponse getByName(String name){
        if (name == null || name.equals(" ")){
            return CommonResponse.errorMsg("权限名称不能为空");
        }
        return CommonResponse.ok(authorityService.getByName(name));
    }

    @PostMapping(value = "/findAll")
    CommonResponse getAll( ){
        return CommonResponse.ok(authorityService.getAll());
    }
}
