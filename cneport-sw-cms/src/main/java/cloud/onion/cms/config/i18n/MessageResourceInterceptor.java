package cloud.onion.cms.config.i18n;

import cloud.onion.cms.utils.LocaleMessageUtil;
import cloud.onion.cms.utils.RedisUtil;
import cloud.onion.core.utils.IpAddressUtil;
import cloud.onion.core.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

/**
 * 国际化拦截器配置
 */
@Component
@Slf4j
public class MessageResourceInterceptor implements HandlerInterceptor {
    @Autowired
    private RedisUtil redisUtil;

    //    private static final String[] CN_LIST = new String[]{"中国","香港","澳门","台湾"};
    private static final String[] CN_LIST = new String[]{"CN","HK","MO","TW"};
    //    private static final String[] RU_LIST = new String[]{"俄罗斯","白俄罗斯", "乌克兰", "摩尔多瓦", "格鲁吉亚", "亚美尼亚", "爱沙尼亚", "拉托维亚", "阿塞拜疆", "哈萨克斯坦", "吉尔吉斯斯坦", "乌兹别克斯坦", "塔吉克斯坦", "土库曼斯坦"};
    private static final String[] RU_LIST = new String[]{"RU","BY", "UA", "MD", "GE", "AM", "EE", "LV", "AZ", "KZ", "KG", "UZ", "TJ", "TM"};

    private static final String I18N_LANG = "lang";
    public static final String I18N_LANG_SESSION = "lang_session";

    @Bean
    public LocaleResolver localeResolver(){
        final SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("zh", "CN"));
        return localeResolver;
    }
    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse rep, Object handler) {

        LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(req);
        Locale locale = setLanguageFromIp(req, rep);
        if (localeResolver != null) {
            localeResolver.setLocale(req, rep, locale);
        }

        // 在跳转到该方法先清除request中的国际化信息
        req.removeAttribute(MessageResourceExtension.I18N_ATTRIBUTE);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse rep, Object handler, ModelAndView modelAndView) {
        // 在方法中设置i18路径
        if (null != req.getAttribute(MessageResourceExtension.I18N_ATTRIBUTE)) {
            return;
        }

        //判断是否HandlerMethod
        HandlerMethod method = null;
        if (handler instanceof HandlerMethod) {
            method = (HandlerMethod) handler;
        }
        if (null == method) {
            return;
        }

        // 在method注解了i18
        I18n i18nMethod = method.getMethodAnnotation(I18n.class);
        if (null != i18nMethod) {
            req.setAttribute(MessageResourceExtension.I18N_ATTRIBUTE, i18nMethod.value());
            return;
        }

        // 在Controller上注解了i18
        I18n i18nController = method.getBeanType().getAnnotation(I18n.class);
        if (null != i18nController) {
            req.setAttribute(MessageResourceExtension.I18N_ATTRIBUTE, i18nController.value());
            return;
        }

        // 根据Controller名字设置i18
        String controller = method.getBeanType().getName();
        int index = controller.lastIndexOf(".");
        if (index != -1) {
            controller = controller.substring(index + 1, controller.length());
        }
        index = controller.toUpperCase().indexOf("CONTROLLER");
        if (index != -1) {
            controller = controller.substring(0, index);
        }
        req.setAttribute(MessageResourceExtension.I18N_ATTRIBUTE, controller);
    }

    private Locale setLanguageFromIp(HttpServletRequest request, HttpServletResponse response) {
        //获取系统的默认区域信息
        Locale locale = Locale.SIMPLIFIED_CHINESE;
        //获取请求信息，国际化的参数值
        String lang = request.getParameter(I18N_LANG);
        if (!StringUtils.isEmpty(lang)) {
            String[] split = lang.split("_");
            //接收的第一个参数为：语言代码，国家代码
            locale = new Locale(split[0], split[1]);
            return locale;
        } else {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("userLanguage".equals(cookie.getName())) {
                        String language = cookie.getValue();
                        if (!StringUtils.isEmpty(language)) {
                            String[] split = language.split("_");
                            //接收的第一个参数为：语言代码，国家代码
                            locale = new Locale(split[0], split[1]);
                            return locale;
                        }
                    }
                }
            }
        }

        String ipAddress = IpAddressUtil.getIpAddr(request);
        try {
            if (redisUtil.hasKey("ipAddress:location:"+ipAddress)) {
                Object language = redisUtil.get("ipAddress:location:" + ipAddress);
                if (language != null) {
                    Cookie cookie = new Cookie("userLanguage", language.toString());
                    cookie.setMaxAge(24*60*60*300);
                    String userLanguage = LocaleMessageUtil.getValueFromCookie(request, "userLanguage");
                    if (StringUtils.isBlank(userLanguage)) {
                        response.addCookie(cookie);
                    }
                    String[] split = language.toString().split("_");
                    locale = new Locale(split[0], split[1]);
                }
            } else {
                Map<String, String> stringStringMap = IpUtils.requestIp(ipAddress);
                String language = "zh_CN";
                if (stringStringMap != null) {
                    String nation = stringStringMap.get("countryCode");
                    if (StringUtils.isNotEmpty(nation)) {
                        boolean cnMatch = Arrays.stream(CN_LIST).anyMatch(nation::contains);
                        boolean ruMatch = Arrays.stream(RU_LIST).anyMatch(nation::contains);
                        if (cnMatch) {
                            language = "zh_CN";
                        } else if (ruMatch) {
                            language = "ru_RU";
                        } else {
                            language = "en_US";
                        }
                    }
                }
                redisUtil.set("ipAddress:location:"+ipAddress, language);
                Cookie cookie = new Cookie("userLanguage", language);
                cookie.setMaxAge(24*60*60);
                String userLanguage = LocaleMessageUtil.getValueFromCookie(request, "userLanguage");
                if (StringUtils.isBlank(userLanguage)) {
                    response.addCookie(cookie);
                }
                String[] split = language.split("_");
                locale = new Locale(split[0], split[1]);
            }
        } catch (Exception e) {
            log.error("ip地址设置语言失败，ip:" + ipAddress +"，错误信息：" + e.getMessage());
        }

        return locale;
    }
}