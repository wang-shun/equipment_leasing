package com.yankuang.equipment.web.restful;


import com.yankuang.equipment.authority.model.Organization;
import com.yankuang.equipment.authority.service.OrganizationService;

import com.yankuang.equipment.common.util.CommonResponse;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.*;
/**
 * @author boms
 * @createtime 2018/8/2
 */
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/orgs")
public class OrganizationController {

    @RpcConsumer
    private OrganizationService organizationService;

    /**
     * @method 通过id查询
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    CommonResponse getById(@PathVariable Long id) {
        return CommonResponse.ok(organizationService.getById(id));
    }

    /**
     * @method 添加
     * @param organization
     * @return
     */
    @PostMapping()
    CommonResponse add(Organization organization){
        if (organization.getName() == null || " ".equals(organization.getName())){
            return CommonResponse.errorMsg("请填写组织名称");
        }

        if (organizationService.getByName(organization.getName()) != null){
            return CommonResponse.errorMsg("此组织名称已存在");
        }

        if (organization.getLevel() == null){
            return CommonResponse.errorMsg("系统错误");
        }

        String pcode = (organization.getPcode() == null || " ".equals(organization.getPcode())) ?"1":organization.getPcode();
        organization.setPcode(pcode);

        int sort = organization.getSorting() == null ?1:organization.getSorting();
        organization.setSorting(sort);

        Long version = (organization.getVersion() == null || organization.getVersion() == 0) ?1:organization.getVersion();
        organization.setVersion(version);

        return CommonResponse.ok(organizationService.add(organization));
    }

    /**
     * @method 更新
     * @param organization
     * @return
     */
    @PutMapping
    CommonResponse update(Organization organization){
        if (organization.getId() == null || organization.getId() == 0){
            return CommonResponse.errorMsg("系统错误");
        }

        if (organization.getName() == null || " ".equals(organization.getName())){
            return CommonResponse.errorMsg("请填写组织名称");
        }

        if (organizationService.getByName(organization.getName()) != null){
            return CommonResponse.errorMsg("此组织名称已存在");
        }

        if (organization.getPcode() == null || " ".equals(organization.getPcode())){
            return CommonResponse.errorMsg("系统错误");
        }

        if (organization.getLevel() == null){
            return CommonResponse.errorMsg("系统错误");
        }

        if (organization.getSorting() == null){
            return CommonResponse.errorMsg("系统错误");
        }

        if (organization.getCode() == null || " ".equals(organization.getCode())){
            return CommonResponse.errorMsg("系统错误");
        }

        if (organization.getVersion() == null || organization.getVersion() == 0){
            return CommonResponse.errorMsg("版本号不能为空");
        }

        return CommonResponse.ok(organizationService.update(organization));
    }

    /**
     * @mehtod 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    CommonResponse del(@PathVariable Long id){
        if (id == null || id == 0){
            return CommonResponse.errorMsg("系统错误");
        }
        return CommonResponse.ok(organizationService.del(id));
    }

    /**
     * @method 通过名字查找
     * @param name
     * @return
     */
    @PostMapping
    CommonResponse getByName(String name){
        if (name == null || " ".equals(name)){
            return CommonResponse.errorMsg("组织名称不能为空");
        }
        return CommonResponse.ok(organizationService.getByName(name));
    }

    /**
     * @method 查询所有
     * @return
     */
    @GetMapping(value = "/findAll")
    CommonResponse getAll( ){
        return CommonResponse.ok(organizationService.getAll( ));
    }


    /**
     * @method 分页查询
     * @param page
     * @param limit
     * @param organization
     * @return
     */
    @PostMapping("/findpage/{page}/{limit}")
    CommonResponse getPage(@PathVariable int page,@PathVariable int limit,Organization organization){
        return CommonResponse.ok(organizationService.findpage(page,limit,organization));
    }
}
