package com.yankuang.equipment.web.restful;

import com.github.pagehelper.PageInfo;
import com.yankuang.equipment.authority.model.*;
import com.yankuang.equipment.authority.service.*;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.web.dto.*;
import com.yankuang.equipment.web.util.AuthorityTreeUtils;
import com.yankuang.equipment.web.util.CodeUtil;
import com.yankuang.equipment.web.util.RedisOperator;
import com.yankuang.equipment.web.util.TreeUtils;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/users")
public class UserController {

    public static final Logger log = Logger.getLogger(UserController.class);

    @Autowired
    RedisOperator redis;

    @RpcConsumer
    UserService userService;

    @RpcConsumer
    RoleAuthorityService roleAuthorityService;

    @RpcConsumer
    RoleUserService roleUserService;

    @RpcConsumer
    DeptUserService deptUserService;

    @RpcConsumer
    RoleService roleService;

    @RpcConsumer
    AuthorityService authorityService;

    /**
     * 用户退出登录.
     */
    @PostMapping(value = "/loginOut")
    public CommonResponse loginOut(HttpServletRequest request) {
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
    @PostMapping(value = "/login")
    public CommonResponse login(@RequestBody String jsonString) {
        if (jsonString == null || "".equals(jsonString)) {
            return CommonResponse.errorMsg("参数不能为空");
        }
        UserIn user = JsonUtils.jsonToPojo(jsonString, UserIn.class);
        String name = user.getUsername();
        if (StringUtils.isEmpty(name)) {
            return CommonResponse.errorMsg("用户名不能为空");
        }
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            return CommonResponse.errorMsg("密码不能为空");
        }
        log.info("--------- 用户登录信息:" + name + ":" + password);
        List<AuthorityDTO> authoritys = new ArrayList();
        List<RoleDTO> roles = new ArrayList();
        final Base64.Decoder decoder = Base64.getDecoder();
        String token = "";
        User loginUser = userService.findByName(name);
        log.info("根据用户名查询的用户信息" + loginUser.toString());
        if (loginUser != null && !password.equals(loginUser.getPassword())) {
            return CommonResponse.errorTokenMsg("密码错误");
        }
        if (loginUser != null && password.equals(loginUser.getPassword())) {
            log.info(loginUser.toString());
            List<Authority> authorities1 = authorityService.findByUserId(loginUser.getId());
            for (Authority authority : authorities1) {
                AuthorityDTO authorityDTO = new AuthorityDTO();
                authorityDTO.setUrl(authority.getUrl());
                authorityDTO.setId(authority.getId());
                authorityDTO.setpId(authority.getpId());
                authorityDTO.setLevel(authority.getLevel());
                authorityDTO.setType(authority.getType());
                authorityDTO.setName(authority.getName());
                authorityDTO.setSorting(authority.getSorting());
                authoritys.add(authorityDTO);
            }
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
            // 存放redis,暂时2小时
            redis.set(token, JsonUtils.objectToJson(userDTO1), 7200);
            // 更新user数据库表，记录最新一次登录保存的redis的key(token)
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
        // 用户角色列表
        userDTO.setRoles(roles);
        List<RoleDTO> roles1 = roles.stream()
                .filter(roleDTO -> "admin".equals(roleDTO.getName()))
                .collect(Collectors.toList());
        if (roles1.size() > 0) {
            List<AuthorityDTO> authoritys1 = authoritys.stream().
                    filter(authorityDTO -> 1 == authorityDTO.getType())
                    .collect(Collectors.toList());
            List<AuthorityDTO> list = getTree(authoritys1);
            // 用户权限idlist
            userDTO.setAuthoritys(list);
        } else {
            // 用户权限idlist
            userDTO.setAuthoritys(getTree(authoritys));
        }

        System.out.println(userDTO.toJsonString());
        return userDTO;
    }

    private List<AuthorityDTO> getTree(List<AuthorityDTO> authoritys) {
        AuthorityTreeUtils authorityTreeUtil = new AuthorityTreeUtils();
        List<AuthorityDTO> trees = new ArrayList<>();
        AuthorityDTO tree = null;
        for (AuthorityDTO authority : authoritys) {
            tree = new AuthorityDTO();
            tree.setId(authority.getId());
            tree.setpId(authority.getpId());
            tree.setName(authority.getName());
            tree.setLevel(authority.getLevel());
            tree.setType(authority.getType());
            tree.setUrl(authority.getUrl());
            tree.setSorting(authority.getSorting());
            trees.add(tree);
        }
        return authorityTreeUtil.menuList(trees);
    }

    /**
     * 根据id查询用户....
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public CommonResponse findById(@PathVariable Long id) {
        return CommonResponse.ok(userService.findById(id));
    }

    /**
     * 根据id删除用户.
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public CommonResponse delete(@PathVariable Long id) {
        return CommonResponse.ok(userService.delete(id));
    }

    /**
     * 添加用户.
     *
     * @param jsonString
     * @return
     */
    @PostMapping()
    public CommonResponse create(@RequestBody String jsonString) {
        if (jsonString == null || "".equals(jsonString)) {
            return CommonResponse.errorMsg("参数不能为空");
        }
        UserIn user = JsonUtils.jsonToPojo(jsonString, UserIn.class);

        if (StringUtils.isEmpty(user.getAccount())) {
            return CommonResponse.errorMsg("账号 不能为空");
        }
        if (StringUtils.isEmpty(user.getName())) {
            return CommonResponse.errorMsg("用户名 不能为空");
        }
        Long roleId = user.getRoleId();
        if (StringUtils.isEmpty(roleId)) {
            return CommonResponse.errorMsg("用户角色id 不能为空");
        }
        Long deptId = user.getDeptId();
        if (StringUtils.isEmpty(deptId)) {
            return CommonResponse.errorMsg("用户部门id 不能为空");
        }
        User userCheck = userService.findByAccount(user.getAccount());
        if (!StringUtils.isEmpty(userCheck)) {
            return CommonResponse.errorMsg("账号已存在,请勿重复添加");
        }
        User user1 = new User();
        user1.setCode(CodeUtil.getCode());
        user1.setPassword("123456");
        user1.setAccount(user.getAccount());
        user1.setName(user.getName());
        user1.setMail(user.getMail());
        user1.setSex(user.getSex());
        user1.setSorting(user.getSorting());
        user1.setTelephone(user.getTelephone());
        user1.setRemark(user.getRemark());
        //TODO 从redis中获取登陆人姓名
        user1.setUpdateBy("admin");
        user1.setCreateBy("admin");
        Boolean b = userService.create(user1);
        if (!b == true) {
            return CommonResponse.errorMsg("添加失败");
        }
        //  用户部门关系表添加
        User user2 = userService.findByAccount(user.getAccount());
        DeptUser deptUser = new DeptUser();
        deptUser.setDepartmentId(deptId);
        deptUser.setUserId(user2.getId());
        deptUser.setCreateBy("admin");
        deptUser.setUpdateBy("admin");
        Boolean b1 = deptUserService.create(deptUser);
        if (!b1){
            return CommonResponse.errorMsg("用户关联部门失败");
        }
        //  用户角色表添加
        RoleUser roleUser = new RoleUser();
        roleUser.setRoleId(roleId);
        roleUser.setUserId(user2.getId());
        roleUser.setCreateBy("admin");
        roleUser.setUpdateBy("admin");
        Boolean b3 = roleUserService.create(roleUser);
        if (!b3) {
            return CommonResponse.errorMsg("用户关联角色失败");
        }
        return CommonResponse.ok("用户添加成功");
    }

    /**
     * 根据id修改用户.
     *
     * @param jsonString
     * @return
     */
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
     * @param jsonString
     * @return
     */
    @GetMapping
    public CommonResponse findByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "20") Integer size,
                                     @RequestParam String jsonString) {
        Map user = new HashMap();
        PageInfo<Map> users = userService.findByPage(page, size, user);
        return CommonResponse.ok(users);
    }

    /**
     * 用户停用.
     *
     * @param id
     * @return
     */
    @PatchMapping("/stop/{id}")
    public CommonResponse stop(@PathVariable Long id) {
        Boolean b = userService.stop(id);
        if (!b) {
            return CommonResponse.errorMsg("禁用失败");
        }
        return CommonResponse.build(200, "禁用成功", null);
    }

    /**
     * 用户启用.
     *
     * @param id
     * @return
     */
    @PatchMapping("/start/{id}")
    public CommonResponse start(@PathVariable Long id) {
        Boolean b = userService.start(id);
        if (!b) {
            return CommonResponse.errorMsg("启用失败");
        }
        return CommonResponse.build(200, "启用成功", null);
    }

}

