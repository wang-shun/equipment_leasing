package com.yankuang.equipment.web.restful;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.authority.service.DeptService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.web.dto.CodesDTO;
import com.yankuang.equipment.web.dto.DeptTreeDTO;
import com.yankuang.equipment.web.util.CodeUtil;
import com.yankuang.equipment.web.util.DeptTreeUtils;
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
@RequestMapping("/v1/depts")
public class DeptController {
    @RpcConsumer
    DeptService deptService;

    /**
     * 添加部门.
     * @param dept
     * @return
     */
    @PostMapping
    public CommonResponse create(@RequestBody Dept dept) {
        String name = dept.getName();
        if (StringUtils.isEmpty(name)) {
            return CommonResponse.errorMsg("部门name不能为空");
        }
        Long sorting = dept.getSorting();
        if (StringUtils.isEmpty(sorting)) {
            return CommonResponse.errorMsg("部门sorting不能为空");
        }
        Byte type = dept.getType();
        if (StringUtils.isEmpty(type)) {
            return CommonResponse.errorMsg("部门type不能为空");
        }
        String pcode = dept.getPcode();
        if (StringUtils.isEmpty(pcode)) {
            return CommonResponse.errorMsg("部门pcode不能为空");
        }

        Dept dept1 = deptService.findByName(name);
        if (dept1 != null) {
            return CommonResponse.ok("部门已存在，请勿重复添加");
        }
        dept.setCode(CodeUtil.getCode());
        dept.setCreateBy("admin");
        dept.setUpdateBy("admin");
        dept.setProjectCode("sb001");
        Boolean b = deptService.create(dept);
        if (!b) {
            return CommonResponse.errorMsg("添加失败");
        }
        return CommonResponse.ok("添加成功");
    }

    /**
     * 根据codes删除部门，单个或批量
     * @param jsonString
     * @return
     */
    @DeleteMapping
    public CommonResponse delete(@RequestBody String jsonString) {
        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorMsg("请选择要删除的数据..");
        }
        CodesDTO codesDTO = JsonUtils.jsonToPojo(jsonString, CodesDTO.class);
        List<String> codes = codesDTO.getCodes();
        Boolean b = deptService.delete(codes);
        if (!b) {
            return CommonResponse.errorMsg("删除失败");
        }
        return CommonResponse.ok("删除成功");
    }

    /**
     * 根据code修改.
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
        if (StringUtils.isEmpty(dept.getCode())) {
            return CommonResponse.errorMsg("部门code不能为空");
        }
        dept.setUpdateBy("admin");
        Boolean b = deptService.update(dept);
        if (b) {
            return CommonResponse.build(200, "更新成功", null);
        }
        return CommonResponse.errorMsg("更新失败");
    }

    /**
     * 根据code查询
     * @param code
     * @return
     */
    @GetMapping(value = "/{code}")
    public CommonResponse findByCode(@PathVariable String code) {
        if (StringUtils.isEmpty(code)) {
            return CommonResponse.errorTokenMsg("code不能为空");
        }
        return CommonResponse.ok(deptService.findByCode(code));
    }

    /**
     * @return
     * @method 查询部门树
     *  type=1 菜单
     */
    @GetMapping(value = "/tree")
    public CommonResponse findAll() {

        List<Dept> depts = deptService.findAll();
        List<DeptTreeDTO> trees = new ArrayList<>();
        DeptTreeDTO tree = null;
        for (Dept dept : depts) {
            tree = new DeptTreeDTO();
            tree.setCode(dept.getCode());
            tree.setPcode(dept.getPcode());
            tree.setName(dept.getName());
            tree.setType(dept.getType());
            tree.setRemark(dept.getRemark());
            tree.setAddress(dept.getAddress());
            tree.setSorting(dept.getSorting());
            tree.setCreateBy(dept.getCreateBy());
            tree.setCreateAt(dept.getCreateAt());
            trees.add(tree);
        }
        DeptTreeUtils treeUtils = new DeptTreeUtils();
        List<DeptTreeDTO> list = treeUtils.menuList(trees);
        return CommonResponse.ok(list);
    }

    /**
     * 部门条件分页查询.
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public CommonResponse findByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "20") Integer size,
                                     @RequestParam String name) {
        Map map = new HashMap();
        map.put("name", name);
        PageInfo<Dept> deptPageInfo = deptService.findByPage(page, size, map);
        return CommonResponse.ok(deptPageInfo);
    }


}
