package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.Authority;
import com.yankuang.equipment.authority.service.AuthorityService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.web.dto.TreeDTO;
import com.yankuang.equipment.web.util.TreeUtils;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author boms
 * @createtime 2018/8/2
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/acls")
public class AuthorityController{
    @RpcConsumer
    AuthorityService authorityService;

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
     * @method 根据id修改权限
     * @param jsonString
     * @return
     */
    @PutMapping
    CommonResponse updateById(@RequestBody String jsonString){
        if (jsonString == null || "".equals(jsonString)) {
            return CommonResponse.errorMsg("参数不能为空");
        }
        Authority authority = JsonUtils.jsonToPojo(jsonString, Authority.class);
        if (StringUtils.isEmpty(authority.getId())){
            return CommonResponse.errorTokenMsg("权限id不能为空");
        }
        authority.setUpdateBy("admin");
        return CommonResponse.ok(authorityService.update(authority));
    }

    /**
     * @author boms
     * @method 添加
     * @param jsonString
     * @return
     */
    @PostMapping
    CommonResponse add(@RequestBody String jsonString){
        if (jsonString == null || "".equals(jsonString)) {
            return CommonResponse.errorMsg("参数不能为空");
        }
        Authority authority = JsonUtils.jsonToPojo(jsonString, Authority.class);
        if (StringUtils.isEmpty(authority.getName())){
            return CommonResponse.errorTokenMsg("权限名称不能为空");
        }
        // 根据权限组名称查重
        // 不存在 ，添加权限组，查询权限组id，添加权限组id和权限idLists关联表(遍历)
        // 存在，根据组id和权限id查重，存在继续，不存在添加
        // 返回成功失败

        if (authorityService.findByName(authority.getName()) != null){
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

        return CommonResponse.ok(authorityService.add(authority));
    }

    /**
     * @author boms
     * @method 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    CommonResponse deleteById(@PathVariable Long id){
        return CommonResponse.ok(authorityService.delete(id));
    }

    /**
     * @author boms
     * @method 通过名字查询
     * @param name
     * @return
     */
    @GetMapping
    CommonResponse getByName(@RequestParam String name){
        if (StringUtils.isEmpty(name)){
            return CommonResponse.errorMsg("权限名称不能为空");
        }
        return CommonResponse.ok(authorityService.findByName(name));
    }

    /**
     * @method 查询权限菜单(type = 1)
     * @return
     */
    @GetMapping(value = "/all")
    CommonResponse getAll(){

        List<Authority> authorities = authorityService.findAll();
        List<TreeDTO> trees = new ArrayList<>();
        for (Authority authority : authorities) {
            TreeDTO tree = new TreeDTO();
            tree.setId(authority.getId());
            tree.setpId(authority.getpId());
            tree.setName(authority.getName());
            trees.add(tree);
        }
        TreeUtils treeUtils = new TreeUtils();
        List<Object> list  = treeUtils.menuList(trees);
        return CommonResponse.ok(list);
    }

//    /**
//     * @author boms
//     * @method 分页查询
//     * @param page
//     * @param size
//     * @return
//     */
//    @GetMapping
//    CommonResponse paging(@RequestParam(value = "page", defaultValue = "1") Integer page,
//                          @RequestParam(value = "size", defaultValue = "20")Integer size,
//                          @RequestParam String searchInput){
//        Authority authority = new Authority();
//        return CommonResponse.ok(authorityService.paging(page,size,authority));
//    }

}
