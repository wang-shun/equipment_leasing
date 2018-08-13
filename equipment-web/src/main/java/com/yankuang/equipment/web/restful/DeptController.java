package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.authority.model.OrgDepartment;
import com.yankuang.equipment.authority.model.OrgDept;
import com.yankuang.equipment.authority.service.DeptOrganizationService;
import com.yankuang.equipment.authority.service.DeptService;
import com.yankuang.equipment.authority.service.OrgDeptService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.StringUtils;
import com.yankuang.equipment.common.util.UuidUtils;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @RpcConsumer
    private DeptOrganizationService deptOrganizationService;

    /**
     * @method 通过id查询
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public CommonResponse getById(@PathVariable Long id) {
        if(id == null || id == 0){
            return  CommonResponse.errorTokenMsg("系统错误");
        }
        return CommonResponse.ok(deptService.getById(id));
    }

    /**
     * @method 更新
     * @param jsonString
     * @return
     */
    @PutMapping("/update")
    public CommonResponse updateById(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorTokenMsg("参数不能为空");
        }
        Dept dept = JsonUtils.jsonToPojo(jsonString,Dept.class);
        if(dept.getId() == null || dept.getId() == 0){
            return  CommonResponse.errorTokenMsg("系统错误");
        }
        if (dept.getName() == null || " ".equals(dept.getName())){
            return CommonResponse.errorTokenMsg("部门名称不能为空");
        }
        if (deptService.getByName(dept.getName()) != null){
            return CommonResponse.errorTokenMsg("此部门名称已存在");
        }
        return CommonResponse.ok(deptService.update(dept));
    }

    /**
     * @method 添加
     * @param jsonString
     * @return
     */
    @PostMapping(value = "/add")
    public CommonResponse add(@RequestBody String jsonString){

        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorTokenMsg("参数不能为空");
        }
        Dept dept = JsonUtils.jsonToPojo(jsonString,Dept.class);

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

        dept.setCode(UuidUtils.newUuid());
        dept.setCreateBy("admin");//TODO 由于用户功能暂未开发完，先写死，后期改
        dept.setUpdateBy("admin");

        return CommonResponse.ok(deptService.add(dept));
    }

    /**
     * @method 删除
     * @param jsonString
     * @return
     */
    @DeleteMapping("/dels")
    public CommonResponse del(@RequestBody String jsonString){
        if(StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorTokenMsg("没有查询的id");
        }
        List<Long> ids = JsonUtils.jsonToList(jsonString,Long.class);
        for (Long id: ids){
            boolean idB = deptService.del(id);
            if (idB == false){
                return CommonResponse.errorTokenMsg("删除失败");
            }
        }
        return  CommonResponse.ok();
    }

    /**
     * @method 通过名字查询
     * @param name
     * @return
     */

    @GetMapping
    public CommonResponse getByName(@RequestParam String name){
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
    public CommonResponse getAll( ){
        return CommonResponse.ok(deptService.getAll( ));
    }

    /**
     * @method 分页查询
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/findpage")
    public CommonResponse getPage(@RequestParam int page,@RequestParam int limit){
        Dept dept = new Dept();
        return CommonResponse.ok(deptService.findpage(page,limit,dept));
    }

    /**
     * @method 查找名字
     * @return
     */
    @GetMapping("/findName")
    public CommonResponse getName(){
        return CommonResponse.ok(deptService.findName());
    }

    /**
     * @method 添加部门管理
     * @param organizationId
     * @param name
     * @return
     */
    @PostMapping("/findOrgNameAdd")
    @Transactional
    public CommonResponse findOrgNameAdd( @RequestParam Long organizationId,
                                   @RequestParam String name){
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

            dept.setCreateBy("admin");

            dept.setUpdateBy("admin");

            deptService.add(dept);
        }

        departmentId = deptService.getId(name);
        if (departmentId == null){
            return CommonResponse.errorTokenMsg("系统错误");
        }

        orgDept.setDepartmentId(departmentId);

        if (orgDeptService.findOrgDept(orgDept) != 0){
            return CommonResponse.errorTokenMsg("已存在此部门");
        }

        orgDept.setUpdateBy("admin");//TODO 用户未完成暂写死
        orgDept.setCreateBy("admin");

        return CommonResponse.ok(orgDeptService.add(orgDept));
    }

    /**
     * @method 部门管理删除功能
     * @param jsonString
     * @return
     */
    @DeleteMapping("/mapperdel/dels")
    public CommonResponse delDeptOrg(@RequestBody String jsonString){
        if(StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorTokenMsg("没有查询的id");
        }
        List<Long> ids = JsonUtils.jsonToList(jsonString,Long.class);
        for (Long id: ids){
            boolean idB = orgDeptService.delById(id);
            if (idB == false){
                return CommonResponse.errorTokenMsg("删除失败");
            }
        }
        return  CommonResponse.ok();
    }


    /**
     * @method 部门管理更新功能
     * @param organizationId
     * @param name
     * @return
     */
    @PutMapping("/orgDepts")
    public CommonResponse udtDeptOrg(@RequestParam Long organizationId,
                              @RequestParam String name,
                              @RequestParam Long id){
        if (id == null){
            return CommonResponse.errorTokenMsg("系统错误");
        }

        if (organizationId == null){
            return CommonResponse.errorTokenMsg("请选择组织号");
        }

        if (name == null || " ".equals(name)){
            return CommonResponse.errorTokenMsg("请填写部门名称");
        }

        Dept dept = new Dept();
        OrgDept orgDept;

        Long departmentId = deptService.getId(name);

        if (departmentId == null){
            orgDept = orgDeptService.selOrgDept(id);
            dept.setId(orgDept.getDepartmentId());
            dept.setName(name);
            deptService.update(dept);
        }else{
            orgDept = new OrgDept();
            orgDept.setDepartmentId(departmentId);
        }

        if (orgDept.getOrganizationId() != organizationId){
            orgDept.setOrganizationId(organizationId);
        }

        if (orgDeptService.findOrgDept(orgDept) != 0){
            return CommonResponse.errorTokenMsg("已存在此部门");
        }

        orgDept.setId(id);
        return CommonResponse.ok(orgDeptService.udtOrgDept(orgDept));
    }

    /**
     * @method 分页查询
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/findpageOrgDept")
    public CommonResponse getPageOrgDept(@RequestParam int page,@RequestParam int limit ){
        Integer startPage;
        Integer endPage;
        if (page <= 0 || limit <= 0 ){
            return CommonResponse.errorTokenMsg("输入的页号要大于0");
        }
        startPage = limit * (page - 1);
        endPage = limit * page;
        OrgDepartment orgDepartment = new OrgDepartment();
        orgDepartment.setStartPage(startPage);
        orgDepartment.setEndPage(endPage);
        return CommonResponse.ok(deptOrganizationService.deptOrgAll(orgDepartment));
    }

    /**
     * @method 查找部门列表
     * @param orgsId
     * @return
     */
    @GetMapping("/findDepts")
    public CommonResponse findDept(@RequestParam Long orgsId){
        List<Dept> deptList = new ArrayList<Dept>();
        List<Long> deptIds = orgDeptService.findDeptId(orgsId);
        for (Long deptId:deptIds){
            deptList.add(deptService.findDept(deptId));
        }
        return CommonResponse.ok(deptList);
    }
}
