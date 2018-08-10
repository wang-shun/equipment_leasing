package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.Authority;
import com.yankuang.equipment.authority.model.AuthorityGroup;
import com.yankuang.equipment.authority.model.AuthorityGroupMapping;
import com.yankuang.equipment.authority.service.AuthorityGroupMappingService;
import com.yankuang.equipment.authority.service.AuthorityGroupService;
import com.yankuang.equipment.authority.service.AuthorityService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.web.dto.AuthorityGroupDTO;
import com.yankuang.equipment.web.dto.TreeDTO;
import com.yankuang.equipment.web.util.CodeUtil;
import com.yankuang.equipment.web.util.TreeUtils;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author boms
 * @createtime 2018/8/2
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/acls")
public class AuthorityController {
    @RpcConsumer
    AuthorityService authorityService;
    @RpcConsumer
    AuthorityGroupService authorityGroupService;
    @RpcConsumer
    AuthorityGroupMappingService authorityGroupMappingService;

    /**
     * @author boms
     * @method 通过id查询
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public CommonResponse getById(@PathVariable Long id) {
        return CommonResponse.ok(authorityService.getById(id));
    }

    /**
     * @author boms
     * @method 修改
     * @param jsonString
     * @return
     */
    @PutMapping
    public CommonResponse updateById(@RequestBody String jsonString){

        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorTokenMsg("参数不能为空");
        }
        Authority authority = JsonUtils.jsonToPojo(jsonString, Authority.class);
        if (StringUtils.isEmpty(authority.getId())) {
            return CommonResponse.errorTokenMsg("权限id不能为空");
        }
        authority.setUpdateBy("admin");
        return CommonResponse.ok(authorityService.update(authority));
    }

    /**
     * @param jsonString
     * @return
     * @method 添加权限组
     */
    @PostMapping
    @Transactional
    CommonResponse add(@RequestBody String jsonString) {
        if (jsonString == null || "".equals(jsonString)) {
            return CommonResponse.errorMsg("参数不能为空");
        }
        AuthorityGroupDTO authorityGroupDTO = JsonUtils.jsonToPojo(jsonString, AuthorityGroupDTO.class);
        String groupName = authorityGroupDTO.getName();
        if (StringUtils.isEmpty(groupName)) {
            return CommonResponse.errorTokenMsg("权限名称不能为空");
        }
        List<Long> ids = authorityGroupDTO.getIds();
        // 根据权限组名称查重
        AuthorityGroup authorityGroup = authorityGroupService.findByName(groupName);
        // 不存在 ，添加权限组，查询权限组id，查重，添加权限组id和权限idLists关联表(遍历)
        if (StringUtils.isEmpty(authorityGroup)) {
            AuthorityGroup authorityGroup1 = new AuthorityGroup();
            authorityGroup1.setCode(CodeUtil.getCode());
            authorityGroup1.setName(groupName);
            authorityGroup1.setCreateBy("admin");
            authorityGroup1.setUpdateBy("admin");
            //添加组
            Boolean b = authorityGroupService.create(authorityGroup1);
            if (b) {
                Long groupId = authorityGroupService.findByName(groupName).getId();
                Boolean flag = this.checkMapping(ids, groupId);
                if (!flag){
                    return CommonResponse.errorMsg("权限组和权限关联表添加失败");
                }
                return CommonResponse.build(200, "权限添加成功", null);
            }
            return CommonResponse.errorMsg("权限组添加失败");
        }
        // 存在，查出groupId,根据组id和权限id遍历查重，存在继续，不存在添加
        Long groupId1 = authorityGroupService.findByName(groupName).getId();
        Boolean flag = this.checkMapping(ids, groupId1);
        if (!flag) {
            return CommonResponse.errorMsg("权限组和权限关联表添加失败");
        }
        return CommonResponse.build(200, "权限添加成功", null);
    }

    /**
     * 根据权限组id和权限id列表查重，添加关联
     * @param ids
     * @param groupId
     */
    private Boolean checkMapping(List<Long> ids, Long groupId) {
        for (Long id : ids) {
            Map param = new HashMap();
            param.put("groupId", groupId);
            param.put("authorityId", id);
            // 双id关联表查重，存在继续，不存在添加
            AuthorityGroupMapping authorityGroupMapping =
                    authorityGroupMappingService.selectByAuthorityIdAndGroupId(param);
            if (StringUtils.isEmpty(authorityGroupMapping)) {
                // 组和权限关联表添加
                AuthorityGroupMapping authorityGroupMapping1 = new AuthorityGroupMapping();
                authorityGroupMapping1.setGroupId(groupId);
                authorityGroupMapping1.setAuthorityId(id);
                authorityGroupMapping1.setCreateBy("admin");
                authorityGroupMapping1.setUpdateBy("admin");
                // 其他参数
                Boolean b = authorityGroupMappingService.create(authorityGroupMapping1);
                if (!b){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public CommonResponse delete(@PathVariable Long id){
        if (id == null || id == 0){
            return CommonResponse.errorTokenMsg("系统错误");
        }
        return CommonResponse.ok(authorityService.delete(id));
    }

    @DeleteMapping("/dels")
    public CommonResponse deleteByIds(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorTokenMsg("选择要删除的数据");
        }
       List<Long> ids = JsonUtils.jsonToList(jsonString,Long.class);
        for (Long id: ids){
            if (authorityService.delete(id) == false){
                return CommonResponse.build(200,"删除失败",ids);
            }
        }
        return CommonResponse.ok();
    }

    /**
     * @author boms
     * @method 删除
     */
    @DeleteMapping("/{id}")
    CommonResponse deleteById(@PathVariable Long id) {
        return CommonResponse.ok(authorityService.delete(id));
    }

    /**
     * @param name
     * @return
     * @author boms
     * @method 通过名字查询
     */
    @GetMapping
    CommonResponse getByName(@RequestParam String name) {
        if (StringUtils.isEmpty(name)) {
            return CommonResponse.errorMsg("权限名称不能为空");
        }
        return CommonResponse.ok(authorityService.findByName(name));
    }

    /**
     * @return
     * @method 查询权限菜单(type = 1)
     */
    @GetMapping(value = "/all")
    CommonResponse getAll() {

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
        List<Object> list = treeUtils.menuList(trees);
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
