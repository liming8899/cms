package cloud.onion.cms.config;


import cloud.onion.cms.model.res.LinkRes;
import cloud.onion.cms.model.res.MenuRes;
import cloud.onion.cms.model.res.SiteConfigRes;
import cloud.onion.cms.service.ILinkService;
import cloud.onion.cms.service.INavMenuService;
import cloud.onion.cms.service.ISiteConfigService;
import cloud.onion.cms.utils.LocaleMessageUtil;
import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author 允泽
 * @date 2022/7/15
 */
@Slf4j
@ControllerAdvice
public class GlobalDataConfig {

    @Autowired
    private ISiteConfigService siteConfigService;
    @Autowired
    private INavMenuService navMenuService;
    @Autowired
    private ILinkService linkService;

    @Value("${server.bind}")
    private String cmsUrl;

    @Value("${sso_login_url}")
    private String ssoLoginUrl;

    @Value("${memberUrl}")
    private String memberUrl;

    @Value("${accountUrl}")
    private String accountUrl;

    @Value("${ssoFlag}")
    private String ssoFlag;

    @Value("${logoutUrl}")
    private String logoutUrl;
    @Value("${ssoLogoutUrl}")
    private String ssoLogoutUrl;

    @Value("${ssoRegisterUrl}")
    private String ssoRegisterUrl;


    @Value("${ssoScodamotOwnerUrl}")
    private String ssoScodamotOwnerUrl;

    @Value("${ssoScodamotAgentUrl}")
    private String ssoScodamotAgentUrl;

    @Value("${scodamotMainUrl}")
    private String scodamotMainUrl;

    @Value("${ssoMainUrl}")
    private String ssoMainUrl;

    @Value("${mainUrl}")
    private String mainUrl;

    /**
     * 添加全局标签数据
     * @param model
     */
    @ModelAttribute()
    public void addAttributes(Model model, HttpServletRequest request) {
        Locale finalSessionLocale = LocaleMessageUtil.getLocaleFromCookie(request);
        model.addAttribute("cmsUrl",cmsUrl);
        model.addAttribute("ssoLoginUrl",ssoLoginUrl);
        /**
         * 基础地址
         */
        model.addAttribute("memberUrl",memberUrl);

        /**
         * sso地址
         */
        model.addAttribute("accountUrl",accountUrl);
        /**
         * 是否开启sso，正式环境开启，开发、测试环境关闭
         */
        model.addAttribute("ssoFlag",ssoFlag);

        /**
         * 退出登录地址
         */
        model.addAttribute("logoutUrl",logoutUrl);

        /**
         * 退出登录地址
         */
        model.addAttribute("ssoLogoutUrl",ssoLogoutUrl);

        /**
         * 统一身份认证前段主地址
         */
        model.addAttribute("ssoRegisterUrl",ssoRegisterUrl);
        /**
         * 多式联运货主端登录地址
         */
        model.addAttribute("ssoScodamotOwnerUrl",ssoScodamotOwnerUrl);
        /**
         * 多式联运货代端登录地址
         */
        model.addAttribute("ssoScodamotAgentUrl",ssoScodamotAgentUrl);
        /**
         * 多式联运地址
         */
        model.addAttribute("scodamotMainUrl",scodamotMainUrl);


        /**
         * 统一身份认证主地址
         */
        model.addAttribute("ssoMainUrl",ssoMainUrl);


        /**
         * 上综服平台主地址
         */
        model.addAttribute("mainUrl",mainUrl);

        /**
         * 站点配置
         */
        SiteConfigRes siteConfig = siteConfigService.getSiteConfig();
        model.addAttribute("site", siteConfig);
        /**
         * 一级链接数据列表
         */
        List<LinkRes> links = linkService.getLists();
        model.addAttribute("links", LocaleMessageUtil.getTransWithChildren(links, "zh_CN", "i18n/home", finalSessionLocale));
        // 获取一级菜单
        List<MenuRes> navList = navMenuService.getNavTree(0);
//        navList = filterNotHidden(navList);
       // model.addAttribute("navList", navList);
        model.addAttribute("navList", LocaleMessageUtil.getTransWithChildren(navList, "zh_CN", "i18n/home", finalSessionLocale));
        /**
         * 登陆状态
         */
        log.info("token的值："+ SaHolder.getRequest().getCookieValue("token"));
        boolean isLogin = StpUtil.isLogin();
        log.info("是否登录状态："+isLogin);
        model.addAttribute("isLogin",isLogin);

        model.addAttribute("lang_session",finalSessionLocale);
    }

    public List<MenuRes> filterNotHidden(List<MenuRes> allMenus) {
        List<MenuRes> menuResList = new ArrayList<>(4);
        for (MenuRes menuRes: allMenus) {
            if (CollectionUtil.isNotEmpty(menuRes.getChildren())) {
                menuRes.setChildren(filterNotHidden(menuRes.getChildren()));
            }
//            if (menuRes.getIsHidden() == null || menuRes.getIsHidden() != 1) {
//                menuResList.add(menuRes);
//            }
        }
        return menuResList;
    }
}
