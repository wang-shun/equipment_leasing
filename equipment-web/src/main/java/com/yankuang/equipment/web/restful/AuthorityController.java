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
        if (" ".equals(authority.getName()) || authority.getName() == null){
            return CommonResponse.errorMsg("用户不能为空");
        }

        if (authorityService.getByName(authority.getName()) != null){
            return CommonResponse.errorMsg("此权限名称已存在");
        }

        if (authority.getId() == null || authority.getId() == 0){
            return CommonResponse.errorMsg("系统错误");
        }

        if (authority.getUrl() == null || " ".equals(authority.getUrl())){
            return CommonResponse.errorMsg("请求路径不能为空");
        }

        if (authority.getType() == null){
            return CommonResponse.errorMsg("请选择类型");
        }

        if (authority.getSorting() == null){
            return CommonResponse.errorMsg("系统错误");
        }
        return CommonResponse.ok(authorityService.update(authority));
    }

    @PostMapping("/add")
    CommonResponse add(Authority authority){
        if (" ".equals(authority.getName()) || authority.getName() == null){
            return CommonResponse.errorMsg("用户不能为空");
        }

        if (authorityService.getByName(authority.getName()) != null){
            return CommonResponse.errorMsg("此权限名称已存在");
        }

        String url = (authority.getUrl() == null || " ".equals(authority.getUrl())) ?"1":authority.getUrl();
        authority.setUrl(url);

        Long type = authority.getType() == null ?1:authority.getType();
        authority.setType(type);

        Long version = (authority.getVersion() == null || " ".equals(authority.getVersion()))?0:authority.getVersion();
        authority.setVersion(version);

        Long sort = authority.getSorting() == null ?0:authority.getSorting();
        authority.setSorting(sort);

        return CommonResponse.ok(authorityService.add(authority));
    }
    @DeleteMapping
    CommonResponse del(Long id){
        return CommonResponse.ok(authorityService.del(id));
    }

    @PostMapping
    CommonResponse getByName(String name){
        if (name == null || " ".equals(name)){
            return CommonResponse.errorMsg("权限名称不能为空");
        }
        return CommonResponse.ok(authorityService.getByName(name));
    }

    @GetMapping(value = "/findAll")
    CommonResponse getAll( ){
        return CommonResponse.ok(authorityService.getAll());
    }

    @PostMapping("/findpage/{page}/{limit}")
    CommonResponse getPage(@PathVariable int page,@PathVariable int limit,Authority authority){
        return CommonResponse.ok(authorityService.findpage(page,limit,authority));
    }

}
