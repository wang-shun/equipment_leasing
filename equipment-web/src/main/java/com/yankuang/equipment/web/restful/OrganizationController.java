package com.yankuang.equipment.web.restful;


import com.yankuang.equipment.authority.model.Organization;
import com.yankuang.equipment.authority.service.OrganizationService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.UuidUtils;
import com.yankuang.equipment.web.dto.IdsDTO;
import com.yankuang.equipment.web.dto.TreeDTO;
import com.yankuang.equipment.web.util.TreeUtils;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;


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
     * 组织新增.
     * @param organization
     * @return
     */
    @PostMapping
    public CommonResponse create(@RequestBody Organization organization) {
        if (StringUtils.isEmpty(organization.getName())) {
            return CommonResponse.errorMsg("请填写组织名称");
        }
        if (organizationService.findByName(organization.getName()) != null) {
            return CommonResponse.errorMsg("此组织名称已存在,请勿重复添加");
        }
        Long pId = organization.getpId();
        if (StringUtils.isEmpty(pId)) {
            return CommonResponse.errorMsg("父级pId不能为空");
        }
        organization.setpId(pId);
        int level = organization.getLevel() == null ? 0 : organization.getLevel();
        organization.setLevel(level);
        String pcode = (organization.getPcode() == null || " ".equals(organization.getPcode())) ? "1" : organization.getPcode();
        organization.setPcode(pcode);

        int sort = organization.getSorting() == null ? 1 : organization.getSorting();
        organization.setSorting(sort);

        Long version = (organization.getVersion() == null || organization.getVersion() == 0) ? 1 : organization.getVersion();
        organization.setVersion(version);

        Long typeId = organization.getTypeId() == null? 1L:organization.getTypeId();//增加组织类型
        organization.setTypeId(typeId);

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
        IdsDTO idsDTO = JsonUtils.jsonToPojo(jsonString,IdsDTO.class);
        List<Long> ids = idsDTO.getIds();
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
     * @method 查询组织树
     */
    @GetMapping(value = "/all")
    public CommonResponse findAll() {
        List<Organization> orgs= organizationService.findAll();

        List<TreeDTO> trees = new ArrayList<>();
        for (Organization org : orgs) {
            TreeDTO tree = new TreeDTO();
            tree.setId(org.getId());
            tree.setpId(org.getpId());
            tree.setName(org.getName());
            trees.add(tree);
        }
        TreeUtils treeUtils = new TreeUtils();
        List<Object> list = treeUtils.menuList(trees);

        return CommonResponse.ok(list);
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
                                     @RequestParam String jsonString) {
        Map orgMap = new HashMap();
        if (!StringUtils.isEmpty(jsonString)){
            Organization organization = JsonUtils.jsonToPojo(jsonString,Organization.class);
            if (organization != null) {
                orgMap.put("typeId", organization.getTypeId());
                orgMap.put("pId", organization.getpId());
                orgMap.put("name",organization.getName());
                orgMap.put("code",organization.getCode());
                orgMap.put("level",organization.getLevel());
            }
        }
        return CommonResponse.ok(organizationService.findByPage(page, size, orgMap));
    }

    /**
     * @method 查询单位功能
     * @return
     */
    @GetMapping("/findList")
    public CommonResponse findList(){
        List<Organization> organizations = organizationService.findByPId();
        List<TreeDTO> treeDTOS = new ArrayList<>();
        for (Organization organization : organizations) {
            TreeDTO treeDTO = new TreeDTO();
            treeDTO.setId(organization.getId());
            treeDTO.setpId(0l);
            treeDTO.setName(organization.getName());
            treeDTOS.add(treeDTO);
        }
        TreeUtils treeUtils = new TreeUtils();
        //前端这边接口需要id值为String类型，现将获取到的map中id获取并重新赋值成String
        List<Object> list = treeUtils.menuList(treeDTOS);
        for (Object obj:list){
            Map<String,Object> map = (Map<String,Object>)obj;
            map.put("id",map.get("id").toString());
        }
        return CommonResponse.ok(list);
    }
}
