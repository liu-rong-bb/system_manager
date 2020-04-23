package com.lr.user_manager_system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CrosConfig implements WebMvcConfigurer {
    //allowCredentials(true)  允许跨域使用cookie
    //allowedOrigins("*") 不能使用通配符*
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
//                .allowedMethods("PUT", "DELETE", "GET", "POST", "OPTIONS")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("access-control-allow-headers",
                        "access-control-allow-methods",
                        "access-control-allow-origin",
                        "access-control-max-age",
                        "X-Frame-Options")
                .allowCredentials(true).maxAge(3600);

    }

    /**
     * 登录拦截器,没有登录不能授权
     */

//    @Bean
//    public LoginInterceptor getLoginIntercepter() {
//
//        return new LoginInterceptor();
//    }
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//
//        registry.addInterceptor(getLoginIntercepter())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/register")
//                .excludePathPatterns("/index.html")
//                .excludePathPatterns("/login")
//                .excludePathPatterns("/logout");
//    }

}
