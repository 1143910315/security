package com.linjiahao.security.component;

import com.linjiahao.security.data.JsonMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户账号或密码校验成功时的操作
 */
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException {
        RequestCache requestCache = new HttpSessionRequestCache();
        SavedRequest request = requestCache.getRequest(httpServletRequest, httpServletResponse);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        JsonMessage jsonMessage = new JsonMessage();
        jsonMessage.setStatus(0);
        jsonMessage.setMessage("登录成功");
        if (request != null) {
            jsonMessage.setUrl(request.getRedirectUrl());// 重定向到被拦截跳转页面
        } else {
            jsonMessage.setUrl("/");// 重定向到首页
        }
        out.write(jsonMessage.toString());
        out.flush();
        out.close();
    }
}
