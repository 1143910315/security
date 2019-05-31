package com.linjiahao.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

@SpringBootApplication
public class SecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecurityApplication.class, args);
    }

    /**
     * 配置CSRF过滤器
     *
     * @return {@link org.springframework.boot.web.servlet.FilterRegistrationBean}
     */
    @Bean
    public FilterRegistrationBean<CsrfFilter> csrfFilter() {
        FilterRegistrationBean<CsrfFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new CsrfFilter(new HttpSessionCsrfTokenRepository()));
        registration.addUrlPatterns("/*");
        registration.setName("csrfFilter");
        return registration;
    }
}
