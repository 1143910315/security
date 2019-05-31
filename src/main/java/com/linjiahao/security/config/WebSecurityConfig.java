package com.linjiahao.security.config;

import com.linjiahao.security.component.*;
import com.linjiahao.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    //根据一个url请求，获得访问它所需要的roles权限
    private final FilterInvocationSecurityMetadataSourceImpl myFilterInvocationSecurityMetadataSource;
    //接收一个用户的信息和访问一个url所需要的权限，判断该用户是否可以访问
    private final AccessDecisionManagerImpl myAccessDecisionManager;
    //403页面
    private final AccessDeniedHandlerImpl myAccessDeniedHandler;

    @Autowired
    public WebSecurityConfig(UserService userService, FilterInvocationSecurityMetadataSourceImpl myFilterInvocationSecurityMetadataSource, AccessDecisionManagerImpl myAccessDecisionManager, AccessDeniedHandlerImpl myAccessDeniedHandler) {
        this.userService = userService;
        this.myFilterInvocationSecurityMetadataSource = myFilterInvocationSecurityMetadataSource;
        this.myAccessDecisionManager = myAccessDecisionManager;
        this.myAccessDeniedHandler = myAccessDeniedHandler;
    }

    /**
     * 定义认证用户信息获取来源，密码校验规则等
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //注入userDetailsService，需要实现userDetailsService接口
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //在这里配置哪些页面不需要认证
    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/", "/login.html", "/register.html", "/noAuthenticate/**", "/favicon.ico");
    }

    /**
     * 定义安全策略
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //配置安全策略
        http.authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                        o.setSecurityMetadataSource(myFilterInvocationSecurityMetadataSource);
                        o.setAccessDecisionManager(myAccessDecisionManager);
                        return o;
                    }
                })
                .and()
                .formLogin()
                .loginPage("/login.html")  // 未登录跳转页面
                .loginProcessingUrl("/login.json")  // 自定义的登录接口
                .usernameParameter("username")  // 登录请求参数的用户名参数名
                .passwordParameter("password")  // 登录请求参数的密码参数名
                .permitAll()
                .failureHandler(new AuthenticationFailureHandlerImpl())
                .successHandler(new AuthenticationSuccessHandlerImpl())
                .and()
                .logout()
                .permitAll()
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler);
    }
}
