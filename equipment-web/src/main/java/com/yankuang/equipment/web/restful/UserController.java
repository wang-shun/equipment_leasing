package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.User;
import com.yankuang.equipment.authority.service.UserService;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.web.util.CodeUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.terminus.boot.rpc.common.annotation.RpcConsumer;
import org.springframework.util.StringUtils;
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

