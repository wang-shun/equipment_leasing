package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.Authority;
import com.yankuang.equipment.authority.service.AuthorityService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.UuidUtils;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
/**
 * @author boms
 * @createtime 2018/8/2
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/acls")
public class AuthorityController{
    @RpcConsumer
    private AuthorityService authorityService;

    /**
     * @author boms
     * @method 通过id查询
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    CommonResponse getById(@PathVariable Long id) {
        return CommonResponse.ok(authorityService.getById(id));
    }

    /**
     * @author boms
     * @method 修改
     * @param jsonString
     * @return
     */
    @PutMapping
    CommonResponse updateById(@RequestBody String jsonString){

        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorTokenMsg("参数不能为空");
        }
        Authority authority = JsonUtils.jsonToPojo(jsonString,Authority.class);

        if (authority.getId() == null || authority.getId() == 0){
            return CommonResponse.errorTokenMsg("系统错误");
        }

        return CommonResponse.ok(authorityService.update(authority));
    }

    /**
     * @author boms
     * @method 添加
     * @param authority
     * @return
     */

    @PostMapping("/add")
    CommonResponse add(@RequestBody Authority authority){
        if (" ".equals(authority.getName()) || authority.getName() == null){
            return CommonResponse.errorTokenMsg("用户不能为空");
        }

        if (authorityService.getByName(authority.getName()) != null){
            return CommonResponse.errorTokenMsg("此权限名称已存在");
        }

        String url = (authority.getUrl() == null || " ".equals(authority.getUrl())) ?"1":authority.getUrl();
        authority.setUrl(url);

        Long type = authority.getType() == null ?1:authority.getType();
        authority.setType(type);

        Long version = (authority.getVersion() == null || " ".equals(authority.getVersion()))?0:authority.getVersion();
        authority.setVersion(version);

        Long sort = authority.getSorting() == null ?0:authority.getSorting();
        authority.setSorting(sort);

        authority.setCode(UuidUtils.newUuid());
        authority.setCreateBy("admin");//TODO 由于用户功能暂未开发完，先写死，后期改
        authority.setUpdateBy("admin");

        return CommonResponse.ok(authorityService.add(authority));
    }

    /**
     * @author boms
     * @method 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    CommonResponse del(@PathVariable Long id){
        if (id == null || id == 0){
            return CommonResponse.errorTokenMsg("系统错误");
        }
        return CommonResponse.ok(authorityService.del(id));
    }

    /**
     * @author boms
     * @method 通过名字查询
     * @param name
     * @return
     */
    @GetMapping("/NameAuthority")
    CommonResponse getByName(@RequestParam String name){
        if (name == null || " ".equals(name)){
            return CommonResponse.errorMsg("权限名称不能为空");
        }
        return CommonResponse.ok(authorityService.getByName(name));
    }

    /**
     * @author boms
     * @method 查询所有
     * @return
     */
    @GetMapping(value = "/findAll")
    CommonResponse getAll( ){
        return CommonResponse.ok(authorityService.getAll());
    }

    /**
     * @author boms
     * @method 分页查询
     * @param page
     * @param limit
     * @return
     */

    @GetMapping("/pageing")
    CommonResponse getPage(@RequestParam int page,@RequestParam int limit){
        Authority authority = new Authority();
        return CommonResponse.ok(authorityService.findpage(page,limit,authority));
    }

    /**
     * @method 查找名字
     * @return
     */
    @GetMapping("/findName")
    CommonResponse getName(){
        return CommonResponse.ok(authorityService.findName());
    }
}
