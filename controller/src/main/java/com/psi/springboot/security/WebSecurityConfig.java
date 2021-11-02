package com.psi.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 自定义配置类配置注册安全策略信息
 */
@Configuration
@EnableWebSecurity  //spring校验的组合注解
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //获取用户基本信息的服务
    @Autowired
    private UserSecurityService userSecurityService;
    //密码处理器
    @Autowired
    private MyPasswordEncoder myPasswordEncoder;
    //认证成功后的处理器
    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;
    //认证失败后的处理器
    @Autowired
    private MyAuthenticationFailHandler myAuthenticationFailHandler;
    //授权失败后的处理器
    @Autowired
    private MyAuthenticationAccessDeniedHandler myAuthenticationAccessDeniedHandler;

    /**
     * 注册认证信息
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //注入用户信息服务和加密处理器
        auth.userDetailsService(userSecurityService).passwordEncoder(myPasswordEncoder);
    }

    /**
     * 注册静态资源处理方式
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        //设置可访问的静态资源
        web.ignoring().antMatchers("/css/**", "/img/**", "/js/**",
                "/loginstyle/**", "/plugins/**", "/template/**");
    }

    /**
     * 注册安全策略
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //安全策略配置
        http.authorizeRequests().antMatchers("/menu/getMenus").authenticated()//登录后可访问
                .antMatchers("/pages/ordersetting.html").hasRole("ADMIN")//拥有ROLE_ADMIN权限才能访问
                .anyRequest().authenticated()//其他请求登录即可访问
                .and().formLogin()
                .loginPage("/index.html")//登录页面
                .loginProcessingUrl("/user/login")//登录请求映射路径
                .usernameParameter("username")//默认使用的用户名参数
                .passwordParameter("password")//默认使用的密码参数
                .successHandler(myAuthenticationSuccessHandler)//登录成功后的处理器
                .failureHandler(myAuthenticationFailHandler)//登录失败后的处理器
                .permitAll()
                .and().csrf().disable()//关闭csrf防护
                //授权异常时的处理器
                .exceptionHandling().accessDeniedHandler(myAuthenticationAccessDeniedHandler);
        //允许ifram显示
        http.headers().frameOptions().disable();
        //退出功能
        http.logout().logoutUrl("/user/logout")
                .logoutSuccessUrl("/index.html");//退出成功后的跳转页面
    }
}
