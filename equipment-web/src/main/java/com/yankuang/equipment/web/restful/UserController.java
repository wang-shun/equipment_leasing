package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.User;
import com.yankuang.equipment.authority.service.UserService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.web.util.CodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

//@Api
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/users")
public class UserController {

    @RpcConsumer(version = "0.0.1", check = "false")
    private UserService userService;

    //@ApiOperation("user login")
    @PostMapping(value = "/login")
    CommonResponse login(String userName, String password) {

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            return CommonResponse.errorTokenMsg("用户名和密码不能为空");
        }
        User loginUser =  userService.login(userName);
        if (loginUser != null && !password.equals(loginUser.getPassword())) {
            return CommonResponse.errorTokenMsg("密码错误");
        }
        if (loginUser != null && password.equals(loginUser.getPassword())) {
            System.out.println(loginUser);
            //TODO 查询用户详情，角色 存放redis，返回key
            return CommonResponse.ok();
        }
        return CommonResponse.errorTokenMsg("用户不存在");
    }

    @ApiOperation("user findByCode")
    @GetMapping(value = "/{code}")
    CommonResponse findByCode(@PathVariable String code) {
        return CommonResponse.ok(userService.findByCode(code));
    }

    //@ApiOperation("user getById")
    /*@GetMapping(value = "/{id}")
    CommonResponse getById(@PathVariable Long id) {
        return CommonResponse.ok(userService.getById(id));
    }*/
    @ApiOperation("user create")
    @PostMapping()
    CommonResponse addUser(@RequestBody User user) {

        user.setCode(CodeUtil.getCode());
        user.setMail("213@121.COM");
        user.setRemark("some");
        user.setStatus((byte) 1);
        user.setTelephone("13654567865");
        user.setSorting((long) 1);
        user.setVersion((long) 1);
        user.setUpdateBy("admin");
        user.setCreateBy("admin");
        Boolean b = userService.create(user);
        if (b == true) {
        return CommonResponse.ok(user);}
        return CommonResponse.errorTokenMsg("添加失败");
    }
}

