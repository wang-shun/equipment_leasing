package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.authority.model.OrgDept;
import com.yankuang.equipment.authority.service.DeptService;
import com.yankuang.equipment.authority.service.OrgDeptService;
import com.yankuang.equipment.common.util.CommonResponse;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
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
     * @method 通过id查询
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    CommonResponse getById(@PathVariable Long id) {
        if(id == null || id == 0){
            return  CommonResponse.errorTokenMsg("系统错误");
        }
        return CommonResponse.ok(deptService.getById(id));
    }

    /**
     * @method 更新
     * @param dept
     * @return
     */
    @PutMapping
    CommonResponse updateById(@RequestBody Dept dept){
        if(dept.getId() == null || dept.getId() == 0){
            return  CommonResponse.errorTokenMsg("系统错误");
        }
        if (dept.getName() == null || " ".equals(dept.getName())){
            return CommonResponse.errorTokenMsg("部门名称不能为空");
        }
        if (deptService.getByName(dept.getName()) != null){
            return CommonResponse.errorTokenMsg("此权限名称已存在");
        }
        return CommonResponse.ok(deptService.update(dept));
    }

    /**
     * @method 添加
     * @param dept
     * @return
     */
    @PostMapping(value = "/add")
    CommonResponse add(@RequestBody Dept dept){

        if (dept.getName() == null || " ".equals(dept.getName())){
            return CommonResponse.errorTokenMsg("部门名称不能为空");
        }

        if (deptService.getByName(dept.getName()) != null){
            return CommonResponse.errorTokenMsg("此部门名称已存在");
        }

        Long level = dept.getLevel() == null ? 0:dept.getLevel();
        dept.setLevel(level);

        String pcode = (dept.getPcode() == null || " ".equals(dept.getPcode())) ?"1":dept.getPcode();
        dept.setPcode(pcode);

        Long sort = dept.getSorting() == null ?1:dept.getSorting();
        dept.setSorting(sort);

        Long version = dept.getVersion() == null ?1:dept.getVersion();
        dept.setVersion(version);

        return CommonResponse.ok(deptService.add(dept));
    }

    /**
     * @method 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    CommonResponse del(@PathVariable Long id){
        if(id == null || id == 0){
            return  CommonResponse.errorTokenMsg("系统错误");
        }
        return  CommonResponse.ok(deptService.del(id));
    }

    /**
     * @method 通过名字查询
     * @param name
     * @return
     */

    @PostMapping
    CommonResponse getByName(String name){
        if (name == null || " ".equals(name)){
            return CommonResponse.errorTokenMsg("部门名称不能为空");
        }
        return CommonResponse.ok(deptService.getByName(name));
    }

    /**
     * @method 查询所有
     * @return
     */

    @GetMapping(value = "/findAll")
    CommonResponse getAll( ){
        return CommonResponse.ok(deptService.getAll( ));
    }

    /**
     * @method 分页查询
     * @param page
     * @param limit
     * @param dept
     * @return
     */
    @GetMapping("/findpage/{page}/{limit}")
    CommonResponse getPage(@PathVariable int page,@PathVariable int limit,@RequestBody Dept dept){
        return CommonResponse.ok(deptService.findpage(page,limit,dept));
    }

    /**
     * @method 查找名字
     * @return
     */
    @GetMapping("/findName")
    CommonResponse getName(){
        return CommonResponse.ok(deptService.findName());
    }

    /**
     * @method 添加部门管理
     * @param organizationId
     * @param name
     * @return
     */
    @GetMapping("/findOrgNameAdd/{organizationId}/{name}")
    CommonResponse findOrgNameAdd( @PathVariable(value = "organizationId") Long organizationId,
                                   @PathVariable(value = "name") String name){
        Long departmentId;

        if (organizationId == null){
            return CommonResponse.errorTokenMsg("请选择组织名称");
        }

        if (name == null || "".equals(name)){
            return CommonResponse.errorTokenMsg("请填写部门名称");
        }

        Dept dept = new Dept();
        OrgDept orgDept = new OrgDept();

        orgDept.setOrganizationId(organizationId);

        if(deptService.getByName(name) ==  null){
            dept.setName(name);

            Long level = dept.getLevel() == null ? 0:dept.getLevel();
            dept.setLevel(level);

            String pcode = (dept.getPcode() == null || " ".equals(dept.getPcode())) ?"1":dept.getPcode();
            dept.setPcode(pcode);

            Long sort = dept.getSorting() == null ?1:dept.getSorting();
            dept.setSorting(sort);

            Long version = dept.getVersion() == null ?1:dept.getVersion();
            dept.setVersion(version);

            deptService.add(dept);
        }

        departmentId = deptService.getId("w");
        if (departmentId == null){
            return CommonResponse.errorTokenMsg("系统错误");
        }

        if (orgDeptService.selOrgDept(departmentId) != null){
            return CommonResponse.errorTokenMsg("已存在此部门");
        }

        orgDept.setDepartmentId(departmentId);
        orgDept.setUpdateBy("admin");//TODO 用户未完成暂写死
        orgDept.setCreateBy("admin");

        return CommonResponse.ok(orgDeptService.add(orgDept));
    }

    /**
     * @method 部门管理删除功能
     * @param id
     * @return
     */
    @DeleteMapping("/mapperdel/{id}")
    CommonResponse delDeptOrg(@PathVariable Long id){
        return CommonResponse.ok(orgDeptService.delById(id));
    }


    /**
     * @method 部门管理更新功能
     * @param organizationId
     * @param name
     * @return
     */
    @PutMapping("/mapperudt/{organizationId}/{name}")
    CommonResponse udtDeptOrg(@PathVariable(value = "organizationId") Long organizationId,
                              @PathVariable(value = "name") String name){
        Long departmentId;
        if (organizationId == null){
            return CommonResponse.errorTokenMsg("请选择组织名称");
        }

        if (name == null || "".equals(name)){
            return CommonResponse.errorTokenMsg("请填写部门名称");
        }
        Dept dept = new Dept();
        OrgDept orgDept = new OrgDept();

        orgDept.setOrganizationId(organizationId);

        if(deptService.getByName(name) ==  null){
            dept.setName(name);
            deptService.update(dept);
        }

        departmentId = deptService.getId(name);
        if (departmentId == null){
            return CommonResponse.errorTokenMsg("系统错误");
        }

        if (orgDeptService.selOrgDept(departmentId) != null){
            return CommonResponse.errorTokenMsg("已存在此部门");
        }
        orgDept.setDepartmentId(departmentId);
        return CommonResponse.ok(orgDeptService.udtOrgDept(orgDept));
    }

    /**
     * @method 分页查询
     * @param page
     * @param limit
     * @param orgDept
     * @return
     */
    @GetMapping("/findpageOrgDept/{page}/{limit}")
    CommonResponse getPageOrgDept(@PathVariable int page,@PathVariable int limit,@RequestBody OrgDept orgDept){
        return CommonResponse.ok(orgDeptService.findpage(page,limit,orgDept));
    }
}
