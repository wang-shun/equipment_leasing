package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.authority.model.OrgDept;
import com.yankuang.equipment.authority.service.DeptService;
import com.yankuang.equipment.authority.service.OrgDeptService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.web.dto.DeptDTO;
import com.yankuang.equipment.web.dto.CodesDTO;
import com.yankuang.equipment.web.util.CodeUtil;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author boms
 * @createtime 2018/8/2
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/depts")
public class DeptController {
    @RpcConsumer
    DeptService deptService;

    @RpcConsumer
    OrgDeptService orgDeptService;

    /**
     * 添加部门,及组织部门关系.
     *
     * @param jsonString
     * @return
     */
    @PostMapping
    public CommonResponse create(@RequestBody String jsonString) {

        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorTokenMsg("参数不能为空");
        }
        DeptDTO dept = JsonUtils.jsonToPojo(jsonString, DeptDTO.class);
        String deptName = dept.getName();
        if (StringUtils.isEmpty(deptName)) {
            return CommonResponse.errorTokenMsg("部门名称不能为空");
        }
        Long orgId = dept.getOrgId();
        if (StringUtils.isEmpty(orgId)) {
            return CommonResponse.errorTokenMsg("组织orgId不能为空");
        }
        Dept dept1 = deptService.findByName(deptName);
        if (dept1 != null) {
            OrgDept orgDept = new OrgDept();
            orgDept.setDepartmentId(dept1.getId());
            orgDept.setOrganizationId(orgId);
            orgDept.setCode(CodeUtil.getCode());
            orgDept.setCreateBy("admin");
            orgDept.setUpdateBy("admin");
            Boolean b2 = orgDeptService.create(orgDept);
            if (!b2){
                return CommonResponse.errorMsg("添加组织部门关系失败");
            }
            return CommonResponse.ok("部门添加成功");
        }
        dept.setCode(CodeUtil.getCode());
        dept.setCreateBy("admin");
        dept.setUpdateBy("admin");
        Boolean b = deptService.create(dept);
        if (!b) {
            return CommonResponse.errorMsg("部门添加失败");
        }
        Dept dept2 = deptService.findByName(deptName);
        OrgDept orgDept = new OrgDept();
        orgDept.setDepartmentId(dept2.getId());
        orgDept.setOrganizationId(orgId);
        orgDept.setCode(CodeUtil.getCode());
        orgDept.setCreateBy("admin");
        orgDept.setUpdateBy("admin");
        Boolean b2 = orgDeptService.create(orgDept);
        if (!b2){
            return CommonResponse.errorMsg("添加组织部门关系失败");
        }
        return CommonResponse.ok("部门添加成功");
    }

    /**
     * 根据组织id和下属部门id,删除组织与下属部门关系
     * @param jsonString
     * @return
     */
    @DeleteMapping
    public CommonResponse delete(@RequestBody String jsonString) {
        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorMsg("参数不能为空");
        }
        CodesDTO codesDTO = JsonUtils.jsonToPojo(jsonString, CodesDTO.class);
        List<Long> deptIds = codesDTO.getIds();
        if (deptIds == null) {
            return CommonResponse.errorMsg("部门id列表不能为空");
        }
        Long orgId = codesDTO.getId();
        if (deptIds == null) {
            return CommonResponse.errorMsg("组织id不能为空");
        }
        Map map = new HashMap();
        map.put("orgId", orgId);
        map.put("deptIds", deptIds);
        boolean b = orgDeptService.deleteByOrgIdAndDeptId(map);
        if (!b) {
            return CommonResponse.errorTokenMsg("删除失败");
        }
        return CommonResponse.ok("删除成功");
    }

    /**
     * 根据id修改.
     *
     * @param jsonString
     * @return
     */
    @PutMapping
    public CommonResponse update(@RequestBody String jsonString) {
        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorTokenMsg("参数不能为空");
        }
        Dept dept = JsonUtils.jsonToPojo(jsonString, Dept.class);
        if (dept.getId() == null || dept.getId() == 0) {
            return CommonResponse.errorTokenMsg("系统错误");
        }
        if (dept.getName() == null || "".equals(dept.getName())) {
            return CommonResponse.errorTokenMsg("部门名称不能为空");
        }
        if (deptService.findByName(dept.getName()) != null) {
            return CommonResponse.errorTokenMsg("此部门名称已存在");
        }
        return CommonResponse.ok(deptService.update(dept));
    }

    /**
     * @param id
     * @return
     * @method 通过id查询
     */
    @GetMapping(value = "/{id}")
    public CommonResponse findById(@PathVariable Long id) {
        if (id == null || id == 0) {
            return CommonResponse.errorTokenMsg("系统错误");
        }
        return CommonResponse.ok(deptService.findById(id));
    }

    /**
     * @return
     * @method 查询所有
     */
    @GetMapping(value = "/all")
    public CommonResponse findAll() {
        return CommonResponse.ok(deptService.findAll());
    }

    /**
     * 条件分页查询.
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public CommonResponse findByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "20") Integer size,
                                     @RequestParam(value = "jsonString", defaultValue = "") String jsonString) {
        Map map = new HashMap();
        return CommonResponse.ok(deptService.findByPage(page, size, map));
    }


}
