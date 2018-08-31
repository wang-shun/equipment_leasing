package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.DeptRole;
import com.yankuang.equipment.authority.model.Role;
import com.yankuang.equipment.authority.model.RoleAuthority;
import com.yankuang.equipment.authority.service.*;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.web.dto.CodesDTO;
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
    RoleAuthorityService roleAuthorityService;

    /**
     * 添加角色，关联部门.
     * @param jsonString
     * @return
     */
    @PostMapping
    public CommonResponse create(@RequestBody String jsonString){

        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorMsg("参数jsonString不能为空");
        }
        DeptRoleDTO deptRoleDTO = JsonUtils.jsonToPojo(jsonString, DeptRoleDTO.class);
        String deptCode = deptRoleDTO.getDeptCode();
        if (StringUtils.isEmpty(deptCode)) {
            return CommonResponse.errorMsg("部门或组织deptCode不能为空");
        }
        String roleName = deptRoleDTO.getName();
        if (StringUtils.isEmpty(roleName)) {
            return CommonResponse.errorMsg("角色name不能为空");
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
                deptRole.setRoleCode(role1.getCode());
                deptRole.setDeptCode(deptCode);
                Boolean b1 = deptRoleService.create(deptRole);
                if (b1) {
                    return CommonResponse.build(200, "角色添加成功", null);
                }
                return CommonResponse.errorMsg("角色添加失败");
            }
        }
        // 角色存在，根据name查询id,根据deptid和roleId 查重，存在返回成功，不存在添加返回成功
        String roleCheckCode = roleCheck.getCode();
        Map map = new HashMap();
        map.put("roleCode", roleCheckCode);
        map.put("deptCode", deptCode);
        // 关联表查重
        DeptRole deptRole = deptRoleService.selectByDeptIdAndRoleId(map);
        if (StringUtils.isEmpty(deptRole)) {
            DeptRole deptRole1 = new DeptRole();
            deptRole1.setRoleCode(roleCheckCode);
            deptRole1.setDeptCode(deptCode);
            Boolean b1 = deptRoleService.create(deptRole1);
            if (b1) {
                return CommonResponse.build(200, "角色添加成功", null);
            }
            return CommonResponse.errorMsg("角色添加失败");
        }
        return CommonResponse.errorMsg("角色已添加，请勿重复添加");
    }

    /**
     * 根据codes删除角色.
     * @param jsonString
     * @return
     */
    @DeleteMapping
    public CommonResponse delete(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorMsg("请选择要删除的数据");
        }
        CodesDTO codesDTO = JsonUtils.jsonToPojo(jsonString, CodesDTO.class);
        List<String> codes = codesDTO.getCodes();
        Boolean b = roleService.delete(codes);
        if (!b) {
            return CommonResponse.errorMsg("删除失败!");
        }
        return CommonResponse.ok("删除成功!");
    }


    /**
     * @method 通过id更新角色.
     * @param jsonString
     * @return
     */
    @PutMapping
    public CommonResponse update(@RequestBody String jsonString){
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
     * 根据code查询角色.
     * @param code
     * @return
     */
    @GetMapping(value = "/{code}")
    public CommonResponse findById(@PathVariable String code) {
        if (StringUtils.isEmpty(code)){
            return CommonResponse.ok("角色id不能为空");
        }
        return CommonResponse.ok(roleService.findByCode(code));
    }


    /**
     * 角色分页查询.
     * @param page
     * @param size
     * @param searchInput
     * @return
     */
    @GetMapping
    public CommonResponse findByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "20") Integer size,
                          @RequestParam String searchInput) {
        Map roleMap = new HashMap();
        return CommonResponse.ok(roleService.list(page, size, roleMap));
    }

    /**
     * 角色授权.
     * 权限ids
     * 角色id
     */
    @PostMapping("/roleAuthority")
    public CommonResponse createRoleAuthority(@RequestBody String jsonString) {

        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorMsg("参数jsonString不能为空");
        }
        RoleAuthorityDTO roleAuthorityDTO = JsonUtils.jsonToPojo(jsonString, RoleAuthorityDTO.class);
        Long roleId = roleAuthorityDTO.getRoleId();
        if (StringUtils.isEmpty(roleId)) {
            return CommonResponse.errorMsg("参数roleId不能为空");
        }
        List<Long> authorityIds = roleAuthorityDTO.getAuthorityIds();
        if (StringUtils.isEmpty(authorityIds)) {
            return CommonResponse.errorMsg("参数authorityIds不能为空");
        }
        // 遍历ids,根据角色roleId查和权限id查重角色和权限关联表
        for (Long authorityId : authorityIds) {
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

        return CommonResponse.build(200, "角色授权成功", null);
    }

    /**
     * @method
     * 根据deptId获取角色列表
     * 用户管理添加用户，角色下拉列表
     * @param deptId
     * @return
     */
    @GetMapping("/byDeptId")
    public CommonResponse findByDeptId(@RequestParam Long deptId){
        List<Role> roles = new ArrayList<Role>();
        List<DeptRole>  deptRoles = deptRoleService.findByDeptId(deptId);
        for (DeptRole deptRole : deptRoles){
            Role role = roleService.findById(deptRole.getRoleId());
            roles.add(role);
        }
        return CommonResponse.ok(roles);
    }
}
