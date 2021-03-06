package com.linjiahao.security.controller;

import com.linjiahao.security.bean.Role;
import com.linjiahao.security.bean.User;
import com.linjiahao.security.data.JsonMessage;
import com.linjiahao.security.service.RoleService;
import com.linjiahao.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/noAuthenticate/register.json")
    public JsonMessage register(@RequestParam("username") String username, @RequestParam("password") String password) {
        JsonMessage jsonMessage = new JsonMessage();
        if (username.length() < 6) {
            jsonMessage.setStatus(2);
            jsonMessage.setMessage("用户名长度最少为6个字符");
            return jsonMessage;
        }
        if (username.length() > 18) {
            jsonMessage.setStatus(2);
            jsonMessage.setMessage("用户名长度最多为18个字符");
            return jsonMessage;
        }
        if (password.length() < 6) {
            jsonMessage.setStatus(2);
            jsonMessage.setMessage("密码长度最少为6个字符");
            return jsonMessage;
        }
        if (password.length() > 18) {
            jsonMessage.setStatus(2);
            jsonMessage.setMessage("密码长度最多为18个字符");
            return jsonMessage;
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        password = passwordEncoder.encode(password);
        try {
            User user = userService.getUserByUsername(username);
            if (user == null) {
                Role role = roleService.getRoleByRolename("Role_user");
                userService.addUser(username, password, role);
                jsonMessage.setStatus(0);
                jsonMessage.setUrl("/login.html");
                jsonMessage.setMessage("注册成功");
            } else {
                jsonMessage.setStatus(1);
                jsonMessage.setMessage("用户已存在");
            }
        } catch (Exception e) {
            jsonMessage.setException(e);
        }
        return jsonMessage;
    }

//    @RequestMapping("/noAuthenticate/logout.json")
//    public JsonMessage logout(HttpServletRequest request) {
//        JsonMessage jsonMessage = new JsonMessage();
//        jsonMessage.setUrl("/");
//        jsonMessage.setMessage("您已退出登录");
//        jsonMessage.setStatus(0);
//        System.out.println(request.getMethod());
//        return jsonMessage;
//    }
}
