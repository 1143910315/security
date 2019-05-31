package com.linjiahao.security.service;

import com.linjiahao.security.bean.User;
import com.linjiahao.security.bean.UserDetailsImpl;
import com.linjiahao.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
//框架需要使用到一个实现了UserDetailsService接口的类
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, RoleService roleService) {
        this.userRepository = userRepository;
        this.roleService = roleService;
    }

    @Transactional
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    @Transactional
    public User getUserByUsername(String username) {
        return userRepository.findFirstByUsername(username);
    }

    @Transactional
    public void addUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userRepository.saveAndFlush(user);
    }

    @Override
    //重写UserDetailsService接口里面的抽象方法
    //根据用户名 返回一个UserDetails的实现类的实例
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("查找用户：" + s);
        User user = getUserByUsername(s);
        if (user == null) {
            throw new UsernameNotFoundException("没有该用户");
        }
        //查到User后将其封装为UserDetails的实现类的实例供程序调用
        //用该User和它对应的Role实体们构造UserDetails的实现类
        return new UserDetailsImpl(user, roleService.getRolesOfUser(user.getId()));
    }
}
