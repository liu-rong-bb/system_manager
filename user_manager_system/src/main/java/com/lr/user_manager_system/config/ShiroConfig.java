package com.lr.user_manager_system.config;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    //保持一段时间的登录状态
    public CookieRememberMeManager rememberMeManager() {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        cookieRememberMeManager.setCipherKey("EVANNIGHTLY_WAOU".getBytes());
        return cookieRememberMeManager;
    }

    @Bean
    public SimpleCookie rememberMeCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }

    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager(@Qualifier("UserRealm") UserRealm UserRealm) {
        System.out.println("/*======================== 安全管理器 ===================================  */");
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(UserRealm);
        return securityManager;
    }


    @Bean(name = "UserRealm")
    public UserRealm UserRealm() {
        System.out.println(" /*=================== 自定义 realm=====================*/");
       return new UserRealm();
    }


    //
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager) {
        System.out.println("/*================= Filter工厂，设置对应的过滤条件和跳转条件 =========================  */");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

        //实现安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        /*进行权限的控制,必须使用LinkHashMap,shrio要按照顺序进行设置!!!!!!!!!*/
        Map<String, String> map = new LinkedHashMap<>();

        //登出
        map.put("/logout", "anon");
        map.put("/login","anon");
        map.put("/register","anon");

        /*最后在写剩下的所有全部拦截,否则会造成拦截所有的url*/
       // map.put("/**", "authc");
        map.put("/*", "authc");

//        //自定义认证路径登录
        shiroFilterFactoryBean.setLoginUrl("/login");
//        //首页
        shiroFilterFactoryBean.setSuccessUrl("/user");
//        //设置未授权的提示页面
       shiroFilterFactoryBean.setUnauthorizedUrl("/err");

//
       shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        return shiroFilterFactoryBean;
    }

    /*================= 加入注解的使用，不加入这个注解不生效 =========================  */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager( securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}

