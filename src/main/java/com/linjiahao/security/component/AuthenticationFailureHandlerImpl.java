package com.linjiahao.security.component;

import com.linjiahao.security.data.JsonMessage;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户账号或密码校验失败时的操作
 */
public class AuthenticationFailureHandlerImpl implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        JsonMessage jsonMessage = new JsonMessage();
        jsonMessage.setStatus(-1);
        if (e instanceof UsernameNotFoundException || e instanceof BadCredentialsException) {
            jsonMessage.setMessage("用户名或密码输入错误，登录失败!");
        } else if (e instanceof DisabledException) {
            jsonMessage.setMessage("账户被禁用，登录失败，请联系管理员!");
        } else {
            jsonMessage.setMessage("登录失败!");
        }
        out.write(jsonMessage.toString());
        out.flush();
        out.close();
    }
}
