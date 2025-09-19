package cloud.onion.cms.controller;


import cloud.onion.cms.config.i18n.I18n;
import cloud.onion.cms.mapper.MemberMapper;
import cloud.onion.cms.model.res.*;
import cloud.onion.cms.service.*;
import cloud.onion.cms.utils.DeduplicationUtil;
import cloud.onion.cms.utils.LocaleMessageUtil;
import cloud.onion.core.entity.Member;
import cloud.onion.core.entity.Menu;
import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 允泽
 * @date 2022/7/15
 */
@Slf4j
@Controller
@Api(tags = "页面及文章展示请求接口")
@I18n({"home"})
public class ArticleController {
    @Autowired
    private INavMenuService navMenuService;
    @Autowired
    private IArticleInfoService articleService;
    @Autowired
    private IXhArticleService xhArticleService;
    @Autowired
    private MemberMapper memberMapper;
    @Autowired
    private IHistoryService historyService;

    @Autowired
    private IFinanceService financeService;

    @Value("${sso_login_url}")
    private String ssoLoginUrl;

    @Value("${server.bind}")
    private String serverBind;

    @Value("${ssoFlag}")
    private boolean ssoFlag;

    @GetMapping("/{path}")
    @ApiOperation(value = "网站页面跳转接口", notes = "查询出页面展示数据")
    public String index(@ApiParam(value = "页面路径",required = true) @PathVariable(value = "path")String path,
                        @ApiParam(value = "当前页码",required = true) @RequestParam(value = "currentPage", defaultValue = "1")int currentPage,
                        @ApiParam(value = "每页显示数量",required = true) @RequestParam(value = "pageSize", defaultValue = "18")int pageSize,
                        @RequestParam(value = "otherSetting", defaultValue = "")String otherSetting,
                        Model model , HttpServletRequest request) throws NotFoundException,UnsupportedEncodingException {
        Locale finalSessionLocale = LocaleMessageUtil.getLocaleFromCookie(request);

        String redirectUrl = request.getParameter("redirectUrl");
        if (path.contains("redirect") && StringUtils.isNotEmpty(redirectUrl)) {
            if (StpUtil.isLogin() || !ssoFlag) {
                return"redirect:"+redirectUrl;
            } else {
                return this.redirectLogin(serverBind+ "/" +path + "?redirectUrl=" +redirectUrl);
            }
        }

        String encodeUrl = URLEncoder.encode(request.getRequestURL().toString(), "UTF-8");
        // 获取菜单信息
        MenuRes nav = navMenuService.getByPath(path);
        nav.setName(LocaleMessageUtil.getTransMessage(nav.getName(), "zh_CN", "i18n/home", finalSessionLocale));
        model.addAttribute("view", nav);
        /**
         * 判断当菜单为授权菜单时，未登陆则直接跳转登陆;
         */
        if (nav.getIsLogin() > 0 && !StpUtil.isLogin()) {
            return this.redirectLogin(encodeUrl);
        }
        /**
         * 判断当菜单为授权菜单时，登陆则进行授权检测
         */
        if (nav.getIsLogin() > 0 && StpUtil.isLogin()) {
            long uid = StpUtil.getLoginIdAsLong();
            Member member = memberMapper.selectById(uid);
            if (member == null) {
                return this.redirectLogin(encodeUrl);
            }
            String authMenus = Objects.requireNonNull(member).getAuthMenus();
            if (authMenus == null) {
                return "forward:/error/401";
            }
            if (!authMenus.contains(String.valueOf(nav.getId()))) {
                return "forward:/error/401";
            }
        }

        // 面包屑
        List<MenuRes> breadCrumb = navMenuService.getParents(nav.getId());

        // 父类数据
        MenuRes parent = new MenuRes();
        long parentId = 0;
        if (nav.getParentId() > 0) {
            parent = navMenuService.view(nav.getParentId());
            if (parent != null) {
                parentId = parent.getParentId();
            }
        }
        model.addAttribute("parent", parent);
        model.addAttribute("breadCrumb",LocaleMessageUtil.getTransWithChildren(breadCrumb, "zh_CN", "i18n/home", finalSessionLocale));

        // 获取菜单列表
        List<MenuRes> navList = navMenuService.lists(0, 0);
        List<MenuRes> parentNavList = new ArrayList<>();
        List<MenuRes> currentNavList = new ArrayList<>();
        List<MenuRes> childNavList = new ArrayList<>();

        long finalParentId = parentId;
        navList.forEach(entity -> {
            // 父级菜单
            if (entity.getParentId().equals(finalParentId)) {
                parentNavList.add(entity);
            }
            // 同级菜单
            if (entity.getParentId().equals(nav.getParentId())) {
                currentNavList.add(entity);
            }
            // 下级菜单
            if (entity.getParentId().equals(nav.getId())) {
                childNavList.add(entity);
            }
        });

        // 获取上级菜单
        model.addAttribute("parentNavList", parentNavList);
        if (nav.getShowCurrentLevel() == null || nav.getShowCurrentLevel() == 1) {
            // 获取同级菜单
            model.addAttribute("currentNavList",LocaleMessageUtil.getTransWithChildren(currentNavList, "zh_CN", "i18n/home", finalSessionLocale));
        } else {
            model.addAttribute("currentNavList", new ArrayList<>(0));
        }

        model.addAttribute("currentNavListTop",LocaleMessageUtil.getTransWithChildren(currentNavList, "zh_CN", "i18n/home", finalSessionLocale));

        // 获取文章列表
        Integer articleAttribute = Objects.requireNonNull(nav).getAttribute();
        if ("funapp-finance".equals(nav.getPath())) {
            List<FinanceHomepageRes> financeInfo = financeService.getFinanceInfo();
            model.addAttribute("financeInfo", financeInfo);
        } else {
            //国别指南模板和其他新闻的模板不一致，查询方法也不一样，单独查询
            if (!"country-guide".equals(nav.getTemplate())) {
                //产业园区没有带查询条件时，默认查询国内园区
                if ("page_list_park".equals(nav.getTemplate())) {
                    if (StringUtils.isBlank(otherSetting)) {
                        otherSetting = "{\"parkType\": 1}";
                    } else {
                        otherSetting = new String(Base64.getDecoder().decode(otherSetting));
                    }
                    model.addAttribute("otherSetting",new String(Base64.getEncoder().encode(otherSetting.getBytes())));
                }
                // 获取菜单前10条文章
                String finalOtherSetting = otherSetting;
                List<MenuRes> collect = childNavList.stream().peek(e -> {
                    Integer attribute = e.getAttribute();
                    //模板是2即新华社文章模板，查询新华社新闻
                    if (attribute == 2) {
                        IPage<ArticleRes> xhs = xhArticleService.getPageList(1, 10, e.getId());
                        e.setArticleList(xhs.getRecords());
                    } else {
                        //模板不是2，查询本地上传新闻
                        IPage<ArticleRes> ars = articleService.pages(1, 10, e.getId(), 0, null, finalOtherSetting, finalSessionLocale);
                        e.setArticleList(ars.getRecords());
                    }
                }).collect(Collectors.toList());

                // 获取下级菜单
                model.addAttribute("childNavList",LocaleMessageUtil.getTransWithChildren(collect, "zh_CN", "i18n/home", finalSessionLocale));


                // 获取下两级菜单
                List<List<MenuRes>> thirdLevelList = new ArrayList<>(2);
                collect.forEach(children -> {
                    List<MenuRes> thirdLevelData = new ArrayList<>(2);
                    navList.forEach(entity -> {
                        if (entity.getParentId().equals(children.getId())) {
                            thirdLevelData.add(entity);
                        }
                    });

                    List<MenuRes> thirdCollect = thirdLevelData.stream().peek(e -> {
                        IPage<CountryGuideRes> countryGuidePageList = articleService.getCountryGuidePageListByTitle(1, -1, e.getId(),null);
                        countryGuidePageList.setRecords(countryGuidePageList.getRecords().stream().filter(DeduplicationUtil.distinctByKey(CountryGuideRes::getTitle)).collect(Collectors.toList()));
                        e.setCountryGuideResList(countryGuidePageList.getRecords());
                    }).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(thirdCollect)) {
                        thirdLevelList.add(thirdCollect);
                    }
                });

                if (thirdLevelList.size() > 0) {
                    model.addAttribute("thirdLevelList",LocaleMessageUtil.getTransWithChildren(thirdLevelList, "zh_CN", "i18n/home", finalSessionLocale));
                }

                IPage<ArticleRes> articleList;
                if (articleAttribute == 2) {
                    articleList = xhArticleService.getPageList(currentPage, pageSize, nav.getId());
                } else {
                    articleList = articleService.pages(currentPage, pageSize, nav.getId(), 0, null, otherSetting, finalSessionLocale);
                }
                model.addAttribute("articleList", articleList);
            } else {
                IPage<CountryGuideRes> countryGuidePageList = articleService.getCountryGuidePageListByTitle(currentPage, pageSize, nav.getId(),null);
                model.addAttribute("countryGuide", countryGuidePageList);
            }
        }

        model.addAttribute("headerClass", path + "-head");

        //建设历程展示
        IPage<ConstructHistoryRes> historyList;
        if ("funapp-win-history".equals(nav.getPath())) {
            historyList = historyService.pages(currentPage, pageSize, null);
            model.addAttribute("historyList", historyList);
        }

        String template = Objects.requireNonNull(nav).getTemplate();

        return StringUtils.isNotBlank(template) ? template : articleAttribute == 2 ? "list_content" : "list_article";
    }

    @GetMapping("/countryGuide/{menuId}/{title}")
    public String findingsList(@PathVariable(value = "menuId")Long menuId,
                               @PathVariable(value = "title")String title,
                               @RequestParam(value = "currentPage", defaultValue = "1")int currentPage,
                               @RequestParam(value = "pageSize", defaultValue = "18")int pageSize,
                        Model model , HttpServletRequest request) throws NotFoundException, UnsupportedEncodingException {
        Locale finalSessionLocale = LocaleMessageUtil.getLocaleFromCookie(request);
        String encodeUrl = URLEncoder.encode(request.getRequestURL().toString(), "UTF-8");
        if (!StpUtil.isLogin() && ssoFlag) {
            return this.redirectLogin(encodeUrl);
        }
        //特殊字符会报404,前端处理后在转换回来
        title = new String(Base64.getDecoder().decode(title.replaceAll("plus","+").replaceAll("slash","/")));
        // 获取菜单信息
        Menu navMenu = Optional.ofNullable(navMenuService.getById(menuId)).orElseThrow(() -> new NotFoundException("404"));
        MenuRes nav = new MenuRes();
        BeanUtils.copyProperties(navMenu,nav);
        nav.setName(LocaleMessageUtil.getTransMessage(nav.getName(), "zh_CN", "i18n/home", finalSessionLocale));
        model.addAttribute("view", nav);

        IPage<CountryGuideRes> countryGuideResList = articleService.getCountryGuidePageList(currentPage, pageSize, menuId, title);
        // 当前页面包屑
        List<MenuRes> breadCrumb = navMenuService.getParents(menuId);

        if (!CollectionUtils.isEmpty(countryGuideResList.getRecords())) {
            List<CountryGuideRes> records = countryGuideResList.getRecords();
            String titleName = records.get(0).getTitle();
            if ("en".equals(finalSessionLocale.getLanguage())) {
                titleName = records.get(0).getEnTitle();
            } else if ("ru".equals(finalSessionLocale.getLanguage())) {
                titleName = records.get(0).getRuTitle();
            }
            breadCrumb.add(new MenuRes().setName(titleName));
            model.addAttribute("countryGuideName", titleName);
        }
        model.addAttribute("breadCrumb",LocaleMessageUtil.getTransWithChildren(breadCrumb, "zh_CN", "i18n/home", finalSessionLocale));

        countryGuideResList.setRecords(countryGuideResList.getRecords().stream().peek(item -> {
            if ("SWB".equals(item.getArticleType())) {
                item.setTitle(item.getTitle()+"（商务部）");
                if (StringUtils.isNotEmpty(item.getEnTitle())) {
                    item.setEnTitle(item.getEnTitle()+"(Department of Commerce)");
                }
                if (StringUtils.isNotEmpty(item.getRuTitle())) {
                    item.setRuTitle(item.getRuTitle()+"(Министерство торговли)");
                }
            }
        }).collect(Collectors.toList()));
        model.addAttribute("countryGuide", countryGuideResList);

        // 获取菜单列表
        List<MenuRes> navList = navMenuService.lists(0, 0);
        List<MenuRes> currentNavList = new ArrayList<>();
        navList.forEach(entity -> {
            // 同级菜单
            if (entity.getParentId().equals(nav.getParentId())) {
                currentNavList.add(entity);
            }
        });
        model.addAttribute("currentNavListTop",LocaleMessageUtil.getTransWithChildren(currentNavList, "zh_CN", "i18n/home", finalSessionLocale));

        return "country-guide-guard";
    }

    @ApiOperation("查询具体文章方法")
    @GetMapping("article/{navId}/{id}")
    public String details(@ApiParam @PathVariable(value = "navId") long navId,
                          @ApiParam @PathVariable(value = "id") long id,
                          Model model, HttpServletRequest request) throws NotFoundException {
        Locale finalSessionLocale = LocaleMessageUtil.getLocaleFromCookie(request);
        //根据navId获取分类信息
        MenuRes menuRes = navMenuService.view(navId);

        //文章详情
        Integer attribute = menuRes.getAttribute();
        ArticleRes articleRes;
        if (attribute == 2 || navId == 10) {
            articleRes = xhArticleService.getByPrimaryId(id);
        } else {
            articleRes = articleService.view(id);
        }

        model.addAttribute("view", articleRes);

        // 当前页面包屑
        List<MenuRes> breadCrumb = navMenuService.getParents(navId);
        breadCrumb.add(new MenuRes().setName("正文"));
        model.addAttribute("breadCrumb",LocaleMessageUtil.getTransWithChildren(breadCrumb, "zh_CN", "i18n/home", finalSessionLocale));

        // 获取分类
        List<MenuRes> navList = new ArrayList<>();
        Menu parentMenu = navMenuService.getById(navId);
        if (parentMenu != null) {
            //产业园区联盟比较特殊，只有一层，没有子菜单，单独处理
            if ("industrialPark".equals(parentMenu.getPath())) {
                List<MenuRes> allMenus = navMenuService.lists(0, 0);
                List<MenuRes> finalNavList = navList;
                allMenus.forEach(entity -> {
                    // 同级菜单
                    if (entity.getParentId().equals(parentMenu.getParentId())) {
                        finalNavList.add(entity);
                    }
                });
            } else {
                navList = navMenuService.lists(parentMenu.getParentId(), 0);
            }
        }
        model.addAttribute("menuList",LocaleMessageUtil.getTransWithChildren(navList, "zh_CN", "i18n/home", finalSessionLocale));
        // 推荐新闻
        IPage<ArticleRes> niceListPages = articleService.pages(1, 3, navId, 2, null, null, finalSessionLocale);
        model.addAttribute("niceList", niceListPages.getRecords());

        model.addAttribute("headerClass", "article_details-head");
        return "view_article";
    }

    @GetMapping("article/{type}/{navId}/{id}")
    public String detailsByType(@PathVariable(value = "type") String type,
                                @PathVariable(value = "navId") long navId,
                                @PathVariable(value = "id") long id,
                                Model model,
                                HttpServletRequest request) throws NotFoundException {
        Locale finalSessionLocale = LocaleMessageUtil.getLocaleFromCookie(request);
        //文章详情
        ArticleRes articleRes;
        if ("XHS".equals(type)) {
            articleRes = xhArticleService.getByPrimaryId(id);
        } else {
            articleRes = articleService.view(id);
        }

        model.addAttribute("view", articleRes);

        // 当前页面包屑
        List<MenuRes> breadCrumb = navMenuService.getParents(navId);
        if ("zh".equals(finalSessionLocale.getLanguage())) {
            breadCrumb.add(new MenuRes().setName(articleRes.getTitle()));
        } else if ("en".equals(finalSessionLocale.getLanguage())) {
            breadCrumb.add(new MenuRes().setName(StringUtils.isNotEmpty(articleRes.getEnTitle())?articleRes.getEnTitle():articleRes.getTitle()));
        } else if ("ru".equals(finalSessionLocale.getLanguage())) {
            breadCrumb.add(new MenuRes().setName(StringUtils.isNotEmpty(articleRes.getRuTitle())?articleRes.getRuTitle():articleRes.getTitle()));
        }
        breadCrumb.add(new MenuRes().setName("正文"));

        model.addAttribute("breadCrumb",LocaleMessageUtil.getTransWithChildren(breadCrumb, "zh_CN", "i18n/home", finalSessionLocale));

        // 获取分类
        List<MenuRes> navList = new ArrayList<>();
        Menu parentMenu = navMenuService.getById(navId);
        if (parentMenu != null) {
            navList = navMenuService.lists(parentMenu.getParentId(), 0);
        }
        model.addAttribute("menuList",LocaleMessageUtil.getTransWithChildren(navList, "zh_CN", "i18n/home", finalSessionLocale));
        // 推荐新闻
        IPage<ArticleRes> niceListPages = articleService.pages(1, 3, navId, 2, null, null, finalSessionLocale);
        model.addAttribute("niceList", niceListPages.getRecords());

        model.addAttribute("headerClass", "article_details-head");
        return "view_article";
    }

    private String redirectLogin(String url) {
        return "redirect:" + ssoLoginUrl + url;
    }

}
