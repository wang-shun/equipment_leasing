package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.Dept;
import com.yankuang.equipment.authority.service.DeptService;
import com.yankuang.equipment.common.util.CommonResponse;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/depts")
public class DeptController {
    @RpcConsumer
    private DeptService deptService;

    @GetMapping(value = "/{id}")
    CommonResponse getById(@PathVariable Long id) {
        if(id == null || id == 0){
            return  CommonResponse.errorMsg("系统错误");
        }
        return CommonResponse.ok(deptService.getById(id));
    }

    @PutMapping(value = "/update")
    CommonResponse updateById(Dept dept){
        if(dept.getId() == null || dept.getId() == 0){
            return  CommonResponse.errorMsg("系统错误");
        }else if (dept.getName() == null || dept.getName().equals(" ")){
            return CommonResponse.errorMsg("部门名称不能为空");
        }else if (dept.getPcode() == null || dept.getPcode().equals(" ")){
            return CommonResponse.errorMsg("系统错误");
        }else if (dept.getLevel() == null){
            return  CommonResponse.errorMsg("系统错误");
        }else if (dept.getSorting() == null){
            return CommonResponse.errorMsg("系统错误");
        }else if (dept.getStatus() != 1 && dept.getStatus() != 2 && dept.getStatus() != 99){
            return CommonResponse.errorMsg("请选择状态");
        }else if (deptService.getByName(dept.getName()) != null){
            return CommonResponse.errorMsg("此权限名称已存在");
        }
        return CommonResponse.ok(deptService.update(dept));
    }

    @PostMapping(value = "/add")
    CommonResponse add(Dept dept){
        if (dept.getName() == null || dept.getName().equals(" ")){
            return CommonResponse.errorMsg("部门名称不能为空");
        }else if (dept.getPcode() == null || dept.getPcode().equals(" ")){
            return CommonResponse.errorMsg("系统错误");
        }else if (dept.getLevel() == null){
            return  CommonResponse.errorMsg("系统错误");
        }else if (dept.getSorting() == null){
            return CommonResponse.errorMsg("系统错误");
        }else if (dept.getStatus() != 1 && dept.getStatus() != 2 && dept.getStatus() != 99){
            return CommonResponse.errorMsg("请选择状态");
        }else if (deptService.getByName(dept.getName()) != null){
            return CommonResponse.errorMsg("此权限名称已存在");
        }
        return CommonResponse.ok(deptService.add(dept));
    }

    @PutMapping(value = "/del")
    CommonResponse del(Long id){
        if(id == null || id == 0){
            return  CommonResponse.errorMsg("系统错误");
        }
        return  CommonResponse.ok(deptService.del(id));
    }

    @PostMapping(value = "/find")
    CommonResponse getByName(String name){
        if (name == null || name.equals(" ")){
            return CommonResponse.errorMsg("部门名称不能为空");
        }
        return CommonResponse.ok(deptService.getByName(name));
    }

    @PostMapping(value = "/findAll")
    CommonResponse getAll( ){
        return CommonResponse.ok(deptService.getAll( ));
    }
}
