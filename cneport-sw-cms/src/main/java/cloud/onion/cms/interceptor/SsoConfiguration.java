package cloud.onion.cms.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: ps
 * @Description:
 * @Date: Created in 2019/11/4 18:51
 */
@Configuration
public class SsoConfiguration implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor getSsoInterceptor() {
        return new SsoInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        // 注册拦截器
        InterceptorRegistration loginRegistry = registry.addInterceptor(getSsoInterceptor());
        // 拦截路径
        loginRegistry.addPathPatterns("/**");
        // 排除路径
//        loginRegistry.excludePathPatterns("/");
//        loginRegistry.excludePathPatterns("/login.html");
//        loginRegistry.excludePathPatterns("/register.html");
//        loginRegistry.excludePathPatterns("/reset.html");
//        loginRegistry.excludePathPatterns("/common/**.html");
        loginRegistry.excludePathPatterns("/error/*");

        // 排除资源请求
        loginRegistry.excludePathPatterns("/css/*");
        loginRegistry.excludePathPatterns("/font/*");
        loginRegistry.excludePathPatterns("/iconfont/*");
        loginRegistry.excludePathPatterns("/images/**");
        loginRegistry.excludePathPatterns("/img/**");
        loginRegistry.excludePathPatterns("/js/**");
        loginRegistry.excludePathPatterns("/pdf/**");
        loginRegistry.excludePathPatterns("/scss/**");
        loginRegistry.excludePathPatterns("/favicon.ico");
    }
}
