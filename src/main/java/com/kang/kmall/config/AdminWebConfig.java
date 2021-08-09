package com.kang.kmall.config;

import com.kang.kmall.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/*
SpringMVC的所有相关配置，都可以使用一个配置类来完成,这个配置类实现WebMvcConfigurer接口，接口中都是默认方法
可以根据需求实现相应的方法。
 */
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {

    /**
     * 配置自定义的拦截器，并设置要拦截的请求和不拦截的请求
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //将我们定义好的拦截器添加到配置中
        //指定要拦截的请求，排除不需要拦截的请求。
        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        registration.addPathPatterns("/**"); //拦截所有路径的所有请求，包括静态资源的请求
        registration.excludePathPatterns("/","/login","/register","/user/login","/user/register",
                "/productCategory/list","/main",
                "/css/**","/js/**","/images/**");  //放行登陆页面的请求和所有静态资源的请求

        /*
        对于静态资源，1、可以使用上述方法将各个目录下的静态资源请求放行
                    2、也可以在配置文件中设置静态资源的访问路径，比如拦截所有以/static开头的静态资源请求：
                    spring.mvc.static-path-pattern=/static/**
         */
    }
}
