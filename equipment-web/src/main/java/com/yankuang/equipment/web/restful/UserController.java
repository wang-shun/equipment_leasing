package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.*;
import com.yankuang.equipment.authority.service.*;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.common.util.UuidUtils;
import com.yankuang.equipment.web.dto.*;
import com.yankuang.equipment.web.util.CodeUtil;
import com.yankuang.equipment.web.util.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import io.terminus.common.model.Paging;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Api
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/users")
public class UserController {

    public static final Logger log = Logger.getLogger(UserController.class);

    @Autowired
    RedisOperator redis;

    @RpcConsumer(version = "0.0.1", check = "false")
    UserService userService;

    @RpcConsumer
    RoleAuthorityService roleAuthorityService;

    @RpcConsumer
    RoleUserService roleUserService;

    @RpcConsumer
    DeptUserService deptUserService;

    @RpcConsumer
    OrgDeptRoleUserService orgDeptRoleUserService;

    @RpcConsumer
    RoleService roleService;

    @RpcConsumer
    AuthorityService authorityService;

    /**
     * 用户退出登录.
     */
    @PostMapping(value = "/loginOut")
    CommonResponse loginOut(HttpServletRequest request) {
        // 登录先去redis中查看登陆状态
        String token = request.getHeader("token");
        redis.del(token);
        return CommonResponse.build(200, "您已经成功退出登录", null);
    }

    /**
     * 用户登录.
     *
     * @param jsonString
     * @return
     */
    @ApiOperation("user login")
    @PostMapping(value = "/login")
    CommonResponse login(@RequestBody String jsonString) {
        if (jsonString == null || "".equals(jsonString)) {
            return CommonResponse.errorMsg("参数不能为空");
        }
        UserIn user = JsonUtils.jsonToPojo(jsonString, UserIn.class);
        String username = user.getUsername();
        if (StringUtils.isEmpty(username)) {
            return CommonResponse.errorMsg("用户名不能为空");
        }
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            return CommonResponse.errorMsg("密码不能为空");
        }
        log.info("--------- 用户登录信息:" + username + ":" + password);
        List<AuthorityDTO> authoritys = new ArrayList();
        List<RoleDTO> roles = new ArrayList();
        final Base64.Decoder decoder = Base64.getDecoder();
        String token = "";
        User loginUser = userService.login(username);
        log.info("根据用户名查询的用户信息" + loginUser.toString());
        if (loginUser != null && !password.equals(loginUser.getPassword())) {
            return CommonResponse.errorTokenMsg("密码错误");
        }
        if (loginUser != null && password.equals(loginUser.getPassword())) {
            log.info(loginUser.toString());
            // 登录验证成功，获取用户基本信息，角色信息，权限信息
            UserDTO userDTO1 = getUserDTO(authoritys, roles, loginUser);
            // redis中存放的key
            token = CodeUtil.getCode();
            userDTO1.setToken(token);
            // user对象信息转json加密base64作为值存放redis
            final Base64.Encoder encoder = Base64.getEncoder();
            String encodedResult = "";
            try {
                // 转字节
                final byte[] textByte = JsonUtils.objectToJson(userDTO1).getBytes("UTF-8");
                // 加密
                encodedResult = encoder.encodeToString(textByte);
                log.info(encodedResult);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // 存放redis
            redis.set(token, encodedResult, 259200);
            // TODO 更新user数据库表，记录最新一次登录保存的redis的key(token)
            User u = new User();
            u.setId(loginUser.getId());
            u.setToken(token);
            userService.update(u);
            // 返回加密用户信息包含token
            return CommonResponse.ok(encodedResult);
        }
        return CommonResponse.errorTokenMsg("用户不存在");
    }

    private UserDTO getUserDTO(List<AuthorityDTO> authoritys, List<RoleDTO> roles, User loginUser) {
        UserDTO userDTO = new UserDTO();
        // 用户角色列表
        List<RoleUser> roleUsers = roleUserService.findByUserId(loginUser.getId());
        if (roleUsers == null || roleUsers.size() == 0) {
            return userDTO;
        }
        userDTO.setId(loginUser.getId());
        userDTO.setName(loginUser.getName());
        // 遍历用户角色列表
        for (RoleUser roleUser : roleUsers) {
            Long roleId = roleUser.getRoleId();
            //根据roleId查询角色信息
            Role role = roleService.findById(roleId);
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setId(role.getId());
            roleDTO.setName(role.getName());
            roles.add(roleDTO);
            // 角色权限
            List<RoleAuthority> roleAuthorities =
                    roleAuthorityService.findByRoleId(roleUser.getRoleId());
            //遍历角色权限列表
            for (RoleAuthority roleAuthority : roleAuthorities) {
                Authority authority = authorityService.findById(roleAuthority.getAuthorityId());
                AuthorityDTO authorityDTO = new AuthorityDTO();
                authorityDTO.setId(authority.getId());
                authorityDTO.setName(authority.getName());
                authorityDTO.setpId(authority.getpId());
                authorityDTO.setType(authority.getType());
                authorityDTO.setSorting(authority.getSorting());
                authorityDTO.setCode(authority.getCode());
                authoritys.add(authorityDTO);
            }
        }
        // 用户角色列表
        userDTO.setRoles(roles);
        // 用户角色idlist
        userDTO.setAuthoritys(authoritys);
        System.out.println(userDTO.toJsonString());
        return userDTO;
    }

    /**
     * 根据id查询用户....
     *
     * @param id
     * @return
     */
    @ApiOperation("user findById")
    @GetMapping(value = "/{id}")
    CommonResponse findById(@PathVariable Long id) {
        return CommonResponse.ok(userService.findById(id));
    }

    /**
     * 根据id删除用户.
     *
     * @param id
     * @return
     */
    @ApiOperation("user deleteById")
    @DeleteMapping(value = "/{id}")
    CommonResponse deleteById(@PathVariable Long id) {
        return CommonResponse.ok(userService.delete(id));
    }

    /**
     * 添加用户.
     *
     * @param jsonString
     * @return
     */
    @ApiOperation("user create")
    @PostMapping()
    CommonResponse addUser(@RequestBody String jsonString) {
        if (jsonString == null || "".equals(jsonString)) {
            return CommonResponse.errorMsg("参数不能为空");
        }
        User user = JsonUtils.jsonToPojo(jsonString, User.class);
        user.setCode(CodeUtil.getCode());

        if (user.getMail() == null || "".equals(user.getMail())) {
            return CommonResponse.errorMsg("mail 不能为空");
        }
        if (user.getTelephone() == null || "".equals(user.getTelephone())) {
            return CommonResponse.errorMsg("telephone 不能为空");
        }
        if (user.getSex() == null || "".equals(user.getSex())) {
            return CommonResponse.errorMsg("性别 不能为空");
        }
        String remark = (user.getRemark() == null || "".equals(user.getRemark())) ? "备注" : user.getRemark();
        user.setRemark(remark);
        if (user.getSorting() == null || "".equals(user.getSorting())) {
            return CommonResponse.errorMsg("sorting 不能为空");
        }
        user.setVersion((long) 1);
        user.setStatus((byte) 1);
        //TODO 从redis中获取登陆人姓名
        user.setUpdateBy("admin");
        user.setCreateBy("admin");
        Boolean b = userService.create(user);
        if (b == true) {
            return CommonResponse.ok(user);
        }
        return CommonResponse.errorTokenMsg("添加失败");
    }

    /**
     * 根据id修改用户.
     *
     * @param jsonString
     * @return
     */
    @ApiOperation("user create")
    @PutMapping()
    public CommonResponse update(@RequestBody String jsonString) {
        if (jsonString == null || "".equals(jsonString)) {
            return CommonResponse.errorMsg("参数不能为空");
        }
        User user = JsonUtils.jsonToPojo(jsonString, User.class);
        if (user.getId() == null || "".equals(user.getId())) {
            return CommonResponse.errorMsg("id 不能为空");
        }
        //todo
        user.setUpdateBy("登陆人");
        Boolean b = userService.update(user);
        if (b) {
            return CommonResponse.build(200, "更新成功", null);
        }
        return CommonResponse.errorTokenMsg("更新失败");
    }

    /**
     * 用户列表分页查询
     *
     * @param page
     * @param size
     * @param searchInput
     * @return
     */
    @GetMapping
    public CommonResponse paging(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                 @RequestParam(value = "size", defaultValue = "20") Integer size,
                                 @RequestParam String searchInput) {
        User user = new User();
//        user.setName(searchInput);
        Paging<User> users = userService.paging(page, size, user);
        return CommonResponse.ok(users);
    }

    /**
     * @param jsonString
     * @return
     * @method 用户添加功能
     */
    @PostMapping("/userAdd")
    public CommonResponse userAdd(@RequestBody String jsonString) {
        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorTokenMsg("参数不能为空");
        }
        OrgRoleDTO orgRoleDTO = JsonUtils.jsonToPojo(jsonString, OrgRoleDTO.class);
        if (orgRoleDTO.getAccount() == null || " ".equals(orgRoleDTO.getAccount())) {
            return CommonResponse.errorTokenMsg("请填写账号");
        }

        if (orgRoleDTO.getName() == null || " ".equals(orgRoleDTO.getName())) {
            return CommonResponse.errorTokenMsg("请填写用户名");
        }

        if (orgRoleDTO.getSex() == null) {
            return CommonResponse.errorTokenMsg("请选择性别");
        }

        if (orgRoleDTO.getOrgId() == null) {
            return CommonResponse.errorTokenMsg("请选择公司");
        }

        if (orgRoleDTO.getRoleId() == null) {
            return CommonResponse.errorTokenMsg("请选择角色");
        }

        if (orgRoleDTO.getDeptId() == null) {
            return CommonResponse.errorTokenMsg("请选择部门");
        }

        User user = new User();

        if (userService.findUserName(orgRoleDTO.getAccount()) <= 0) {
            user.setName(orgRoleDTO.getName());
            user.setAccount(orgRoleDTO.getAccount());
            user.setCode(UuidUtils.newUuid());
            user.setCreateBy("admin");//TODO 暂未开发完，先写死
            user.setPassword("123456");//TODO 面密码默认为123456，加密功能等后续完善
            user.setSex(orgRoleDTO.getSex());
            user.setStatus((byte) 1);
            user.setSorting(0L);
            user.setVersion(1L);
            user.setUpdateBy("admin");
            Boolean b = userService.create(user);

            if (b == false) {
                return CommonResponse.errorTokenMsg("添加用户失败");
            }

        }
        if (userService.findUserIds(orgRoleDTO.getAccount()) != null) {
            if (roleUserService.findUserId(userService.findUserIds(orgRoleDTO.getAccount())) > 0 || deptUserService.findDeptId(userService.findUserIds(orgRoleDTO.getAccount())) > 0) {
                return CommonResponse.errorTokenMsg("该用户已存在");
            }

        }
        //将信息添加到用户角色关系表
        RoleUser roleUser = new RoleUser();
        roleUser.setUserId(userService.findUserIds(orgRoleDTO.getAccount()));
        roleUser.setCreateBy("admin");//TODO 暂未开发完，先写死
        roleUser.setUpdateBy("admin");
        roleUser.setStatus((byte) 1);
        roleUser.setRoleId(orgRoleDTO.getRoleId());
        roleUser.setVersion(1L);

        roleUserService.create(roleUser);
        //将信息添加到部门用户关系表
        DeptUser deptUser = new DeptUser();
        deptUser.setCreateBy("admin");//TODO 暂未开发完，先写死
        deptUser.setUpdateBy("admin");
        deptUser.setStatus((byte) 1);
        deptUser.setDepartmentId(orgRoleDTO.getDeptId());
        deptUser.setUserId(userService.findUserIds(orgRoleDTO.getAccount()));
        deptUser.setVersion(1L);

        deptUserService.create(deptUser);

        return CommonResponse.build(200, "添加成功", null);
    }

    /**
     * @param jsonString
     * @return
     * @method 删除用户功能
     */
    @DeleteMapping("/delUser")
    public CommonResponse delUser(@RequestBody String jsonString) {
        if (com.yankuang.equipment.common.util.StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorTokenMsg("没有查询的id");
        }
        List<Long> ids = JsonUtils.jsonToList(jsonString, Long.class);
        for (Long id : ids) {
            if (roleUserService.deleteByUserId(id) == false) {
                return CommonResponse.errorTokenMsg("删除失败");
            }
            if (deptUserService.deleteByUserId(id) == false) {
                return CommonResponse.errorTokenMsg("删除失败");
            }
            if (userService.delete(id) == false) {
                return CommonResponse.errorTokenMsg("删除失败");
            }
        }
        return CommonResponse.ok();
    }

    /**
     * @param offset
     * @param limit
     * @return
     * @method 用户分页查询
     */
    @GetMapping("/findUser")
    public CommonResponse findUser(@RequestParam Integer offset,
                                   @RequestParam Integer limit) {
        Integer startPage = limit * (offset - 1);
        Integer endPage = limit * offset;
        OrgDeptRoleUser orgDeptRoleUser = new OrgDeptRoleUser();
        orgDeptRoleUser.setPages(startPage);
        orgDeptRoleUser.setLimit(endPage);
        return CommonResponse.ok(orgDeptRoleUserService.getAll(orgDeptRoleUser));
    }

    /**
     * @param jsonString
     * @return
     * @method 用户更新
     */
    @PutMapping("/updateUser")
    public CommonResponse udtUser(@RequestBody String jsonString) {
        if (StringUtils.isEmpty(jsonString)) {
            return CommonResponse.errorTokenMsg("参数不能为空");
        }

        OrgRoleDTO orgRoleDTO = JsonUtils.jsonToPojo(jsonString, OrgRoleDTO.class);
        User user = new User();

        if (orgRoleDTO.getName() != null) {
            if (userService.findUserAccount(orgRoleDTO.getName()) <= 0) {
                user.setName(orgRoleDTO.getName());
                user.setId(orgRoleDTO.getUserId());
                userService.update(user);
            } else {
                return CommonResponse.errorTokenMsg("姓名未改变");
            }
        }

        if (orgRoleDTO.getSex() != null) {
            if (userService.findUserSex(orgRoleDTO.getSex()) <= 0) {
                user.setSex(orgRoleDTO.getSex());
                user.setId(orgRoleDTO.getUserId());
                userService.update(user);
            } else {
                return CommonResponse.errorTokenMsg("性别未改变");
            }

        }

        if (orgRoleDTO.getAccount() != null) {
            if (userService.findUserName(orgRoleDTO.getAccount()) <= 0) {
                user.setAccount(orgRoleDTO.getAccount());
                user.setId(orgRoleDTO.getUserId());
                userService.update(user);
            } else {
                return CommonResponse.errorTokenMsg("账号未改变");
            }
        }

        if (userService.findUserIds(orgRoleDTO.getAccount()) != null) {
            if (roleUserService.findUserId(userService.findUserIds(orgRoleDTO.getAccount())) > 0 || deptUserService.findDeptId(userService.findUserIds(orgRoleDTO.getAccount())) > 0) {
                return CommonResponse.errorTokenMsg("组织、部门、角色关系未改变");
            }
        }

        if (orgRoleDTO.getRoleId() != null) {
            //将信息添加到用户角色关系表
            RoleUser roleUser = new RoleUser();
            roleUser.setUserId(userService.findUserIds(orgRoleDTO.getAccount()));
            roleUser.setCreateBy("admin");//TODO 暂未开发完，先写死
            roleUser.setUpdateBy("admin");
            roleUser.setStatus((byte) 1);
            roleUser.setRoleId(orgRoleDTO.getRoleId());
            roleUser.setVersion(1L);

            roleUserService.update(roleUser);
        }

        if (orgRoleDTO.getDeptId() != null) {
            //将信息添加到部门用户关系表
            DeptUser deptUser = new DeptUser();
            deptUser.setCreateBy("admin");//TODO 暂未开发完，先写死
            deptUser.setUpdateBy("admin");
            deptUser.setStatus((byte) 1);
            deptUser.setDepartmentId(orgRoleDTO.getDeptId());
            deptUser.setUserId(userService.findUserIds(orgRoleDTO.getAccount()));
            deptUser.setVersion(1L);

            deptUserService.update(deptUser);
        }

        return CommonResponse.ok();
    }

    /**
     * @param id
     * @return
     * @method 禁用
     */
    @PutMapping("/updateCloseStatus")
    public CommonResponse udtUserStatusClose(@RequestParam Long id) {
        if (userService.closeStatusUser(id) == false) {
            return CommonResponse.errorTokenMsg("禁用失败");
        }
        return CommonResponse.build(200, "禁用成功", null);
    }

    /**
     * @param id
     * @return
     * @method 启用功能
     */
    @PutMapping("/updateOpenStatus")
    public CommonResponse udtUserStatusOpen(@RequestParam Long id) {
        if (userService.openStatusUser(id) == false) {
            return CommonResponse.errorTokenMsg("启用失败");
        }
        return CommonResponse.build(200, "启用成功", null);
    }

}

