package com.linjiahao.security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("")
    public String index() {
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
