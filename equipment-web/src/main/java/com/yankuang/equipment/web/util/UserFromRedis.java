package com.yankuang.equipment.web.util;

import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.web.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserFromRedis {

    @Autowired
    RedisOperator redis;

    public UserDTO findByToken() {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("token");
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        String userRedis = (String) redis.get(token);
        if (StringUtils.isEmpty(userRedis)) {
            return null;
        }

        UserDTO userFromRedis = JsonUtils.jsonToPojo(userRedis, UserDTO.class);

        return userFromRedis;
    }

}
