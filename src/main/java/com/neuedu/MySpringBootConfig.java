package com.neuedu;

import com.neuedu.interceptor.AdminInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootConfiguration
public class MySpringBootConfig implements WebMvcConfigurer {
    @Autowired
    AdminInterceptor adminInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/user/**","/order/**","/product/**","/cart/**","/cartgory/**","/shipping/**")
                .excludePathPatterns("/user/login.do","/user/register.do","/user/check_valid.do","/user/forget_get_question.do",
                        "/user/forget_check_answer.do","/user/forget_reset_password.do","/user/logout.do","/user/res","/product/list.do");
    }
}
