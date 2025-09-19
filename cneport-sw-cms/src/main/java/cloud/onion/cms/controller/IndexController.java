package cloud.onion.cms.controller;

import cloud.onion.cms.config.i18n.I18n;
import cloud.onion.cms.model.res.ArticleRes;
import cloud.onion.cms.model.res.BannerRes;
import cloud.onion.cms.model.res.MenuRes;
import cloud.onion.cms.service.IArticleInfoService;
import cloud.onion.cms.service.IBannerService;
import cloud.onion.cms.service.INavMenuService;
import cloud.onion.cms.service.IXhArticleService;
import cloud.onion.cms.utils.LocaleMessageUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Controller
@Api(tags = "首页展示请求接口")
@I18n({"home"})
public class IndexController {
    @Autowired
    private IArticleInfoService articleInfoService;
    @Autowired
    private IXhArticleService xhArticleService;
    @Autowired
    private IBannerService bannerService;
    @Autowired
    private INavMenuService menuService;

    @GetMapping
    @ApiOperation(value = "展示首页页面及数据")
    public String index(Model model,HttpServletRequest request) {
        Locale finalSessionLocale = LocaleMessageUtil.getLocaleFromCookie(request);

        List<MenuRes> dynamicMenus = menuService.lists(0, 1);
        dynamicMenus = dynamicMenus.stream()
                .sorted(Comparator.comparing(MenuRes::getId).reversed())
                .parallel()
                .peek(e -> {
                    if (e.getId() == 10) {
                        e.setPath(e.getLinkUrl());
                        IPage<ArticleRes> xhs = xhArticleService.getPageList(1, 6, e.getId());
                        e.setArticleList(xhs.getRecords());
                    } else {
                        if (e.getAttribute() == 2) {
                            IPage<ArticleRes> xhs = xhArticleService.getPageList(1, 6, e.getId());
                            e.setArticleList(xhs.getRecords());
                        } else {
                            IPage<ArticleRes> ars = articleInfoService.pages(1, 6, e.getId(), 0, null, null, finalSessionLocale);
                            e.setArticleList(ars.getRecords());
                        }
                    }
                }).collect(Collectors.toList());

        //model.addAttribute("dynamicMenus", dynamicMenus);
        model.addAttribute("dynamicMenus",LocaleMessageUtil.getTransWithChildren(dynamicMenus, "zh_CN", "i18n/home", finalSessionLocale));


        // 获取banner
        List<BannerRes> bannerList = bannerService.lists(1);
        //model.addAttribute("bannerList", bannerList);

        model.addAttribute("bannerList",LocaleMessageUtil.getTransWithChildren(bannerList, "zh_CN", "i18n/home", finalSessionLocale));


        // 获取数据分析图
        BannerRes dataStatistics = bannerService.one(2);
        model.addAttribute("dataStatistics", dataStatistics);

        // 8大菜单
        List<MenuRes> menuList = menuService.lists(3, 0);

        //model.addAttribute("menuList", menuList);
        model.addAttribute("menuList",LocaleMessageUtil.getTransWithChildren(menuList, "zh_CN", "i18n/home", finalSessionLocale));
        return "index";
    }

    /**
     * 修改系统默认语言
     * @param request
     * @return
     */
    @GetMapping(value = "common/locale")
    public String localeHandler(HttpServletRequest request) {
        String lastUrl = request.getHeader("referer");
        return "redirect:" + lastUrl.replaceAll("&amp;","&");
    }

}
