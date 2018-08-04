package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.authority.service.DeptService;
import com.yankuang.equipment.common.util.CommonResponse;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
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

    /**
     * @method 通过id查询
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    CommonResponse getById(@PathVariable Long id) {
        if(id == null || id == 0){
            return  CommonResponse.errorMsg("系统错误");
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
            return  CommonResponse.errorMsg("系统错误");
        }
        if (dept.getName() == null || " ".equals(dept.getName())){
            return CommonResponse.errorMsg("部门名称不能为空");
        }
        if (dept.getPcode() == null || " ".equals(dept.getPcode())){
            return CommonResponse.errorMsg("系统错误");
        }
        if (dept.getLevel() == null){
            return  CommonResponse.errorMsg("系统错误");
        }
        if (dept.getSorting() == null){
            return CommonResponse.errorMsg("系统错误");
        }
        if (deptService.getByName(dept.getName()) != null){
            return CommonResponse.errorMsg("此权限名称已存在");
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
            return CommonResponse.errorMsg("部门名称不能为空");
        }

        if (deptService.getByName(dept.getName()) != null){
            return CommonResponse.errorMsg("此权限名称已存在");
        }

        if (dept.getLevel() == null){
            return  CommonResponse.errorMsg("系统错误");
        }

        String pcode = (dept.getPcode() == null || " ".equals(dept.getPcode())) ?"1":dept.getPcode();
        dept.setPcode(pcode);

        Long sort = dept.getSorting() == null ?1:dept.getSorting();
        dept.setSorting(sort);

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
            return  CommonResponse.errorMsg("系统错误");
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
            return CommonResponse.errorMsg("部门名称不能为空");
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
    @PostMapping("/findpage/{page}/{limit}")
    CommonResponse getPage(@PathVariable Integer page,@PathVariable Integer limit,@RequestBody Dept dept){
        return CommonResponse.ok(deptService.findpage(page == null ?0:page,limit == null ?0:limit,dept));
    }
}
