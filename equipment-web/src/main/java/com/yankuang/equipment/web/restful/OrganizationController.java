package com.yankuang.equipment.web.restful;


import com.yankuang.equipment.authority.model.Organization;
import com.yankuang.equipment.authority.service.OrganizationService;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.UuidUtils;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
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
@RequestMapping("/v1/orgs")
public class OrganizationController {

    @RpcConsumer
    OrganizationService organizationService;

    /**
     * @param id
     * @return
     * @method 通过id查询
     */
    @GetMapping("/{id}")
    public CommonResponse findById(@PathVariable Long id) {
        return CommonResponse.ok(organizationService.findById(id));
    }

    /**
     * @param organization
     * @return
     * @method 添加
     */
    @PostMapping
    public CommonResponse create(@RequestBody Organization organization) {
        if (StringUtils.isEmpty(organization.getName())) {
            return CommonResponse.errorMsg("请填写组织名称");
        }
        if (organizationService.findByName(organization.getName()) != null) {
            return CommonResponse.errorMsg("此组织名称已存在");
        }
        if (StringUtils.isEmpty(organization.getpId())) {
            return CommonResponse.errorMsg("父级pId不能为空");
        }

        int level = organization.getLevel() == null ? 0 : organization.getLevel();
        organization.setLevel(level);

        Long pId = (StringUtils.isEmpty(organization.getpId())) ? 0 : organization.getpId();
        organization.setpId(pId);

        String pcode = (organization.getPcode() == null || " ".equals(organization.getPcode())) ? "1" : organization.getPcode();
        organization.setPcode(pcode);

        int sort = organization.getSorting() == null ? 1 : organization.getSorting();
        organization.setSorting(sort);

        Long version = (organization.getVersion() == null || organization.getVersion() == 0) ? 1 : organization.getVersion();
        organization.setVersion(version);

        organization.setCode(UuidUtils.newUuid());
        organization.setCreateBy("admin");//TODO 由于用户功能暂未开发完，先写死，后期改
        organization.setUpdateBy("admin");

        return CommonResponse.ok(organizationService.create(organization));
    }

    /**
     * @param jsonString
     * @return
     * @method 更新
     */
    @PutMapping
    public CommonResponse update(@RequestBody String jsonString) {
        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorTokenMsg("参数不能为空");
        }
        Organization organization = JsonUtils.jsonToPojo(jsonString, Organization.class);

        if (StringUtils.isEmpty(organization.getId())) {
            return CommonResponse.errorMsg("id不能为空");
        }
        return CommonResponse.ok(organizationService.update(organization));
    }

    /**
     * @param jsonString
     * @return
     * @mehtod 根据id列表删除
     * 入参是id列表，一个或者多个
     */
    @DeleteMapping
    public CommonResponse delete(@RequestBody String jsonString) {
        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorTokenMsg("没有查询的id");
        }
        List<Long> ids = JsonUtils.jsonToList(jsonString, Long.class);
        for (Long id : ids) {
            boolean idB = organizationService.delete(id);
            if (idB == false) {
                return CommonResponse.errorTokenMsg("删除失败");
            }
        }
        return CommonResponse.ok();
    }


    /**
     * @return
     * @method 查询所有
     */
    @GetMapping(value = "/all")
    public CommonResponse findAll() {
        return CommonResponse.ok(organizationService.findAll());
    }


    /**
     * @param page
     * @param size
     * @return
     * @method 分页查询
     */
    @GetMapping
    public CommonResponse findByPage(@RequestParam(value = "page", defaultValue = "1") int page,
                                     @RequestParam(value = "size", defaultValue = "20") int size,
                                     @RequestParam(value = "searchInput", defaultValue = "") String serachInput) {
        Map orgMap = new HashMap();
        return CommonResponse.ok(organizationService.findByPage(page, size, orgMap));
    }

}
