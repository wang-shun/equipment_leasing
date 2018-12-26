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
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/users")
public class UserController {

    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    RedisOperator redis;

    @RpcConsumer
    UserService userService;

    @RpcConsumer
    RoleUserService roleUserService;

    @RpcConsumer
    DeptUserService deptUserService;

    @RpcConsumer
    RoleService roleService;

    @RpcConsumer
    AuthorityService authorityService;

    @RpcConsumer
    UserAuthorityService userAuthorityService;

    @RpcConsumer
    CodeService codeService;

    /**
     * 获取数据库id最大值生成.
     *
     * @return
     */
    private String getCode(String deptCode) {

        Map map = new HashMap();
        map.put("tableName", "el_user");
        Long idMax = codeService.findIdMax(map);
        idMax += 1;
        String code = CodeUtil.getFixedLengthCode(idMax.toString(), 4);
        return deptCode + code;

    }

    /**
     * 用户退出登录.
     */
    @PostMapping(value = "/loginOut")
    public CommonResponse loginOut(HttpServletRequest request) {
        String token = request.getHeader("token");
        redis.del(token);
        return CommonResponse.build(200, "您已经成功退出登录", null);
    }

    /**
     * 用户登录.
     *
     * @param user
     * @return
     */
    @PostMapping(value = "/login")
    public CommonResponse login(@RequestBody User user) {
        if (StringUtils.isEmpty(user)) {
            return CommonResponse.errorMsg("参数不能为空");
        }
        String account = user.getAccount();
        if (StringUtils.isEmpty(account)) {
            return CommonResponse.errorMsg("account不能为空");
        }
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            return CommonResponse.errorMsg("密码不能为空");
        }
        log.info("--------- 用户登录信息:" + account + ":" + password);
        List<AuthorityTreeDTO> authoritys = new ArrayList<>();
        List<RoleDTO> roles = new ArrayList<>();
        final Base64.Decoder decoder = Base64.getDecoder();
        String token = "";
        User loginUser = userService.findByAccount(account);
        if (StringUtils.isEmpty(loginUser)) {
            return CommonResponse.errorTokenMsg("账号不存在");
        }
        Byte status = loginUser.getStatus();
        if (status.equals(2) || 2 == status) {
            return CommonResponse.errorTokenMsg("账号已经被停用");
        }
        if (!password.equals(loginUser.getPassword())) {
            System.out.println(loginUser.getPassword());
            return CommonResponse.errorTokenMsg("密码错误");
        }
        log.info("根据用户名查询的用户信息" + loginUser.toString());
        List<Authority> authorityResult = authorityService.findByUserCode(loginUser.getCode());
        for (Authority authority : authorityResult) {
            AuthorityTreeDTO authorityDTO = new AuthorityTreeDTO();
            authorityDTO.setUrl(authority.getUrl());
            authorityDTO.setCode(authority.getCode());
            authorityDTO.setPcode(authority.getPcode());
            authorityDTO.setLevel(authority.getLevel());
            authorityDTO.setType(authority.getType());
            authorityDTO.setName(authority.getName());
            authorityDTO.setSorting(authority.getSorting());
            authorityDTO.setIcon(authority.getIcon());
            authorityDTO.setRemark(authority.getRemark());
            authorityDTO.setCreateAt(authority.getCreateAt());
            authoritys.add(authorityDTO);
        }
        List<Role> roles1 = roleService.findByUserCode(loginUser.getCode());
        for (Role role : roles1) {
            RoleDTO roleDTO = new RoleDTO();
            roleDTO.setCode(role.getCode());
            roleDTO.setName(role.getName());
            roles.add(roleDTO);
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setId(loginUser.getId());
        userDTO.setCode(loginUser.getCode());
        userDTO.setName(loginUser.getName());
        userDTO.setDeptCode(loginUser.getDeptCode());
        userDTO.setDeptId(loginUser.getDeptId());
        userDTO.setDeptName(loginUser.getDeptName());
        userDTO.setProjectCode(loginUser.getProjectCode());
        // redis中存放的key
        token = CodeUtil.getCode();
        userDTO.setToken(token);
        userDTO.setRoles(roles);
        // 当前登录用户的权限树列表
        List<AuthorityTreeDTO> authorityTreeDTOS = getTree(authoritys);
        // 1.redis中存放权限列表
        userDTO.setAuthoritys(authoritys);
        // 存放redis,暂时2小时
        redis.set(token, JsonUtils.objectToJson(userDTO), 120);
        // user对象信息转json加密base64作为值存放redis
        userDTO.setAuthoritys(authorityTreeDTOS);
        System.out.println("返给前端页面的是权限树型结构" + userDTO.toString());
        final Base64.Encoder encoder = Base64.getEncoder();
        String encodedResult = "";
        try {
            // 转字节
            final byte[] textByte = JsonUtils.objectToJson(userDTO).getBytes("UTF-8");
            // 加密
            encodedResult = encoder.encodeToString(textByte);
            log.info(encodedResult);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 更新user数据库表，记录最新一次登录保存的redis的key(token)
        User u = new User();
        u.setCode(loginUser.getCode());
        u.setToken(token);
        userService.update(u);
        // 返回加密用户信息包含token
        return CommonResponse.ok(encodedResult);
    }

    private List<AuthorityTreeDTO> getTree(List<AuthorityTreeDTO> authoritys) {
        AuthorityTreeUtils authorityTreeUtil = new AuthorityTreeUtils();
        List<AuthorityTreeDTO> trees = new ArrayList<>();
        AuthorityTreeDTO tree = null;
        for (AuthorityTreeDTO authority : authoritys) {
            tree = new AuthorityTreeDTO();
            tree.setCode(authority.getCode());
            tree.setPcode(authority.getPcode());
            tree.setName(authority.getName());
            tree.setLevel(authority.getLevel());
            tree.setType(authority.getType());
            tree.setUrl(authority.getUrl());
            tree.setIcon(authority.getIcon());
            tree.setSorting(authority.getSorting());
            tree.setRemark(authority.getRemark());
            tree.setCreateAt(authority.getCreateAt());
            trees.add(tree);
        }
        return authorityTreeUtil.menuList(trees);
    }


    /**
     * 添加用户.
     *
     * @param user
     * @return
     */
    @PostMapping()
    public CommonResponse create(@RequestBody User user) {
        if (StringUtils.isEmpty(user)) {
            return CommonResponse.errorMsg("参数不能为空");
        }
        if (StringUtils.isEmpty(user.getAccount())) {
            return CommonResponse.errorMsg("账号 不能为空");
        }
        if (StringUtils.isEmpty(user.getName())) {
            return CommonResponse.errorMsg("用户名 不能为空");
        }
        String deptCode = user.getDeptCode();
        if (StringUtils.isEmpty(deptCode)) {
            return CommonResponse.errorMsg("用户部门code不能为空");
        }
        List<String> roleCodes = user.getRoleCodes();
        if (StringUtils.isEmpty(roleCodes)) {
            return CommonResponse.errorMsg("用户角色roleCodes不能为空");
        }
        User userCheck = userService.findByAccount(user.getAccount());
        if (!StringUtils.isEmpty(userCheck)) {
            return CommonResponse.errorMsg("账号已存在,请勿重复添加");
        }
        user.setCode(getCode(deptCode));
        user.setPassword("123456");
        //从redis中获取登陆人姓名项目code
        String nameFromRedis = getLoginerName();
        user.setUpdateBy(nameFromRedis);
        user.setCreateBy(nameFromRedis);
        user.setProjectCode("sb001");
        Boolean b = userService.create(user);
        if (!b == true) {
            return CommonResponse.errorMsg("添加用户失败");
        }
        //  用户部门关系表添加
        User user2 = userService.findByAccount(user.getAccount());
        DeptUser deptUser = new DeptUser();
        deptUser.setDeptCode(deptCode);
        deptUser.setUserCode(user2.getCode());
        Boolean b1 = deptUserService.create(deptUser);
        if (!b1) {
            return CommonResponse.errorMsg("用户关联部门失败");
        }
        //  用户角色表添加
        for (String roleCode : roleCodes) {
            RoleUser roleUser = new RoleUser();
            roleUser.setRoleCode(roleCode);
            roleUser.setUserCode(user2.getCode());
            Boolean b3 = roleUserService.create(roleUser);
            if (!b3) {
                return CommonResponse.errorMsg("用户关联角色失败");
            }
        }

        return CommonResponse.ok("用户添加成功");
    }

    private String getLoginerName() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return "";
        }
        String userRedis = (String) redis.get(token);
        if (StringUtils.isEmpty(userRedis)) {
            return "";
        }
        UserDTO userFromRedis = JsonUtils.jsonToPojo(userRedis, UserDTO.class);
        String loginName = userFromRedis.getName();
        return loginName;
    }

    /**
     * 根据codes删除用户.
     *
     * @param codesDTO
     * @return
     */
    @DeleteMapping
    public CommonResponse delete(@RequestBody CodesDTO codesDTO) {
        if (StringUtils.isEmpty(codesDTO)) {
            return CommonResponse.errorMsg("请选择要删除的数据!");
        }
        List<String> codes = codesDTO.getCodes();
        // 删除用户部门
        if (!StringUtils.isEmpty(deptUserService.findByUserCode(codes))) {
            deptUserService.deleteByUserCode(codes);
        }
        // 删除用户角色
        if (!StringUtils.isEmpty(roleUserService.findByUserCode(codes))) {
            roleUserService.deleteByRoleCode(codes);
        }
        Boolean b = userService.delete(codes);
        if (!b) {
            return CommonResponse.errorMsg("删除失败!");
        }
        return CommonResponse.ok("删除成功!");

    }

    /**
     * 根据code修改用户.
     *
     * @param user
     * @return
     */
    @PutMapping()
    public CommonResponse update(@RequestBody User user) {
        if (StringUtils.isEmpty(user)) {
            return CommonResponse.errorMsg("参数不能为空");
        }
        if (StringUtils.isEmpty(user.getCode())) {
            return CommonResponse.errorMsg("code不能为空");
        }
        List<String> userCodes = new ArrayList<>();
        userCodes.add(user.getCode());
        if (!StringUtils.isEmpty(deptUserService.findByUserCode(userCodes))) {
            // 删除用户部门旧关系
            deptUserService.deleteByUserCode(userCodes);
            // 添加用户部门新关系
            DeptUser deptUser = new DeptUser();
            deptUser.setDeptCode(user.getDeptCode());
            deptUser.setUserCode(user.getCode());
            deptUserService.create(deptUser);
        }

        if (!StringUtils.isEmpty(roleUserService.findByUserCode(userCodes))) {
            // 删除用户角色旧关系
            roleUserService.deleteByUserCode(userCodes);
            // 添加用户角色新关系
            List<String> roleCodes = user.getRoleCodes();
            for (String roleCode : roleCodes) {
                RoleUser roleUser = new RoleUser();
                roleUser.setRoleCode(roleCode);
                roleUser.setUserCode(user.getCode());
                roleUserService.create(roleUser);
            }
        }
        String nameFromRedis = getLoginerName();
        user.setUpdateBy(nameFromRedis);
        Boolean b = userService.update(user);
        if (!b) {
            return CommonResponse.errorTokenMsg("更新失败");
        }
        return CommonResponse.ok("更新成功");
    }


    /**
     * 根据code查询用户详情.
     *
     * @param code
     * @return
     */
    @GetMapping(value = "/{code}")
    public CommonResponse findById(@PathVariable String code) {

        User user = userService.findByCode(code);
        List<Role> roleList = roleService.findByUserCode(code);
        // 用户角色
        List<ResultSmall> roles = new ArrayList<>();
        for (Role role : roleList) {
            ResultSmall resultSmall = new ResultSmall();
            resultSmall.setName(role.getName());
            resultSmall.setCode(role.getCode());
            roles.add(resultSmall);
        }
        user.setRoles(roles);
        // 用户权限
        List<Authority> authoritieList = authorityService.findByUserCode(code);
        List<ResultSmall> authorities = new ArrayList<>();
        for (Authority authority : authoritieList) {
            ResultSmall resultSmall = new ResultSmall();
            resultSmall.setName(authority.getName());
            resultSmall.setCode(authority.getCode());
            authorities.add(resultSmall);
        }
        user.setAuthorities(authorities);
        return CommonResponse.ok(user);
    }


    /**
     * 用户列表分页查询
     *
     * @param page
     * @param size
     * @param name
     * @return
     */
    @GetMapping
    public CommonResponse findByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "20") Integer size,
                                     @RequestParam String name) {
        Map user = new HashMap();
        user.put("name", name);
        PageInfo<User> users = userService.findByPage(page, size, user);
        return CommonResponse.ok(users);
    }

    /**
     * 用户停用.
     *
     * @param code
     * @return
     */
    @PatchMapping("/stop/{code}")
    public CommonResponse stop(@PathVariable String code) {
        User user = userService.findByCode(code);
        String token = user.getToken();
        // 从redis中删除相应用户最后一次登录信息
        if (!StringUtils.isEmpty(token)) {
            redis.del(token);
        }
        Boolean b = userService.stop(code);
        if (!b) {
            return CommonResponse.errorMsg("禁用失败");
        }
        return CommonResponse.build(200, "禁用成功", null);
    }

    /**
     * 用户启用.
     *
     * @param code
     * @return
     */
    @PatchMapping("/start/{code}")
    public CommonResponse start(@PathVariable String code) {
        Boolean b = userService.start(code);
        if (!b) {
            return CommonResponse.errorMsg("启用失败");
        }
        return CommonResponse.build(200, "启用成功", null);
    }


    /**
     * 用户授权.
     *
     * @param userAuthorityDTO
     * @return
     */
    @PostMapping("/acls")
    public CommonResponse createRoleAuthority(@RequestBody UserAuthorityDTO userAuthorityDTO) {

        if (StringUtils.isEmpty(userAuthorityDTO)) {
            return CommonResponse.errorMsg("参数不能为空");
        }
        String userCode = userAuthorityDTO.getUserCode();
        if (StringUtils.isEmpty(userCode)) {
            return CommonResponse.errorMsg("参数userCode不能为空");
        }
        List<String> authorityCodes = userAuthorityDTO.getAuthorityCodes();
        if (StringUtils.isEmpty(authorityCodes)) {
            return CommonResponse.errorMsg("参数authorityCodes不能为空");
        }
        List<String> codes = new ArrayList<>();
        codes.add(userCode);
        userAuthorityService.deleteByUserCodes(codes);
        // 遍历authorityCodes,根据用户code查和权限code查重用户和权限关联表
        for (String authorityCode : authorityCodes) {
            UserAuthority userAuthority1 = new UserAuthority();
            userAuthority1.setAuthorityCode(authorityCode);
            userAuthority1.setUserCode(userCode);
            Boolean b = userAuthorityService.create(userAuthority1);
            if (!b) {
                return CommonResponse.errorMsg("添加用户权限关联失败");
            }
        }
        return CommonResponse.build(200, "用户授权成功", null);
    }

}

