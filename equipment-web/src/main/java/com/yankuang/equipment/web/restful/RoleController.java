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
    private RoleService roleService;

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
        Role role = new Role();
        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorMsg("参数jsonString不能为空");
        }
        DeptRoleDTO deptRoleDTO = JsonUtils.jsonToPojo(jsonString, DeptRoleDTO.class);
        if (StringUtils.isEmpty(deptRoleDTO.getDeptId())) {
            return CommonResponse.errorMsg("参数deptId不能为空");
        }
        if (StringUtils.isEmpty(deptRoleDTO.getName())){
            return CommonResponse.errorMsg("角色名不能为空");
        }
        // 角色查重
        Role role1 = roleService.findByName(deptRoleDTO.getName());
        DeptRole deptRole = new DeptRole();
        if (StringUtils.isEmpty(role1)){
            // 角色不存在，添加，根据name查询id,添加关联表
            role.setCode(CodeUtil.getCode());
            role.setName(deptRoleDTO.getName());
            role.setPcode("0");
            role.setLevel((long) 1);
            role.setType((long) 2);
            role.setSorting((long) 1);
            role.setCreateBy("admin");
            role.setUpdateBy("admin");
            Boolean b = roleService.add(role);
            if (b) {
                role = roleService.findByName(deptRoleDTO.getName());
                deptRole.setRoleId(role.getId());
                deptRole.setDepartmentId(deptRoleDTO.getDeptId());
                deptRole.setCreateBy("admin");
                deptRole.setUpdateBy("admin");
                Boolean b1 = deptRoleService.create(deptRole);
                if (b1) {
                    CommonResponse.build(200, "角色添加成功", null);
                }
                CommonResponse.errorMsg("角色添加失败");
            }
        }
        // 角色存在，根据name查询id,根据deptid和roleId 查重，存在返回成功，不存在添加返回成功
        Map map =  new HashMap();
        map.put("roleId", role1.getId());
        map.put("departmentId", deptRoleDTO.getDeptId());
        deptRole = deptRoleService.selectByDeptIdAndRoleId(map);
        if (StringUtils.isEmpty(deptRole)) {
            deptRole.setRoleId(role1.getId());
            deptRole.setDepartmentId(deptRoleDTO.getDeptId());
            deptRole.setCreateBy("admin");
            deptRole.setUpdateBy("admin");
            Boolean b1 = deptRoleService.create(deptRole);
            if (b1) {
                CommonResponse.build(200, "角色添加成功", null);
            }
            CommonResponse.errorMsg("角色添加失败");
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

//    /**
//     * @method 通过名字查询
//     * @param name
//     * @return
//     */
//    @GetMapping(value = "/name")
//    CommonResponse getByName(String name){
//        if (name == null || " ".equals(name)){
//            return CommonResponse.errorMsg("角色名称不能为空");
//        }
//        return CommonResponse.ok(roleService.findByName(name));
//    }


    /**
     * @method 分页查询
     * @param page
     * @param limit
     * @param role
     * @return
     */
    @PostMapping("/findpage/{page}/{limit}")
    CommonResponse getPage(@PathVariable int page,@PathVariable int limit,@RequestBody Role role){
        return CommonResponse.ok(roleService.paging(page,limit,role));
    }

}
