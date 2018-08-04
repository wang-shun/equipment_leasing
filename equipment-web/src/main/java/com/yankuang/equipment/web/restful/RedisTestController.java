package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.authority.model.User;
import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.web.util.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("redis")
public class RedisTestController {

    @Autowired
    private StringRedisTemplate strRedis;

    @Autowired
    private RedisOperator redis;

    @RequestMapping("/test")
    public CommonResponse test() {

        strRedis.opsForValue().set("kcx", "test项目~~~~~~");

        User user = new User();
        user.setName("破面");
        user.setPassword("123456");

        strRedis.opsForValue().set("json:user", JsonUtils.objectToJson(user));

        User jsonUser = JsonUtils.jsonToPojo(strRedis.opsForValue().get("json:user"), User.class);

        return CommonResponse.ok(jsonUser);
    }

    @RequestMapping("/getJsonList")
    public CommonResponse getJsonList() {

        User user1 = new User();
        user1.setName("破面");
        user1.setPassword("123456");

        User user2 = new User();
        user2.setPassword("123456");
        user2.setName("小奥");

        User user3 = new User();
        user3.setPassword("123456");
        user3.setName("小银");

        List<User> userList = new ArrayList<User>();
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);

        redis.set("json:info:userlist", JsonUtils.objectToJson(userList), 2000);

        String userListJson = redis.get("json:info:userlist");
        List<User> userListBorn = JsonUtils.jsonToList(userListJson, User.class);

        return CommonResponse.ok(userListBorn);
    }
}
