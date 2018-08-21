package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.web.dto.UserDTO;
import com.yankuang.equipment.web.util.UserFromRedis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/v1/redis")
public class RedisController {
    private static final Logger log = LoggerFactory.getLogger(RedisController.class);
    @Autowired
    UserFromRedis userFromRedis;

    @RequestMapping
    public CommonResponse getUserRedis() {
        UserDTO userDTO = userFromRedis.findByToken();
        log.info("------------------ 当前登陆人: " + userDTO.getName() + "-------------------------");
        return CommonResponse.ok(userDTO);
    }
}
