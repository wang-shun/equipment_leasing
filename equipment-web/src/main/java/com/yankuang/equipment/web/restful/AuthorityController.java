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
        return CommonResponse.ok(authorityService.getById(id));
    }

    @PutMapping
    CommonResponse updateById(Authority authority){
        if (authority.getName().equals(" ") || authority.getName() == null){
            return CommonResponse.errorMsg("用户不能为空");
        }

        if (authorityService.getByName(authority.getName()) != null){
            return CommonResponse.errorMsg("此权限名称已存在");
        }

        if (authority.getId() == null || authority.getId() == 0){
            return CommonResponse.errorMsg("系统错误");
        }

        if (authority.getUrl() == null || authority.getUrl().equals(" ")){
            return CommonResponse.errorMsg("请求路径不能为空");
        }

        if (authority.getType() != 1 && authority.getType() != 2 && authority.getType() != 3){
            return CommonResponse.errorMsg("请选择类型");
        }

        if (authority.getSorting() == null){
            return CommonResponse.errorMsg("系统错误");
        }
        return CommonResponse.ok(authorityService.update(authority));
    }

    @PostMapping("/add")
    CommonResponse add(Authority authority){
        if (authority.getName().equals(" ") || authority.getName() == null){
            return CommonResponse.errorMsg("用户不能为空");
        }

        if (authorityService.getByName(authority.getName()) != null){
            return CommonResponse.errorMsg("此权限名称已存在");
        }

        String url = (authority.getUrl() == null || authority.getUrl().equals(" ")) ?authority.getUrl():" ";
        authority.setUrl(url);

        Long type = (authority.getType() != 1 && authority.getType() != 2 && authority.getType() != 3) ?authority.getType():1;
        authority.setType(type);

        Long sort = authority.getSorting() == null ?authority.getSorting():0;
        authority.setSorting(sort);

        return CommonResponse.ok(authorityService.add(authority));
    }
    @DeleteMapping
    CommonResponse del(Long id){
        return CommonResponse.ok(authorityService.del(id));
    }

    @PostMapping
    CommonResponse getByName(String name){
        if (name == null || name.equals(" ")){
            return CommonResponse.errorMsg("权限名称不能为空");
        }
        return CommonResponse.ok(authorityService.getByName(name));
    }

    @GetMapping(value = "/findAll")
    CommonResponse getAll( ){
        return CommonResponse.ok(authorityService.getAll());
    }
}
