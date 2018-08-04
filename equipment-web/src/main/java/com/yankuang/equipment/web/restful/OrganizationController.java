package com.yankuang.equipment.web.restful;


import com.yankuang.equipment.authority.model.Organization;
import com.yankuang.equipment.authority.service.OrganizationService;

import com.yankuang.equipment.common.util.CommonResponse;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/orgs")
public class OrganizationController {

    @RpcConsumer
    private OrganizationService organizationService;

    @GetMapping("/{id}")
    CommonResponse getById(@PathVariable Long id) {
        if (id == null || id == 0){
            return CommonResponse.errorMsg("系统错误");
        }
        return CommonResponse.ok(organizationService.getById(id));
    }

    @PostMapping()
    CommonResponse add(Organization organization){
        if (organization.getName() == null || organization.getName().equals(" ")){
            return CommonResponse.errorMsg("请填写组织名称");
        }else if (organization.getPcode() == null || organization.getPcode().equals(" ")){
            return CommonResponse.errorMsg("系统错误");
        }else if (organization.getLevel() == null){
            return CommonResponse.errorMsg("系统错误");
        }else if (organization.getSorting() == null){
            return CommonResponse.errorMsg("系统错误");
        }else if (organization.getCode() == null || organization.getCode().equals(" ")){
            return CommonResponse.errorMsg("系统错误");
        }else if (organization.getVersion() == null || organization.getVersion() == 0){
            return CommonResponse.errorMsg("版本号不能为空");
        }else if (organizationService.getByName(organization.getName()) != null){
            return CommonResponse.errorMsg("此组织名称已存在");
        }
        return CommonResponse.ok(organizationService.add(organization));
    }

    @PutMapping()
    CommonResponse update(Organization organization){
        if (organization.getId() == null || organization.getId() == 0){
            return CommonResponse.errorMsg("系统错误");
        }else if (organization.getName() == null || organization.getName().equals(" ")){
            return CommonResponse.errorMsg("请填写组织名称");
        }else if (organization.getPcode() == null || organization.getPcode().equals(" ")){
            return CommonResponse.errorMsg("系统错误");
        }else if (organization.getLevel() == null){
            return CommonResponse.errorMsg("系统错误");
        }else if (organization.getSorting() == null){
            return CommonResponse.errorMsg("系统错误");
        }else if (organization.getCode() == null || organization.getCode().equals(" ")){
            return CommonResponse.errorMsg("系统错误");
        }else if (organization.getVersion() == null || organization.getVersion() == 0){
            return CommonResponse.errorMsg("版本号不能为空");
        }else if (organizationService.getByName(organization.getName()) != null){
            return CommonResponse.errorMsg("此组织名称已存在");
        }
        return CommonResponse.ok(organizationService.update(organization));
    }

    @DeleteMapping("/{code}")
    CommonResponse del(Long id){
        if (id == null || id == 0){
            return CommonResponse.errorMsg("系统错误");
        }
        return CommonResponse.ok(organizationService.del(id));
    }

    @PostMapping(value = "/find")
    CommonResponse getByName(String name){
        if (name == null || name.equals(" ")){
            return CommonResponse.errorMsg("组织名称不能为空");
        }
        return CommonResponse.ok(organizationService.getByName(name));
    }

    @PostMapping(value = "/findAll")
    CommonResponse getAll( ){
        return CommonResponse.ok(organizationService.getAll( ));
    }
}
