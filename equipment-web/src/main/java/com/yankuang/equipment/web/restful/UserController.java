package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.RoleAuthority;
import com.yankuang.equipment.authority.model.RoleUser;
import com.yankuang.equipment.authority.model.User;
import com.yankuang.equipment.authority.service.RoleAuthorityService;
import com.yankuang.equipment.authority.service.RoleUserService;
import com.yankuang.equipment.authority.service.UserService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.web.dto.UserDTO;
import com.yankuang.equipment.web.util.CodeUtil;
import com.yankuang.equipment.web.util.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import io.terminus.common.model.Paging;
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

    @Autowired
    private RedisOperator redis;

    @RpcConsumer(version = "0.0.1", check = "false")
    private UserService userService;

    @RpcConsumer
    RoleAuthorityService roleAuthorityService;

    @RpcConsumer
    RoleUserService roleUserService;

    /**
     * 用户退出登录.
     */
    @PostMapping(value = "/loginOut")
    CommonResponse loginOut(HttpServletRequest request){
        // 登录先去redis中查看登陆状态
        String token = request.getHeader("token");
        redis.del(token);
        return CommonResponse.build(200, "您已经成功退出登录", null);
    }

    /**
     * 用户登录.
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
        final Base64.Decoder decoder = Base64.getDecoder();
        // 登录先去redis中查看登陆状态
        String token = request.getHeader("token");
        String userJson = redis.get(token);
        if (!StringUtils.isEmpty(userJson)) {
            redis.expire(token, 1800);
            // 解密
            try {
                String decoderResult = new String(decoder.decode(userJson), "UTF-8");
                System.out.println(decoderResult);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return CommonResponse.ok(token);
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
            String result = getUserDTO(userDTO, authorityIds, roleIds, loginUser);
            token = CodeUtil.getCode();

            final Base64.Encoder encoder = Base64.getEncoder();
            String encodedResult = "";
            try {

                final byte[] textByte = result.getBytes("UTF-8");
                // 加密
                encodedResult = encoder.encodeToString(textByte);
                System.out.println(encodedResult);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            redis.set(token, encodedResult, 1800);

            return CommonResponse.ok(token);
        }
        return CommonResponse.errorTokenMsg("用户不存在");
    }

    private String getUserDTO(UserDTO userDTO, List<Long> authorityIds, List<Long> roleIds, User loginUser) {

        // 用户角色列表
        List<RoleUser> roleUsers = roleUserService.findByUserId(loginUser.getId());
        if (roleUsers == null || roleUsers.size() == 0) {
            return "";
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
        System.out.println(userDTO.toString());
        return userDTO.toString();
    }

    /**
     * 根据id查询用户.
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

    /**
     * 添加用户.
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
        if (user.getId() == null|| "".equals(user.getId())) {
            return CommonResponse.errorMsg("id 不能为空");
        }
        //todo
        user.setUpdateBy("登陆人");
        Boolean b = userService.update(user);
        return CommonResponse.errorTokenMsg("更新失败");
    }

    /**
     * 用户列表分页查询
     * @param offset
     * @param limit
     * @param searchInput
     * @return
     */
    @GetMapping
    public CommonResponse paging(@RequestParam(value = "page", defaultValue = "1") Integer offset,
                                 @RequestParam(value = "size", defaultValue = "20")Integer limit,
                                 @RequestParam String searchInput){
        User user = new User();
//        user.setName(searchInput);
        Paging<User> users = userService.paging(offset, limit, user);
        return CommonResponse.ok(users);
    }
}

