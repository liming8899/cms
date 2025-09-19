package cloud.onion.cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import java.util.Collections;

/**
 * @author 允泽
 * @date 2022/7/15
 */
@SpringBootApplication
public class CmsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CmsApplication.class);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        super.onStartup(servletContext);
        servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));
        SessionCookieConfig sessionCookieConfig=servletContext.getSessionCookieConfig();
        sessionCookieConfig.setHttpOnly(true);
    }

}
