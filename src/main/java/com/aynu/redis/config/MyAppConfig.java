package com.aynu.redis.config;

import com.aynu.redis.interceptor.Interceptor1;
import com.aynu.redis.interceptor.InternationalInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyAppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器到springmvc机制  然后他会返回一个拦截器注册

        InterceptorRegistration interceptorRegistration=registry.addInterceptor(new Interceptor1());
        interceptorRegistration.addPathPatterns("/login/*");
        InterceptorRegistration internationalInterceptor = registry.addInterceptor(new InternationalInterceptor());
        internationalInterceptor.addPathPatterns("/*").excludePathPatterns("/static/**");
    }
    /**
     *增加映射关系
     *
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //使得  /login/page的映射为 login.jsp
        registry.addViewController("/login/page").setViewName("login");
        //使得/logout/page映射为 logout_welcome.jsp
        registry.addViewController("/logout/page").setViewName("logout_welcome");
        //使得logout映射为logout.jsp
        registry.addViewController("/logout").setViewName("logout");
    }
}
