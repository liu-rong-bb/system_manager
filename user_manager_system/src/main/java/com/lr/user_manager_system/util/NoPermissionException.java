package com.lr.user_manager_system.util;

import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.crypto.CryptoException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class NoPermissionException {
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public String handleShiroException(Exception ex) {
        System.out.println("无权限");
        return "无权限";
    }
    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public String AuthorizationException(Exception ex) {
        System.out.println("权限认证失败");
        return "权限认证失败";
    }

    @ResponseBody
    @ExceptionHandler(UnavailableSecurityManagerException.class)
    public Result UnavailableSecurityManagerException(Exception ex){
        System.out.println("输入错误");
        return new Result(400,"输入错误");
    }

    @ResponseBody
    @ExceptionHandler(CryptoException.class)
    public Result CryptoException(Exception ex){
        System.out.println("未登录");
        return new Result(400,"输入错误");
    }
}
