package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.Authority;
import com.yankuang.equipment.authority.service.AuthorityService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.web.dto.IdsDTO;
import com.yankuang.equipment.web.dto.TreeDTO;
import com.yankuang.equipment.web.util.CodeUtil;
import com.yankuang.equipment.web.util.TreeUtils;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/acls")
public class AuthorityController {
    @RpcConsumer
    AuthorityService authorityService;

    /**
     * 通过id查询.
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public CommonResponse findById(@PathVariable Long id) {
        return CommonResponse.ok(authorityService.findById(id));
    }

    /**
     * 根据id修改.
     * @param jsonString
     * @return
     */
    @PutMapping
    public CommonResponse update(@RequestBody String jsonString) {

        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorTokenMsg("参数不能为空");
        }
        Authority authority = JsonUtils.jsonToPojo(jsonString, Authority.class);
        if (StringUtils.isEmpty(authority.getId())) {
            return CommonResponse.errorTokenMsg("权限id不能为空");
        }
        authority.setUpdateBy("admin");
        Boolean b = authorityService.update(authority);
        if (b) {
            return CommonResponse.build(200, "更新成功", null);
        }
        return CommonResponse.errorMsg("更新失败");
    }

    /**
     *  添加权限.
     *  type=1 添加权限菜单
     *  rype=2 添加权限按钮
     * @param jsonString
     * @return
     */
    @PostMapping
    @Transactional
    public CommonResponse create(@RequestBody String jsonString) {
        if (jsonString == null || "".equals(jsonString)) {
            return CommonResponse.errorMsg("参数不能为空");
        }
        Authority authority = JsonUtils.jsonToPojo(jsonString, Authority.class);
        String name = authority.getName();
        if (StringUtils.isEmpty(name)) {
            return CommonResponse.errorTokenMsg("权限名称不能为空");
        }
        Long type = authority.getType();
        if (StringUtils.isEmpty(type)) {
            return CommonResponse.errorTokenMsg("权限type类型不能为空");
        }
        Long pId = authority.getpId();
        if (StringUtils.isEmpty(pId)) {
            return CommonResponse.errorTokenMsg("权限pId不能为空");
        }
        // 根据权限名称查重
        Authority au = authorityService.findByName(name);
        // 不存在 ，添加权限
        if (StringUtils.isEmpty(au)) {
            Authority au1 = new Authority();
            au1.setCode(CodeUtil.getCode());
            au1.setName(name);
            au1.setType(type);
            au1.setpId(pId);
            au1.setCreateBy("admin");
            au1.setUpdateBy("admin");
            //添加权限
            Boolean b = authorityService.create(au1);
            if (!b) {
                return CommonResponse.errorMsg("权限添加失败");
            }
            return CommonResponse.build(200, "权限添加成功", null);
        }
        // 存在
        return CommonResponse.build(200, "权限已存在，请勿重复添加", null);
    }

    /**
     * 根据id删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public CommonResponse delete(@PathVariable Long id) {
        if (id == null || id == 0) {
            return CommonResponse.errorTokenMsg("权限id不能为空");
        }
        Boolean b = authorityService.delete(id);
        if (b) {
            return CommonResponse.ok("权限删除成功");
        }
        return CommonResponse.errorMsg("权限删除失败");
    }

    /**
     * 根据id
     * @param jsonString
     * @return
     */
    @DeleteMapping("/byIds")
    public CommonResponse deletes(@RequestBody String jsonString) {
        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorTokenMsg("选择要删除的数据");
        }
        IdsDTO idsDTO = JsonUtils.jsonToPojo(jsonString, IdsDTO.class);
        List<Long> ids = idsDTO.getIds();
        Boolean b = authorityService.deletes(ids);
        if (!b) {
            return CommonResponse.errorMsg("批量删除失败");
        }
        return CommonResponse.ok("批量删除成功");
    }

    /**
     * @return
     * @method 查询权限树
     *  type=1 菜单
     */
    @GetMapping(value = "/all")
    public CommonResponse findAll() {

        List<Authority> authorities = authorityService.findAll();
        List<TreeDTO> trees = new ArrayList<>();
        TreeDTO tree = null;
        for (Authority authority : authorities) {
            tree = new TreeDTO();
            tree.setId(authority.getId());
            tree.setpId(authority.getpId());
            tree.setName(authority.getName());
            trees.add(tree);
        }
        TreeUtils treeUtils = new TreeUtils();
        List<Object> list = treeUtils.menuList(trees);
        return CommonResponse.ok(list);
    }

}
