package com.example.demo.exception;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.example.demo.common.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice // 返回json
public class GlobalExceptionHandler {

    private static final Log log = LogFactory.get();


    // 统一异常处理@ExceptionHandler,主要用于Exception  运行时异常
    @ExceptionHandler(RuntimeException.class)
    public Result handler(RuntimeException e) {
        log.error("运行时异常：", e);
        return Result.error("-1", e.getMessage());
    }

    /**
     * 其他异常
     * @param e 异常
     * @return 提示
     */
    @ExceptionHandler(Exception.class)
    public Result handler(Exception e) {
        log.error("系统异常：", e);
        return Result.error("-1", e.getMessage());
    }

    //统一异常处理@ExceptionHandler,主要用于Exception  自定义异常
    @ExceptionHandler(value = CustomException.class)
    public Result handler(CustomException e) {
        log.error("发生业务异常！原因是：{}", e.getMsg());
        return Result.error(e.getCode(), e.getMsg());
    }

    /**
     * 将AuthenticationException异常往上抛，让认证处理器去处理
     * @param authenticationException 认证异常
     * @throws AuthenticationException 认证异常
     */
    @ExceptionHandler(value = AuthenticationException.class)
    public void handler(AuthenticationException authenticationException) throws AuthenticationException {
        throw authenticationException;
    }

    /**
     * 将AuthenticationException异常往上抛，让授权处理器去处理
     * @param accessDeniedException 拒绝访问异常
     * @throws AccessDeniedException 拒绝访问异常
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public void handler(AccessDeniedException accessDeniedException) throws AccessDeniedException {
        log.error("权限不足");
        throw accessDeniedException;
    }

}
