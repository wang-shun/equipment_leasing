package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.DeptRole;
import com.yankuang.equipment.authority.model.Role;
import com.yankuang.equipment.authority.service.CodeService;
import com.yankuang.equipment.authority.service.DeptRoleService;
import com.yankuang.equipment.authority.service.RoleService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.web.dto.CodesDTO;
import com.yankuang.equipment.web.util.CodeUtil;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理模块.
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
    CodeService codeService;

    /**
     * 获取数据库id最大值生成.
     *
     * @return
     */
    private String getCode(String deptCode) {

        Map map = new HashMap();
        map.put("tableName", "el_role");
        Long idMax = codeService.findIdMax(map);
        idMax += 1 ;
        String code = CodeUtil.getFixedLengthCode(idMax.toString(), 4);
        return deptCode + code;

    }

    /**
     * 添加角色，关联部门.
     *
     * @param role
     * @return
     */
    @PostMapping
    public CommonResponse create(@RequestBody Role role) {
        String deptCode = role.getDeptCode();
        if (StringUtils.isEmpty(deptCode)) {
            return CommonResponse.errorMsg("部门deptCode不能为空");
        }
        String name = role.getName();
        if (StringUtils.isEmpty(name)) {
            return CommonResponse.errorMsg("角色name不能为空");
        }
        // 角色查重
        Role roleCheck = roleService.findByName(name);
        // 角色不存在
        if (StringUtils.isEmpty(roleCheck)) {
            // 添加,添加关联表
            String roleCode = getCode(deptCode);
            role.setCode(roleCode);
            role.setSorting((long) 1);
            role.setCreateBy("admin");
            role.setUpdateBy("admin");
            Boolean b = roleService.create(role);
            if (b) {
                //角色添加成功，添加部门关联
                DeptRole deptRole = new DeptRole();
                deptRole.setRoleCode(roleCode);
                deptRole.setDeptCode(deptCode);
                Boolean b1 = deptRoleService.create(deptRole);
                if (b1) {
                    return CommonResponse.build(200, "添加成功", null);
                }
                return CommonResponse.errorMsg("添加失败");
            }
        }
        // 角色存在，根据deptCode和roleCode 查重，存在返回成功，不存在添加返回成功
        String roleCheckCode = roleCheck.getCode();
        Map map = new HashMap();
        map.put("roleCode", roleCheckCode);
        map.put("deptCode", deptCode);
        // 关联表查重
        DeptRole deptRole = deptRoleService.findByDeptCodeAndRoleCode(map);
        // 不存在，添加关联
        if (StringUtils.isEmpty(deptRole)) {
            DeptRole deptRoleIn = new DeptRole();
            deptRoleIn.setRoleCode(roleCheckCode);
            deptRoleIn.setDeptCode(deptCode);
            Boolean b1 = deptRoleService.create(deptRoleIn);
            if (b1) {
                return CommonResponse.build(200, "角色添加成功", null);
            }
            return CommonResponse.errorMsg("角色添加失败");
        }
        // 存在返回
        return CommonResponse.errorMsg("角色已添加，请勿重复添加");
    }

    /**
     * 根据codes删除角色.
     *
     * @param codesDTO
     * @return
     */
    @DeleteMapping
    public CommonResponse delete(@RequestBody CodesDTO codesDTO) {
        List<String> codes = codesDTO.getCodes();
        // 删除角色部门关联表
        Boolean b = deptRoleService.deleteByRoleCodes(codes);
        if (!b) {
            return CommonResponse.errorMsg("删除角色部门关联表失败!");
        }
        // 删除角色
        b = roleService.delete(codes);
        if (!b) {
            return CommonResponse.errorMsg("删除角色失败!");
        }
        return CommonResponse.ok("删除成功!");
    }


    /**
     * @param role
     * @return
     * @method 根据角色code更新角色.
     * 通过角色code和部门新旧code更新关联表.
     */
    @PutMapping
    public CommonResponse update(@RequestBody Role role) {
        if (StringUtils.isEmpty(role.getCode())) {
            return CommonResponse.errorMsg("角色code不能为空");
        }
        if (StringUtils.isEmpty(role.getDeptCode())) {
            return CommonResponse.errorMsg("部门deptCode不能为空");
        }
        if (StringUtils.isEmpty(role.getDeptCodeNew())) {
            return CommonResponse.errorMsg("部门deptCodeNew不能为空");
        }
        String deptCode = role.getDeptCode();
        String deptCodeNew = role.getDeptCodeNew();
        // 检查关联关系是否更新
        Boolean b = false;
        if (!deptCode.equals(deptCodeNew)) {
            Map map = new HashMap();
            map.put("deptCode", deptCode);
            map.put("roleCode", role.getCode());
            // 删除旧的关联关系
            b = deptRoleService.deleteByDeptCodeAndRoleCode(map);
            if (!b) {
                return CommonResponse.errorTokenMsg("删除旧的关联关系失败");
            }
            DeptRole deptRoleIn = new DeptRole();
            deptRoleIn.setRoleCode(role.getCode());
            deptRoleIn.setDeptCode(deptCodeNew);
            // 添加新的关联关系
            b = deptRoleService.create(deptRoleIn);
            if (!b) {
                return CommonResponse.errorTokenMsg("添加新的关联关系失败");
            }
        }
        b = roleService.update(role);
        if (!b) {
            return CommonResponse.errorTokenMsg("更新失败");
        }
        return CommonResponse.build(200, "更新成功", null);
    }

    /**
     * 根据code查询角色.
     *
     * @param code
     * @return
     */
    @GetMapping(value = "/{code}")
    public CommonResponse findById(@PathVariable String code) {
        if (StringUtils.isEmpty(code)) {
            return CommonResponse.ok("角色id不能为空");
        }
        return CommonResponse.ok(roleService.findByCode(code));
    }


    /**
     * 角色分页查询.
     *
     * @param page
     * @param size
     * @param name
     * @return
     */
    @GetMapping
    public CommonResponse findByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "20") Integer size,
                                     @RequestParam String name) {
        Map map = new HashMap();
        map.put("name", name);
        return CommonResponse.ok(roleService.list(page, size, map));
    }

    /**
     * 根据部门code查询部门下所有角色.
     *
     * @param deptCode
     * @return
     */
    @GetMapping("/byDeptCode/{deptCode}")
    public CommonResponse findByDeptCode(@PathVariable String deptCode) {
        List<Role> roles = roleService.findByDeptCode(deptCode);
        return CommonResponse.ok(roles);
    }


}
