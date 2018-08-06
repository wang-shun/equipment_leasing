package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.RoleAuthority;
import com.yankuang.equipment.authority.model.RoleUser;
import com.yankuang.equipment.authority.model.User;
import com.yankuang.equipment.authority.service.*;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.web.dto.UserDTO;
import com.yankuang.equipment.web.util.CodeUtil;
import com.yankuang.equipment.web.util.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Api
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private RedisOperator redis;

    @RpcConsumer(version = "0.0.1", check = "false")
    private UserService userService;

    @RpcConsumer
    RoleAuthorityService roleAuthorityService;

    @RpcConsumer
    RoleUserService roleUserService;

    @RpcConsumer
    RoleService roleService;

    @RpcConsumer
    AuthorityService authorityService;


    /**
     * 用户登录loginWithoutToken.
     * @param userName
     * @param password
     * @return
     */
    @ApiOperation("user login")
    @PostMapping(value = "/login")
    CommonResponse login(@RequestParam(defaultValue = "") String userName,
                                     @RequestParam(defaultValue = "") String password,
                                     HttpServletRequest request) {
        UserDTO userDTO = new UserDTO();
        List<Long> authorityIds = new ArrayList<Long>();
        List<Long> roleIds = new ArrayList<Long>();

        // 登录先去redis中查看登陆状态
        String token = request.getHeader("token");
        String userJson = redis.get(token);
        if (!StringUtils.isEmpty(userJson)) {
            redis.expire(token, 1800);
            return CommonResponse.ok(JsonUtils.jsonToPojo(userJson, UserDTO.class));
        }

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return CommonResponse.errorTokenMsg("用户名和密码不能为空");
        }
        User loginUser =  userService.login(userName);
        if (loginUser != null && !password.equals(loginUser.getPassword())) {
            return CommonResponse.errorTokenMsg("密码错误");
        }
        if (loginUser != null && password.equals(loginUser.getPassword())) {
            System.out.println(loginUser);
            UserDTO userResult = getUserDTO(userDTO, authorityIds, roleIds, loginUser);
            // TODO
            token = CodeUtil.getCode();
            redis.set(token, JsonUtils.objectToJson(userDTO), 1800);
            // 密码不返回处理
            userDTO.setPassword("");
            userDTO.setToken(token);
            return CommonResponse.ok(userDTO);
        }
        return CommonResponse.errorTokenMsg("用户不存在");
    }

    private UserDTO getUserDTO(UserDTO userDTO, List<Long> authorityIds, List<Long> roleIds, User loginUser) {
        //拼装最终存入redis的用户信息
        userDTO.setCode(loginUser.getCode());
        userDTO.setId(loginUser.getId());
        userDTO.setName(loginUser.getName());
        userDTO.setPassword(loginUser.getPassword());

        // 用户角色列表
        List<RoleUser> roleUsers = roleUserService.findByUserId(loginUser.getId());
        if (roleUsers == null || roleUsers.size() == 0) {
            return userDTO;
        }

        // 遍历用户角色列表
        for (RoleUser roleUser : roleUsers) {
            roleIds.add(roleUser.getRoleId());
            // 用户角色idlist
            userDTO.setRoleIds(roleIds);
            // 角色权限
            List<RoleAuthority> roleAuthorities =
                    roleAuthorityService.findByRoleId(roleUser.getRoleId());
            for (RoleAuthority roleAuthority : roleAuthorities) {
                authorityIds.add(roleAuthority.getAuthorityId());
            }
        }
        // 用户角色idlist
        userDTO.setAuthorityIds(authorityIds);
        return userDTO;
    }

    /**
     * 根据code查询用户.
     * @param code
     * @return
     */
    @ApiOperation("user findByCode")
    @GetMapping(value = "/{code}")
    CommonResponse findByCode(@PathVariable String code) {
        return CommonResponse.ok(userService.findByCode(code));
    }

    /**
     * 添加用户.
     * @param jsonString
     * @return
     */
    @ApiOperation("user create")
    @PostMapping()
    CommonResponse addUser(@RequestBody String jsonString) {
        if (jsonString == null || "".equals(jsonString)) {
            return CommonResponse.errorMsg("参数不能为空");
        }
        User user = JsonUtils.jsonToPojo(jsonString,User.class);
        user.setCode(CodeUtil.getCode());

        if (user.getMail() == null|| "".equals(user.getMail())) {
            return CommonResponse.errorMsg("mail 不能为空");
        }
        if (user.getTelephone() == null || "".equals(user.getTelephone())) {
            return CommonResponse.errorMsg("telephone 不能为空");
        }
        if (user.getSex() == null || "".equals(user.getSex())) {
            return CommonResponse.errorMsg("性别 不能为空");
        }
        String remark = (user.getRemark() == null || "".equals(user.getRemark())) ? "备注":user.getRemark();
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
}

