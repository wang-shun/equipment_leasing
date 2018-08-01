package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.User;
import com.yankuang.equipment.authority.service.UserService;
import com.yankuang.equipment.web.util.CommonResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.web.bind.annotation.*;

@Api
@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/users")
public class UserController {

    @RpcConsumer(version = "0.0.1", check = "false")
    private UserService userService;

    @ApiOperation("user login")
    @PostMapping(value = "/login")
    CommonResponse login(String userName, String password) {

        User user = userService.login(userName, password);
        if (user!=null) {
            return CommonResponse.ok(user);
        }
        return CommonResponse.errorMsg("用户名不存在或者密码错误");
    }

    @ApiOperation("user getById")
    @GetMapping(value = "/{id}")
    CommonResponse getById(@PathVariable Long id) {
        return CommonResponse.ok(userService.getById(id));
    }
}

