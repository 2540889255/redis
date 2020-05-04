package com.aynu.redis.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //密码编译器
        System.out.println("SpringSecurityConfig启动了");
        PasswordEncoder  passwordEncoder=new BCryptPasswordEncoder();
        //使用内存存储
        auth.inMemoryAuthentication()
        //设置密码编译器
        .passwordEncoder(passwordEncoder)
        //注册用户admin 密码为 123456 ,并赋予USER 和ADMIN的角色
        .withUser("user")
                .password("$2a$10$JjR6K0qatBiGEQxxKjF77uKL7qOGCJj2g7xfcn1LSgCx/Qt0VEu2i")
                .roles("USER","ADMIN")
        //连接方法
                .and()
        .withUser("user1")
                .password("$2a$10$JjR6K0qatBiGEQxxKjF77uKL7qOGCJj2g7xfcn1LSgCx/Qt0VEu2i")
                .roles()
                .and()
        .withUser("user3")
                .password("$2a$10$JjR6K0qatBiGEQxxKjF77uKL7qOGCJj2g7xfcn1LSgCx/Qt0VEu2i")
                .roles();
        super.configure(auth);
    }

    /**
     * 下面的方法是限制用户的请求
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //只要通过了验证就可以访问所有请求
        //authorizeRequests方法限定支队签名成功的用户请求
        //anyrequest方法限定所有的请求
        //authenticated方法对所有签名成功的用户允许方法
        /*http.authorizeRequests()//.anyRequest().authenticated()
                //and是连接词 formlogin代表使用的是spring security默认的界面
                .antMatchers("/user/login").hasAnyRole("USER","ADMIN")
                .antMatchers("/admin/login").hasAuthority("ROLE_ADMIN")

                //其他请求允许签名后访问
                .anyRequest().permitAll()
            .and().formLogin()
                //httpbase 说明启动HTTP基础验证
            .and().httpBasic();*/
        /**
         * 这一段是使用HTTPS验证
         *
         * http.requiresChannel()//requireChannel说明使用通道  requiresSecure表明使用HTTPS请求
         *                 .antMatchers("/user/login").requiresSecure()
         *                 .antMatchers("/admin").requiresInsecure()//requireInstance表明取消使用HTTPS请求
         *                 .and().formLogin()
         *                 .and().httpBasic();
         */
        http.authorizeRequests().antMatchers("/admin/**").access("hasRole('ADMIN')")
                //启用remember功能
            .and().rememberMe().tokenValiditySeconds(86400).key("remember-me-key")
                //启动HTTPbase功能
            .and().httpBasic()
                //通过签名后可以访问任何请求
            .and().authorizeRequests().antMatchers("/**").permitAll()
                //设置登录页和默认的跳转路径
            .and().formLogin().loginPage("/login/page")
                .defaultSuccessUrl("/admin/welcome")
            .and().logout().logoutUrl("/logout/page")
                .logoutSuccessUrl("/welcome");

    }
}
