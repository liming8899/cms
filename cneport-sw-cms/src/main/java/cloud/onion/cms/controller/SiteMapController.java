package cloud.onion.cms.controller;

import cloud.onion.cms.model.res.MenuRes;
import cloud.onion.cms.utils.LocaleMessageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author 允泽
 * @date 2022/9/7
 */
@Controller
@RequestMapping("sitemap")
@Api(tags = "网站地图查询接口")
public class SiteMapController {

    @GetMapping
    @ApiOperation(value = "返回网站地图数据并跳转网站地图页面")
    public String index(Model model, HttpServletRequest request) {
        Locale finalSessionLocale = LocaleMessageUtil.getLocaleFromCookie(request);
        model.addAttribute("menuList", new ArrayList<>());
        /**
         * 面包屑
         */
        List<MenuRes> breadCrumb = new ArrayList<>();

        String siteName = LocaleMessageUtil.getTransMessage("网站地图", "zh_CN", "i18n/home", finalSessionLocale);
        breadCrumb.add(new MenuRes().setName(siteName));
        model.addAttribute("breadCrumb", LocaleMessageUtil.getTransWithChildren(breadCrumb, "zh_CN", "i18n/home", finalSessionLocale));
        model.addAttribute("siteName", siteName);
        return "sitemap";
    }
}
