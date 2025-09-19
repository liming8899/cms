package cloud.onion.cms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collections;

/**
 * @author 陆茂盛
 * @date 2022/5/27
 */
@Configuration
public class CorsConfig {
    @Value("${vReferer}")
    private String vReferer;

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*"); // 允许任何的head头部
        String[] vRefererSplit = vReferer.split(",");
        for (String referer : vRefererSplit) {
            corsConfiguration.addAllowedOrigin(referer); // 允许任何域名使用
        }
        corsConfiguration.addAllowedMethod("*"); // 允许任何的请求方法
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOriginPatterns(Collections.singletonList("*"));
        return corsConfiguration;
    }
}
