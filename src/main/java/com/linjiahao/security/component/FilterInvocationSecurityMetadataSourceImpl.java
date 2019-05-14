package com.linjiahao.security.component;

import com.linjiahao.security.bean.Resource;
import com.linjiahao.security.bean.Role;
import com.linjiahao.security.service.ResourceService;
import com.linjiahao.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
//接收用户请求的地址，返回访问该地址需要的所有权限
public class FilterInvocationSecurityMetadataSourceImpl implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private ResourceService resourceService;
    @Autowired
    private RoleService roleService;

    @Override
    //接收用户请求的地址，返回访问该地址需要的所有权限
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        //得到用户的请求地址,控制台输出一下
        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        System.out.println("用户请求的地址是：" + requestUrl);
        //如果登录页面就不需要权限
        if ("/".equals(requestUrl)) {
            return null;
        }
        Resource resource = resourceService.getResourceByUrl(requestUrl);
        //如果没有匹配的url则说明大家都可以访问
        if (resource == null) {
            return SecurityConfig.createList("ROLE_LOGIN");
        }
        //从RoleService获取Resource拥有的权限
        List<Role> roles = roleService.getRolesOfResource(resource.getId());
        int size = roles.size();
        String[] values = new String[size];
        for (int i = 0; i < size; i++) {
            values[i] = roles.get(i).getRolename();
        }
        return SecurityConfig.createList(values);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}
