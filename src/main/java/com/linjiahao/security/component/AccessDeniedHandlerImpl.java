package com.linjiahao.security.component;

import com.linjiahao.security.data.JsonMessage;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
//自定义403响应内容
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        JsonMessage jsonMessage=new JsonMessage();
        jsonMessage.setMessage("权限不足，请联系管理员!");
        jsonMessage.setStatus(-1);
        out.write(jsonMessage.toString());
        out.flush();
        out.close();
    }
}
