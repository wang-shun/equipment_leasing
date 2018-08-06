package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.Role;
import com.yankuang.equipment.authority.service.RoleService;
import com.yankuang.equipment.common.util.CommonResponse;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.*;

/**
 * @author boms
 * @createtime 2018/8/2
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/rols")
public class RoleController {
    @RpcConsumer
    private RoleService roleService;


    /**
     * @method 通过id查询
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    CommonResponse getById(@PathVariable Long id) {
        if (id == null || id ==0){
            return CommonResponse.ok("系统错误");
        }
        return CommonResponse.ok(roleService.getById(id));
    }

    /**
     * @method 更新
     * @param role
     * @return
     */
    @PutMapping
    CommonResponse updateById(@RequestBody Role role){
        if (role.getId() == null || role.getId() == 0){
            return CommonResponse.errorMsg("系统错误");
        }
        if (role.getName() == null || " ".equals(role.getName())){
            return CommonResponse.errorMsg("角色不能为空");
        }
        if (roleService.getByName(role.getName()) != null){
            return CommonResponse.errorMsg("此角色名称已存在");
        }
        if (role.getPcode() == null || " ".equals(role.getPcode())){
            return CommonResponse.errorMsg("系统错误");
        }
        if (role.getType() == null){
            return CommonResponse.errorMsg("类型不能为空");
        }
        if (role.getSorting() == null){
            return CommonResponse.errorMsg("系统错误");
        }
        if (role.getLevel() == null){
            return CommonResponse.errorMsg("系统错误");
        }
        return CommonResponse.ok(roleService.update(role));
    }

    /**
     * @method 添加
     * @param role
     * @return
     */
    @PostMapping(value = "/add")
    CommonResponse add(@RequestBody Role role){
        if (role.getName() == null || " ".equals(role.getName())){
            return CommonResponse.errorMsg("角色不能为空");
        }

        if (roleService.getByName(role.getName()) != null){
            return CommonResponse.errorMsg("此角色名称已存在");
        }

        String pcode = (role.getPcode() == null || " ".equals(role.getPcode())) ?"0":role.getPcode();
        role.setPcode(pcode);

        Long type = (role.getType() == null) ?1:role.getType();
        role.setType(type);

        Long sort = role.getSorting() == null ?1:role.getSorting();
        role.setSorting(sort);

        Long level = role.getLevel() == null ?0:role.getLevel();
        role.setLevel(level);

        return CommonResponse.ok(roleService.add(role));
    }

    /**
     * @method 删除
     * @param id
     * @return
     */

    @DeleteMapping("/{id}")
    CommonResponse del(@PathVariable Long id){
        if (id == null || id ==0){
            return CommonResponse.ok("系统错误");
        }
        return CommonResponse.ok(roleService.del(id));
    }

    /**
     * @method 通过名字查询
     * @param name
     * @return
     */
    @PostMapping(value = "/find")
    CommonResponse getByName(String name){
        if (name == null || " ".equals(name)){
            return CommonResponse.errorMsg("角色名称不能为空");
        }
        return CommonResponse.ok(roleService.getByName(name));
    }

    /**
     * @method 查询所有
     * @return
     */
    @PostMapping(value = "/findAll")
    CommonResponse getAll(  ){
        return CommonResponse.ok(roleService.getAll( ));
    }

    /**
     * @method 分页查询
     * @param page
     * @param limit
     * @param role
     * @return
     */
    @PostMapping("/findpage/{page}/{limit}")
    CommonResponse getPage(@PathVariable int page,@PathVariable int limit,@RequestBody Role role){
        return CommonResponse.ok(roleService.findpage(page,limit,role));
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
