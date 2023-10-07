package com.example.demo.service.impl;

import com.example.demo.common.Result;
import com.example.demo.constant.GlobalConstants;
import com.example.demo.entity.dto.LoginDto;
import com.example.demo.entity.model.LoginUser;
import com.example.demo.enums.ResponseStatusEnum;
import com.example.demo.exception.CustomException;
import com.example.demo.service.LoginService;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Value("${jwt.tokenHead}")
    private String tokenHead; // 放在请求头部中的token字段名

    @Autowired
    private AuthenticationManager authenticationManager; // 认证管理器

    @Autowired
    private UserDetailsService userDetailsService; // 获取用户详细信息的服务

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder; // 强哈希散列加密编码器

    @Autowired
    private RedisCache redisCache;

    /**
     * 登录方法
     * @param loginDto 登录用户对象
     * @return json响应返回结果
     */
    @Override
    public Result doLogin(LoginDto loginDto) {
        /**
         * 因为使用了全局异常处理，GlobalExceptionHandler会自动捕获controller层抛出的异常
         * authenticationManager.authenticate 这个方法 如果认证失败会抛出AuthenticationException异常
         * 认证失败处理器无法获取到AuthenticationException异常，因为全局异常处理器已经捕获
         * 然后AuthenticationException异常不属于ServiceException，所以会返回500，服务器响应错误
         */
        // 进行认证 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
        Authentication authentication = null;
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        try {
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) { // 捕获掉authenticate这个方法抛出的异常，防止掩盖掉UserDetailsService的loadUserByUsername抛出的自定义异常
            if (e instanceof BadCredentialsException) {
                throw new CustomException(ResponseStatusEnum.USERNAME_PASSWORD_ERROR);
            } else {
                // 构造一个新的自定义异常类
                CustomException customException = new CustomException(ResponseStatusEnum.ERROR);
                customException.setMsg(e.getMessage());
                throw customException;
            }
        }

        if (Objects.isNull(authentication)) {
            // 用户名或密码错误
            throw new CustomException(ResponseStatusEnum.USERNAME_PASSWORD_ERROR);
        }
        // 此时已经认证成功
        LoginUser authUser = (LoginUser) authentication.getPrincipal();
        String username = authUser.getUsername();
        String token = JwtUtil.createJWT(username);

        // 把token和用户信息存到redis中
        redisCache.setCacheObject(GlobalConstants.REDIS_TOKEN_PREFIX + username, token);
        redisCache.setCacheObject(GlobalConstants.REDIS_USER_DETAILS_PREFIX + username, authUser);
//        System.out.println(authUser);

        // 将用户存入上下文中
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        Result result = Result.success(ResponseStatusEnum.LOGIN_SUCCESS.getCode(),
                ResponseStatusEnum.LOGIN_SUCCESS.getMsg());
        result.put(GlobalConstants.TOKEN, token);

        return result;
    }

    /**
     * 退出登录方法
     * @return json响应结果
     */
    @Override
    public Result doLogout() {
        // 从上下文中取出Authentication认证对象
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser authUser = (LoginUser) authentication.getPrincipal(); // 获取用户对象
        String username = authUser.getUsername(); // 获取用户名

        // 删除redis中存的关于这个用户的信息
        redisCache.deleteObject(GlobalConstants.REDIS_TOKEN_PREFIX + username);
        redisCache.deleteObject(GlobalConstants.REDIS_USER_DETAILS_PREFIX + username);

        return Result.success(ResponseStatusEnum.LOGOUT_SUCCESS.getCode(),
                ResponseStatusEnum.LOGOUT_SUCCESS.getMsg());
    }
}
