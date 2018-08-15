package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.AuthorityGroupMapping;
import com.yankuang.equipment.authority.model.DeptRole;
import com.yankuang.equipment.authority.model.Role;
import com.yankuang.equipment.authority.model.RoleAuthority;
import com.yankuang.equipment.authority.service.*;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.web.dto.DeptRoleDTO;
import com.yankuang.equipment.web.dto.RoleAuthorityDTO;
import com.yankuang.equipment.web.util.CodeUtil;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/v1/roles")
public class RoleController {

    @RpcConsumer
    RoleService roleService;

    @RpcConsumer
    DeptRoleService deptRoleService;

    @RpcConsumer
    AuthorityGroupMappingService authorityGroupMappingService;

    @RpcConsumer
    RoleAuthorityService roleAuthorityService;

    /**
     * @method 通过id查询
     * @param id
     * @return
     * @method 通过id查询
     */
    @GetMapping(value = "/{id}")
    public CommonResponse getById(@PathVariable Long id) {
        if (StringUtils.isEmpty(id)){
            return CommonResponse.ok("角色id不能为空");
        }
        return CommonResponse.ok(roleService.findById(id));
    }

    /**
     * @method 通过id更新
     * @param jsonString
     * @return
     */
    @PutMapping
    public CommonResponse updateById(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorMsg("参数jsonString不能为空");
        }
        Role role = JsonUtils.jsonToPojo(jsonString, Role.class);
        if (StringUtils.isEmpty(role.getId())) {
            return CommonResponse.errorMsg("角色id不能为空");
        }
        Boolean b = roleService.update(role);
        if (b) {
            return CommonResponse.build(200, "更新成功", null);
        }
        return CommonResponse.errorTokenMsg("更新失败");

    }

    /**
     * @param jsonString
     * @return
     * @method 添加角色，关联部门
     */
    @PostMapping()
    public CommonResponse add(@RequestBody String jsonString){

        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorMsg("参数jsonString不能为空");
        }
        DeptRoleDTO deptRoleDTO = JsonUtils.jsonToPojo(jsonString, DeptRoleDTO.class);
        Long deptId = deptRoleDTO.getDeptId();
        if (StringUtils.isEmpty(deptId)) {
            return CommonResponse.errorMsg("参数deptId不能为空");
        }
        String roleName = deptRoleDTO.getName();
        if (StringUtils.isEmpty(roleName)) {
            return CommonResponse.errorMsg("角色名不能为空");
        }
        // 角色查重
        Role roleCheck = roleService.findByName(roleName);

        if (StringUtils.isEmpty(roleCheck)) {
            // 角色不存在，添加，根据name查询id,添加关联表
            Role role = new Role();
            role.setCode(CodeUtil.getCode());
            role.setName(roleName);
            role.setPcode("0");
            role.setLevel((long) 1);
            role.setType((long) 2);
            role.setSorting((long) 1);
            role.setCreateBy("admin");
            role.setUpdateBy("admin");
            Boolean b = roleService.create(role);
            if (b) {
                Role role1 = roleService.findByName(roleName);
                DeptRole deptRole = new DeptRole();
                deptRole.setRoleId(role1.getId());
                deptRole.setDepartmentId(deptId);
                deptRole.setCreateBy("admin");
                deptRole.setUpdateBy("admin");
                Boolean b1 = deptRoleService.create(deptRole);
                if (b1) {
                    return CommonResponse.build(200, "角色添加成功", null);
                }
                return CommonResponse.errorMsg("角色添加失败");
            }
        }
        // 角色存在，根据name查询id,根据deptid和roleId 查重，存在返回成功，不存在添加返回成功
        Long roleCheckId = roleCheck.getId();
        Map map = new HashMap();
        map.put("roleId", roleCheckId);
        map.put("departmentId", deptId);
        // 关联表查重
        DeptRole deptRole = deptRoleService.selectByDeptIdAndRoleId(map);
        if (StringUtils.isEmpty(deptRole)) {
            DeptRole deptRole1 = new DeptRole();
            deptRole1.setRoleId(roleCheckId);
            deptRole1.setDepartmentId(deptId);
            deptRole1.setCreateBy("admin");
            deptRole1.setUpdateBy("admin");
            Boolean b1 = deptRoleService.create(deptRole1);
            if (b1) {
                return CommonResponse.build(200, "角色添加成功", null);
            }
            return CommonResponse.errorMsg("角色添加失败");
        }
        return CommonResponse.errorMsg("角色已添加，请勿重复添加");
    }

    /**
     * @param jsonString
     * @return
     * @method 删除
     */
    @DeleteMapping("/dels")
    public CommonResponse delete(@RequestBody String jsonString){
        if(StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorMsg("参数不能为空");
        }
        List<Long> ids = JsonUtils.jsonToList(jsonString,Long.class);
        for (Long id: ids){
            boolean idB = roleService.delete(id);
            if (idB == false){
                return CommonResponse.errorTokenMsg("删除失败");
            }
        }
        return  CommonResponse.ok();
    }

    /**
     * @param page
     * @param size
     * @param searchInput
     * @return
     * @method 分页查询
     */
    @GetMapping
    public CommonResponse findListByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "20") Integer size,
                          @RequestParam String searchInput) {
        Map roleMap = new HashMap();
        return CommonResponse.ok(roleService.list(page, size, roleMap));
    }

    /**
     * 角色授权，通过权限组groupIds，关联权限ids
     * 1.遍历组groupIds
     * 1.1 根据组id查询权限ids
     * 1.2 遍历权限ids
     * 1.2.1 根据权限id和角色id查重 角色和权限关联表
     * 1.2.2 不存在就新增，存在不操作
     */
    @PostMapping("/roleAuthority")
    public CommonResponse createRoleAuthority(@RequestBody String jsonString) {

        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorMsg("参数jsonString不能为空");
        }
        RoleAuthorityDTO roleAuthorityDTO = JsonUtils.jsonToPojo(jsonString, RoleAuthorityDTO.class);
        Long roleId = roleAuthorityDTO.getRoleId();
        List<Long> groupIds = roleAuthorityDTO.getGroupIds();
        if (StringUtils.isEmpty(roleId) || StringUtils.isEmpty(groupIds)) {
            return CommonResponse.errorMsg("参数roleId和groupIds不能为空");
        }
        for (Long groupId : groupIds) {
            // 根据组id查组和权限关联表，权限ids
            List<AuthorityGroupMapping> authorityGroupMappings =
                    authorityGroupMappingService.findByGroupId(groupId);
            // 遍历ids,根据角色roleId查和权限id查重角色和权限关联表
            for (AuthorityGroupMapping authorityGroupMapping : authorityGroupMappings) {
                Long authorityId = authorityGroupMapping.getAuthorityId();
                Map map = new HashMap();
                map.put("roleId", roleId);
                map.put("authorityId", authorityId);
                RoleAuthority roleAuthority = roleAuthorityService.findByRoleIdAndAuthorityId(map);
                if (StringUtils.isEmpty(roleAuthority)) {
                    RoleAuthority roleAuthority1 = new RoleAuthority();
                    roleAuthority1.setAuthorityId(authorityId);
                    roleAuthority1.setRoleId(roleId);
                    roleAuthority1.setCreateBy("admin");
                    roleAuthority1.setUpdateBy("admin");
                    Boolean b = roleAuthorityService.create(roleAuthority1);
                    if (!b) {
                        return CommonResponse.errorMsg("添加角色权限关联失败");
                    }
                }
            }
        }

        return CommonResponse.build(200, "添加成功", null);
    }

    /**
     * @method
     * 用户管理根据deptId获取角色列表
     * @param deptId
     * @return
     */
    @GetMapping("/findRoles")
    public CommonResponse findRoles(@RequestParam Long deptId){
        List<Role> deptList = new ArrayList<Role>();
        List<Long> roleIds = deptRoleService.findRoleId(deptId);
        for (Long roleId:roleIds){
            deptList.add(roleService.findById(roleId));
        }
        return CommonResponse.ok(deptList);
    }
}
