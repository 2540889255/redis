package com.aynu.redis.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

@Configuration
public class InternationalInterceptor implements HandlerInterceptor {

    private LocaleChangeInterceptor localeChangeInterceptor=null;

    @Bean
    public LocaleResolver initLocaleResolver(){
        SessionLocaleResolver sessionLocaleResolver=new SessionLocaleResolver();
        //默认的国际化区域
        sessionLocaleResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        System.out.println("国际化生效了" +
                "");
        return sessionLocaleResolver;
    }
    //创建国际化拦截器
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor(){
        if (localeChangeInterceptor!=null){
            return localeChangeInterceptor;
        }
        localeChangeInterceptor=new LocaleChangeInterceptor();
        //设置参数名字
        localeChangeInterceptor.setParamName("language");
        return localeChangeInterceptor;
    }

    //给处理器增加国际化拦截器

}
