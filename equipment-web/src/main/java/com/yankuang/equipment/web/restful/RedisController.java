package com.yankuang.equipment.web.restful;

import com.yankuang.equipment.common.util.CommonResponse;
import com.yankuang.equipment.common.util.JsonUtils;
import com.yankuang.equipment.web.util.RedisOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

@RestController
@RequestMapping("/v1/redis")
public class RedisController {
    private static final Logger log = LoggerFactory.getLogger(RedisController.class);
    @Autowired
    private RedisOperator redis;

    @RequestMapping
    public CommonResponse getUserRedis(@RequestParam String token) {
        String userRedis = redis.get(token);

        if (StringUtils.isEmpty(userRedis)) {
            return CommonResponse.errorTokenMsg("未登录");
        }
        final Base64.Decoder decoder = Base64.getDecoder();
        String decoderResult = null;
        try {
            decoderResult = new String(decoder.decode(userRedis), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        log.info("----- 解码后内容 : " + decoderResult + "------------");
        // 刷新token时长
        redis.expire(token, 1800);
        return CommonResponse.ok(decoderResult);
    }
}
