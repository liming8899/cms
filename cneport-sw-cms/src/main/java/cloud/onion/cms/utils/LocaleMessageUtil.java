package cloud.onion.cms.utils;


import cloud.onion.cms.config.i18n.MessageResourceExtension;
import cn.dev33.satoken.session.SaSession;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static cloud.onion.cms.config.i18n.MessageResourceInterceptor.I18N_LANG_SESSION;


/**
 * 国际化工具
 *
 */
@Slf4j
@Component
public class LocaleMessageUtil {

    /**
     * 获取当前语言编码
     * @return
     */
    public static Locale getLang(){
        return LocaleContextHolder.getLocale();
    }

    /**
     * @param code：对应文本配置的key.
     * @return 对应地区的语言消息字符串
     */
    public static String getMessage(String code) {
        return getMessage(code, new Object[]{});
    }

    public static String getMessage(String code, String defaultMessage) {
        return getMessage(code, null, defaultMessage);
    }

    public static String getMessage(String code, String defaultMessage, Locale locale) {
        return getMessage(code, null, defaultMessage, locale);
    }

    public static String getMessage(String code, Locale locale) {
        return getMessage(code, null, "", locale);
    }

    public static String getMessage(String code, Object[] args) {
        return getMessage(code, args, "");
    }

    public static String getMessage(String code, Object[] args, Locale locale) {
        return getMessage(code, args, "", locale);
    }

    public static String getMessage(String code, Object[] args, String defaultMessage) {
        Locale locale = LocaleContextHolder.getLocale();
        return getMessage(code, args, defaultMessage, locale);
    }

    public static String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        MessageResourceExtension messageSource = SpringUtil.getBean(MessageResourceExtension.class);
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }

    /**
     * 根据值从一种语言转为另一种语言
     * @param message
     * @param locale
     * @param baseName
     * @param transLocale
     * @return
     */
    public static String getTransMessage(String message, String locale, String baseName, Locale transLocale) {
        try(InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(baseName+"_"+locale+".properties")) {
            if (null == resourceAsStream) {
                throw new RuntimeException("无法获取资源文件路径");
            }
            Properties properties = new Properties();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(resourceAsStream, StandardCharsets.UTF_8))) {
                properties.load(reader);
                Map<String,Object> lookup = new HashMap(properties);
                for (Map.Entry<String, Object> next : lookup.entrySet()) {
                    if (next.getValue().equals(message)) {
                        String key = next.getKey();
                        if (key != null) {
                            return getMessage(key, transLocale);
                        }
                    }
                }
            } catch (Exception ignored) {
                throw new RuntimeException("资源文件流转换出错");
            }
        } catch (Exception e) {
            throw new RuntimeException("翻译转换报错");
        }
        return message;
    }

    /**
     * 根据值从一种语言转为另一种语言(有多层)
     * @param dataToTrans
     * @param locale
     * @param baseName
     * @param transLocale
     * @return
     */
    public static List<Object> getTransWithChildren(List<?> dataToTrans, String locale, String baseName, Locale transLocale) {
        List<Object> dataTransed = new ArrayList<>();
        JSONArray parse = JSONUtil.parseArray(dataToTrans);
        Iterator<Object> iterator = parse.stream().iterator();
        while(iterator.hasNext()) {
            Object next = iterator.next();
            if (next instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject)next;
                if (jsonObject.get("name") != null) {
                    jsonObject.set("name",getTransMessage(jsonObject.get("name").toString(),locale, baseName,transLocale));
                }
                if (jsonObject.get("description") != null) {
                    jsonObject.set("description",getTransMessage(jsonObject.get("description").toString(),locale, baseName,transLocale));
                }
                Object children = jsonObject.get("children");
                if (children != null) {
                    List<?> chidData  = (List<?>)children;
                    if (chidData.size()>0){
                        jsonObject.set("children",transChildren(children,locale, baseName,transLocale));
                    }
                }
                dataTransed.add(jsonObject);
            } else if (next instanceof JSONArray){
                dataTransed.add(transChildren(next,locale, baseName,transLocale));
            }
        }
        return dataTransed;
    }

    /**
     * 翻译数据
     * @param childData
     * @param locale
     * @param baseName
     * @param transLocale
     * @return
     */
    private static List<JSONObject> transChildren(Object childData, String locale, String baseName, Locale transLocale) {
        List<JSONObject> transData = new ArrayList<>();
        Object[] objects = JSONUtil.parseArray(childData).toArray();
        for (Object obj: objects) {
            JSONObject jsonObject = (JSONObject)obj;
            if (jsonObject.get("name") != null) {
                jsonObject.set("name",getTransMessage(jsonObject.get("name").toString(),locale, baseName,transLocale));
            }
            Object children = jsonObject.get("children");
            if (children != null) {
                List<?> chidData  = (List<?>)children;
                if (chidData.size()>0){
                    jsonObject.set("children",transChildren(children,locale, baseName,transLocale));
                }
            }
            transData.add(jsonObject);
        }
        return transData;
    }

    public static Locale getLocale(HttpServletRequest request) {
        Locale sessionLocale = Locale.SIMPLIFIED_CHINESE;
        Locale changeSessionLocale = null;
        if (StpUtil.isLogin()) {
            SaSession saSession = StpUtil.getSession();
            changeSessionLocale = saSession.get(I18N_LANG_SESSION) == null ? sessionLocale: (Locale)saSession.get(I18N_LANG_SESSION);
        } else {
            HttpSession session = request.getSession();
            changeSessionLocale = session.getAttribute(I18N_LANG_SESSION) == null ? sessionLocale: (Locale)session.getAttribute(I18N_LANG_SESSION);
        }
        return changeSessionLocale;
    }

    public static Locale getLocaleFromCookie(HttpServletRequest request) {
//        Locale locale = Locale.SIMPLIFIED_CHINESE;
        Locale locale = LocaleContextHolder.getLocale();
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
        return locale;
    }

    public static String getValueFromCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}