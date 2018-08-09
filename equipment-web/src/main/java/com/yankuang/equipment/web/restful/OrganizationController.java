package com.yankuang.equipment.web.restful;


import com.yankuang.equipment.authority.model.Organization;
import com.yankuang.equipment.authority.service.OrganizationService;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.UuidUtils;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
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
    @PostMapping("/add")
    public CommonResponse add(@RequestBody Organization organization){
        if (organization.getName() == null || " ".equals(organization.getName())){
            return CommonResponse.errorMsg("请填写组织名称");
        }

        if (organizationService.getByName(organization.getName()) != null){
            return CommonResponse.errorMsg("此组织名称已存在");
        }

        int level = organization.getLevel() == null?0:organization.getLevel();
        organization.setLevel(level);

        String pcode = (organization.getPcode() == null || " ".equals(organization.getPcode())) ?"1":organization.getPcode();
        organization.setPcode(pcode);

        int sort = organization.getSorting() == null ?1:organization.getSorting();
        organization.setSorting(sort);

        Long version = (organization.getVersion() == null || organization.getVersion() == 0) ?1:organization.getVersion();
        organization.setVersion(version);

        organization.setCode(UuidUtils.newUuid());
        organization.setCreateBy("admin");//TODO 由于用户功能暂未开发完，先写死，后期改
        organization.setUpdateBy("admin");

        return CommonResponse.ok(organizationService.add(organization));
    }

    /**
     * @method 更新
     * @param jsonString
     * @return
     */
    @PutMapping
    public CommonResponse update(@RequestBody String jsonString){
        if (StringUtils.isEmpty(jsonString)){
            return CommonResponse.errorTokenMsg("参数不能为空");
        }

        Organization organization = JsonUtils.jsonToPojo(jsonString,Organization.class);

        if (organization.getId() == null || organization.getId() == 0){
            return CommonResponse.errorMsg("系统错误");
        }
        if (organization.getPcode() == null || " ".equals(organization.getPcode())){
            return CommonResponse.errorMsg("系统错误");
        }

        return CommonResponse.ok(organizationService.update(organization));
    }

    /**
     * @mehtod 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public CommonResponse del(@PathVariable Long id){
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
    @GetMapping
    public CommonResponse getByName(@RequestParam String name){
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
    public CommonResponse getAll( ){
        return CommonResponse.ok(organizationService.getAll( ));
    }


    /**
     * @method 分页查询
     * @param page
     * @param limit
     * @return
     */
    @PostMapping("/findpage/{page}/{limit}")
    public CommonResponse getPage(@PathVariable int page,@PathVariable int limit){
        Organization organization = new Organization();
        return CommonResponse.ok(organizationService.findpage(page,limit,organization));
    }

    /**
     * @method 查询所有名字
     * @return
     */

    @GetMapping("/findName")
    public CommonResponse getName(){
        return CommonResponse.ok(organizationService.findName());
    }

    /**
     * @method 公司管理添加功能
     * @param pOrgsId
     * @param OrgsName
     * @return
     */
    @PostMapping("/orgsAdd")
    public CommonResponse orgsAdd(@RequestParam String pOrgsId,@RequestParam String OrgsName){
        if (pOrgsId == null || " ".equals(pOrgsId)){
            return CommonResponse.errorTokenMsg("系统错误");
        }

        if (OrgsName == null || " ".equals(OrgsName)){
            return CommonResponse.errorMsg("请填写组织名称");
        }

        if (organizationService.getByName(OrgsName) != null){
            return CommonResponse.errorMsg("此组织名称已存在");
        }

        Organization organization = new Organization();

        organization.setName(OrgsName);
        organization.setPcode(pOrgsId);

        int level = organization.getLevel() == null?0:organization.getLevel();
        organization.setLevel(level);

        String pcode = (organization.getPcode() == null || " ".equals(organization.getPcode())) ?"1":organization.getPcode();
        organization.setPcode(pcode);

        int sort = organization.getSorting() == null ?1:organization.getSorting();
        organization.setSorting(sort);

        Long version = (organization.getVersion() == null || organization.getVersion() == 0) ?1:organization.getVersion();
        organization.setVersion(version);

        organization.setCode(UuidUtils.newUuid());
        organization.setCreateBy("admin");//TODO 由于用户功能暂未开发完，先写死，后期改
        organization.setUpdateBy("admin");

        return CommonResponse.ok(organizationService.add(organization));
    }

    /**
     * @method 公司管理修改功能
     * @param pOrgsId
     * @param orgsName
     * @param id
     * @return
     */
    @PutMapping("/orgsUpdate")
    public CommonResponse orgsUdt(@RequestParam String pOrgsId,@RequestParam String orgsName,@RequestParam Long id){

        if (id == null){
            return CommonResponse.errorMsg("系统错误");
        }
        if (pOrgsId == null || " ".equals(pOrgsId)){
            return CommonResponse.errorMsg("系统错误");
        }

        if (orgsName == null || " ".equals(orgsName)){
            return CommonResponse.errorTokenMsg("组织名称不能为空");
        }

        Organization organization = new Organization();
        organization.setId(id);
        organization.setName(orgsName);
        organization.setPcode(pOrgsId);

        return CommonResponse.ok(organizationService.update(organization));

    }
}
