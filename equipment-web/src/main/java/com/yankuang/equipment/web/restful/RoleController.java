package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.DeptRole;
import com.yankuang.equipment.authority.model.Role;
import com.yankuang.equipment.authority.service.DeptRoleService;
import com.yankuang.equipment.authority.service.RoleService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.web.dto.DeptRoleDTO;
import com.yankuang.equipment.web.util.CodeUtil;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
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

    /**
     * @method 通过id查询
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    CommonResponse getById(@PathVariable Long id) {
        if (StringUtils.isEmpty(id)){
            return CommonResponse.ok("角色id不能为空");
        }
        return CommonResponse.ok(roleService.getById(id));
    }

    /**
     * @method 通过id更新
     * @param jsonString
     * @return
     */
    @PutMapping
    CommonResponse updateById(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorMsg("参数jsonString不能为空");
        }
        Role role = JsonUtils.jsonToPojo(jsonString, Role.class);
        if (StringUtils.isEmpty(role.getId())){
            return CommonResponse.errorMsg("角色id不能为空");
        }
        Boolean b = roleService.update(role);
        if (b) {
            return CommonResponse.build(200, "更新成功", null);
        }
        return CommonResponse.errorTokenMsg("更新失败");

    }

    /**
     * @method 添加
     * @param jsonString
     * @return
     */
    @PostMapping()
    CommonResponse add(@RequestBody String jsonString){

        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorMsg("参数jsonString不能为空");
        }
        DeptRoleDTO deptRoleDTO = JsonUtils.jsonToPojo(jsonString, DeptRoleDTO.class);
        Long deptId = deptRoleDTO.getDeptId();
        if (StringUtils.isEmpty(deptId)) {
            return CommonResponse.errorMsg("参数deptId不能为空");
        }
        String roleName = deptRoleDTO.getName();
        if (StringUtils.isEmpty(roleName)){
            return CommonResponse.errorMsg("角色名不能为空");
        }
        // 角色查重
        Role roleCheck = roleService.findByName(roleName);

        if (StringUtils.isEmpty(roleCheck)){
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
            Boolean b = roleService.add(role);
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
        Map map =  new HashMap();
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
            return  CommonResponse.errorMsg("角色添加失败");
        }
        return CommonResponse.errorMsg("角色已添加，请勿重复添加");
    }

    /**
     * @method 删除
     * @param id
     * @return
     */

    @DeleteMapping("/{id}")
    CommonResponse delete(@PathVariable Long id){
        if (StringUtils.isEmpty(id)){
            return CommonResponse.ok("角色id不能为空");
        }
        return CommonResponse.ok(roleService.delete(id));
    }

    /**
     * @method 分页查询
     * @param offset
     * @param limit
     * @param searchInput
     * @return
     */
    @GetMapping
    CommonResponse paging(@RequestParam(value = "page", defaultValue = "1") Integer offset,
                          @RequestParam(value = "size", defaultValue = "20")Integer limit,
                          @RequestParam String searchInput){
        Role role = new Role();
        return CommonResponse.ok(roleService.paging(offset, limit, role));
    }

    /**
     * @method 查找名字
     * @return
     */
    @GetMapping("/findName")
    CommonResponse getName(){
        return CommonResponse.ok(roleService.findName());
    }

}
