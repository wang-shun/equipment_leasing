package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.authority.service.DeptService;
import com.yankuang.equipment.authority.service.OrgDeptService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.common.util.UuidUtils;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
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
    private DeptService deptService;

    @RpcConsumer
    private OrgDeptService orgDeptService;

    /**
     * 添加部门.
     *
     * @param jsonString
     * @return
     */
    @PostMapping
    public CommonResponse create(@RequestBody String jsonString) {

        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorTokenMsg("参数不能为空");
        }
        Dept dept = JsonUtils.jsonToPojo(jsonString, Dept.class);

        if (dept.getName() == null || " ".equals(dept.getName())) {
            return CommonResponse.errorTokenMsg("部门名称不能为空");
        }

        if (deptService.findByName(dept.getName()) != null) {
            return CommonResponse.errorTokenMsg("此部门名称已存在");
        }

        Long level = dept.getLevel() == null ? 0 : dept.getLevel();
        dept.setLevel(level);

        String pcode = (dept.getPcode() == null || " ".equals(dept.getPcode())) ? "1" : dept.getPcode();
        dept.setPcode(pcode);

        Long sort = dept.getSorting() == null ? 1 : dept.getSorting();
        dept.setSorting(sort);

        Long version = dept.getVersion() == null ? 1 : dept.getVersion();
        dept.setVersion(version);

        dept.setCode(UuidUtils.newUuid());
        //TODO 由于用户功能暂未开发完，先写死，后期改
        dept.setCreateBy("admin");
        dept.setUpdateBy("admin");

        return CommonResponse.ok(deptService.create(dept));
    }

    /**
     * 根据id删除.
     *
     * @param jsonString
     * @return
     */
    @DeleteMapping
    public CommonResponse delete(@RequestBody String jsonString) {
        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorTokenMsg("没有查询的id");
        }
        List<Long> ids = JsonUtils.jsonToList(jsonString, Long.class);
        for (Long id : ids) {
            boolean idB = deptService.delete(id);
            if (idB == false) {
                return CommonResponse.errorTokenMsg("删除失败");
            }
        }
        return CommonResponse.ok();
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
        if (dept.getName() == null || " ".equals(dept.getName())) {
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
                                     @RequestParam String jsonString) {
        Map map = new HashMap();
        return CommonResponse.ok(deptService.findByPage(page, size, map));
    }


}
