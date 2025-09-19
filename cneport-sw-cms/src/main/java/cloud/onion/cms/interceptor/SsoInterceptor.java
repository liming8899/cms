package cloud.onion.cms.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: ps
 * @Description:
 * @Date: Created in 2019/11/4 18:38
 */
@Slf4j
public class SsoInterceptor implements HandlerInterceptor {

    @Value("${vReferer}")
    private String vReferer;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws IOException {

        // 是否通过
        boolean flg = true;

        String method = request.getMethod();
        if(!"GET".equals(method) && !"POST".equals(method) && !"PUT".equals(method)  && !"DELETE".equals(method) && !"HEAD".equals(method)){
            log.error("请求地址："+request.getRequestURI());
            response.sendRedirect(request.getContextPath()+"/error/500");
            return false;
        }

        String referer = request.getHeader("Referer");
        if (StringUtils.isEmpty(referer) || StringUtils.isBlank(referer)){
            return flg;
        }

        //配置文件所配置的请求
        String[] referers = vReferer.split(",");
        for (String r : referers) {
            if (referer.trim().startsWith(r)){
                return flg;
            }
        }
        log.error("拦截referer："+referer.trim());
        response.sendRedirect(request.getContextPath()+"/error/500");
        return false;
    }
}
