package cloud.onion.cms.config.i18n;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

/**
 * 基本配置及静态资源映射
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
 
    @Autowired
    private MessageResourceInterceptor messageResourceInterceptor;

    /**
     * 拦截器配置
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //国际化配置拦截器
        registry.addInterceptor(messageResourceInterceptor).addPathPatterns("/**");
    }
 
}