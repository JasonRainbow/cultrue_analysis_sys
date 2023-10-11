package com.example.demo.config.authentication;

import cn.hutool.core.util.StrUtil;
import com.example.demo.common.Result;
import com.example.demo.constant.GlobalConstants;
import com.example.demo.entity.model.LoginUser;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.exception.CustomException;
import com.example.demo.utils.JsonUtil;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.RedisCache;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 *  ${认证过滤器}
 */
@Component
public class MyOncePerRequestFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(MyOncePerRequestFilter.class);

    @Value("${jwt.tokenHeader}")
    private String header;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        // header的值是在yml文件中定义的 “Authorization”
        String token = request.getHeader(header);
//        System.out.println("MyOncePerRequestFilter-token = " + token);
        LoginUser loginUser = jwtUtil.getLoginUser(token);
        if (loginUser != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
//                    System.out.println(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        /*if (!StrUtil.isEmpty(token)) {
            String username = null;
            LoginUser authUser = null;
            try {
                Claims claims = JwtUtil.parseJWT(token); // 这里会检查token是否过期，过期会抛出异常
                username = claims.getSubject();
                authUser = redisCache.getCacheObject(GlobalConstants.REDIS_USER_DETAILS_PREFIX + username);
            } catch (Exception ignored) {

            }
            if (username != null && authUser != null) { // 携带了token并且能够正常解析
                String redisToken = redisCache.getCacheObject(GlobalConstants.REDIS_TOKEN_PREFIX + username);
//            System.out.println("MyOncePerRequestFilter-redisToken = " + redisToken);
                if (StrUtil.isEmpty(redisToken)) {
                    //输出JSON
                    JsonUtil.writeJson(request,response, Result.error(ResponseStatusEnum.TOKEN_EXPIRED.getCode(),
                            ResponseStatusEnum.TOKEN_EXPIRED.getMsg()));
                    return;
                }

                //对比前端发送请求携带的的token是否与redis中存储的一致
                if (redisToken.equals(token)) {
//                System.out.println("MyOncePerRequestFilter-authUser = " + authUser);
                    checkTokenExpiration(redisToken, username);
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(authUser, null, authUser.getAuthorities());
//                    System.out.println(authenticationToken);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                } else {

                }
            }
        }*/
        chain.doFilter(request, response);
    }

    /**
     * 检查token是否过期，如果过期就抛出异常并把redis中缓存的token删除
     * @param redisToken token
     * @param username 用户名
     */
    private void checkTokenExpiration(String redisToken, String username) {
        boolean isExpired = false;
        try {
            isExpired = JwtUtil.verifyExpired(redisToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isExpired) {
            // 删除缓存的token
            redisCache.deleteObject(GlobalConstants.REDIS_TOKEN_PREFIX + username);
            // 抛出自定义异常
            throw new CustomException(ResponseStatusEnum.TOKEN_EXPIRED);
        }
    }
}
