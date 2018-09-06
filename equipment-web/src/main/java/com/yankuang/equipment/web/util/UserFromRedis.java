package com.yankuang.equipment.web.util;

import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.web.dto.UserDTO;
import com.yankuang.equipment.web.log.WebLogAspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

@Component
public class UserFromRedis {

    private final Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

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
        // 解密redis中获取的用户信息
//        final Base64.Decoder decoder = Base64.getDecoder();
//        String decoderResult = null;
//        try {
//            decoderResult = new String(decoder.decode(userRedis), "UTF-8");
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        logger.info("----- 解码后内容 : " + decoderResult + "------------");
        // json转对象
        UserDTO userFromRedis = JsonUtils.jsonToPojo(userRedis, UserDTO.class);

        return userFromRedis;
    }

}
