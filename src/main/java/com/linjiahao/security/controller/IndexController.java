package com.linjiahao.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {
    @GetMapping("")
    public String index(HttpServletRequest request) {
        System.out.println(request.getSession().getMaxInactiveInterval());
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) request
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if (securityContextImpl != null) {
            Authentication authentication = securityContextImpl.getAuthentication();
            if (authentication == null) {
                System.out.println("error");
            } else {
                System.out.println("success");
            }
        }
        return "index/index";
    }

    @GetMapping("/login.html")
    public String login() {
        return "index/login";
    }

    @GetMapping("/register.html")
    public String register() {
        return "index/register";
    }
}
