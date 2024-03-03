package com.example.demo.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.demo.constant.GlobalConstants;
import com.example.demo.entity.model.LoginUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * jwt工具类
 */
@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    private static String SECRET_KEY;

    private static Long EXPIRATION_TIME;

    @Autowired
    private RedisCache redisCache;


    //对于静态变量，需要使用set方法才能使用设置好的字段值
    @Value("${jwt.secret}")
    public void setSECRET_KEY(String SECRET_KEY) {
        this.SECRET_KEY = SECRET_KEY;
    }

    @Value("${jwt.expiration}")
    public void setEXPIRATION_TIME(Long expiration) {
        this.EXPIRATION_TIME = expiration;
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 生成JWT  并缓存到redis
     */
    public String createJWT(LoginUser loginUser) {
        String UUID = getUUID(); // 生成UUID
        loginUser.setUUID(UUID);
        refreshToken(loginUser); // 刷新redis缓存中的用户信息
        return getJwtBuilder(loginUser.getUsername(), null, UUID).compact();
    }

    /**
     * 生成jtw
     *
     * @param subject token中要存放的数据（json格式）
     * @return
     */
    public static String createJWT(String subject) {
        JwtBuilder builder = getJwtBuilder(subject, null, getUUID());// 设置过期时间
        return builder.compact();
    }

    /**
     * 生成jtw
     *
     * @param subject   token中要存放的数据（json格式）
     * @param ttlMillis token超时时间
     * @return
     */
    public static String createJWT(String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, getUUID());// 设置过期时间
        return builder.compact();
    }

    /**
     * 获得JWT构建者
     * @param subject
     * @param ttlMillis
     * @param uuid
     * @return
     */
    private static JwtBuilder getJwtBuilder(String subject, Long ttlMillis, String uuid) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey secretKey = generalKey();
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        if (ttlMillis == null) {
            ttlMillis = EXPIRATION_TIME;
        }
        long expMillis = nowMillis + ttlMillis;
        Date expDate = new Date(expMillis);
        return Jwts.builder()
                .setId(uuid)              //唯一的ID
                .setSubject(subject)   // 主题  可以是JSON数据
                .setIssuer("hzx")     // 签发者
                .setIssuedAt(now)      // 签发时间
                .signWith(signatureAlgorithm, secretKey) //使用HS256对称加密算法签名, 第二个参数为秘钥
                .setExpiration(expDate);
    }

    /**
     * 创建token
     *
     * @param id
     * @param subject
     * @param ttlMillis
     * @return
     */
    public static String createJWT(String id, String subject, Long ttlMillis) {
        JwtBuilder builder = getJwtBuilder(subject, ttlMillis, id);// 设置过期时间
        return builder.compact();
    }

    /**
     * 生成加密后的秘钥 secretKey
     *
     * @return
     */
    public static SecretKey generalKey() {
        byte[] encodedKey = Base64.getDecoder().decode(SECRET_KEY);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }

    /**
     * 解析
     *
     * @param jwt
     * @return
     */
    public static Claims parseJWT(String jwt) {
        SecretKey secretKey = generalKey();
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(jwt)
                .getBody();
    }

    /**
     * 校验令牌：1.头部信息和载荷信息是否被篡改 2.校验令牌是否过期
     * @param jwtToken 令牌
     * @return 令牌是否通过校验
     */
    public static boolean isVerifiedToken(String jwtToken) {
        // 带着签名构建校验对象
        Algorithm algorithm = Algorithm.HMAC256(generalKey().getEncoded());
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        // 校验
        jwtVerifier.verify(jwtToken); // 不通过则会抛出异常
        return true;
    }

    /**
     * 检查token是否过期
     * @param jwtToken token
     * @return 是否过期
     * @throws Exception 异常
     */
    public static boolean verifyExpired(String jwtToken) throws Exception {
        return parseJWT(jwtToken).getExpiration().before(new Date());
    }

    /**
     * 根据token获得登录的用户
     * @param token 令牌
     * @return 登录用户信息
     */
    public LoginUser getLoginUser(String token) {
        LoginUser loginUser = null;
        try {
            Claims claims = parseJWT(token);
            String UUID = claims.getId();
            String tokenKey = genTokenKey(UUID);
            loginUser = redisCache.getCacheObject(tokenKey);
        } catch (Exception ignored) {

        }
        return loginUser;
    }

    /**
     * 设置当前登录的用户
     * @param loginUser 登录用户
     */
    public void setLoginUser(LoginUser loginUser) {
        if (loginUser != null && StringUtils.isNoneEmpty(loginUser.getUUID())) {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新redis中缓存的用户信息
     * @param loginUser 登录用户
     */
    public void refreshToken(LoginUser loginUser) {
        String key = genTokenKey(loginUser.getUUID());
        redisCache.setCacheObject(key, loginUser, EXPIRATION_TIME, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取要存储在redis的key
     * @param UUID 唯一的ID
     * @return key
     */
    public static String genTokenKey(String UUID) {
        return GlobalConstants.REDIS_USER_DETAILS_PREFIX + UUID;
    }

    /**
     * 判断密码是否相同
     * @param rawPassword 真实的密码
     * @param encodedPassword 加密后的密码
     * @return 是否相同
     */
    public static boolean matchPassword(String rawPassword, String encodedPassword) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
