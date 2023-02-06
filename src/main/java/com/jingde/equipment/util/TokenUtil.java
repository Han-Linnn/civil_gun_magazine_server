package com.jingde.equipment.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jingde.equipment.core.exception.AuthException;
import com.jingde.equipment.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
//import java.util.Date;
import javax.annotation.Resource;

import static com.jingde.equipment.constant.ServiceConstant.*;

/**
 * 令牌工具类，JWT
 * @author
 */
@Component
public class TokenUtil {
    private final Logger logger = LoggerFactory.getLogger(TokenUtil.class);
    @Resource
    private RedisUtil redisUtil;

    // 生成 token
    public String generateToken(User user) {
        // TODO: 过期时间 withExpiresAt(new Date((new Date()).getTime() + 7 * 24 * 60 * 60 * 1000))
        return JWT.create()
                // 将 user id 保存到 token 里面
                .withAudience(user.getId().toString())
                // token 的密钥
                .sign(Algorithm.HMAC256(TOKEN_SECRET));
    }

    // 获取 token 中的 userId
    public Integer getUserId(String token) throws AuthException {
        // 验证token
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build();
        try {
            verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new AuthException("token无效，请重新登录");
        }
        int userId;
        try {
            // 解码出token中的userId
            DecodedJWT jwt = JWT.decode(token);
            userId = Integer.parseInt(jwt.getAudience().get(0));
        } catch (JWTDecodeException e) {
            throw new AuthException("token无效，请重新登录");
        }
        // 验证 redis token
        String key = String.format("access_token%d", userId);
        String cacheToken = (String) redisUtil.get(key);
        if (cacheToken != null && cacheToken.equals(token)) {
            return userId;
        }
        throw new AuthException("token无效，请重新登录");
    }
}
