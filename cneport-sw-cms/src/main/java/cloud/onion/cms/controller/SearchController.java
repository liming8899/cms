package cloud.onion.cms.controller;

import cloud.onion.cms.config.i18n.I18n;
import cloud.onion.cms.mapper.SearchKeyMapper;
import cloud.onion.cms.model.entity.LoginInfo;
import cloud.onion.cms.model.res.ArticleRes;
import cloud.onion.cms.model.res.GeneralArticleRes;
import cloud.onion.cms.model.res.MenuRes;
import cloud.onion.cms.model.res.SearchRes;
import cloud.onion.cms.service.IArticleInfoService;
import cloud.onion.cms.service.ILoginInfoService;
import cloud.onion.cms.service.ISearchService;
import cloud.onion.cms.utils.LocaleMessageUtil;
import cloud.onion.core.entity.SearchKey;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 允泽
 * @date 2022/8/2
 */
@Slf4j
@Controller
@RequestMapping("search")
@Api(tags = "关键词查询接口")
@I18n({"home"})
public class SearchController {

    @Autowired
    private ISearchService searchService;
    @Autowired
    private IArticleInfoService articleService;
    @Autowired
    private SearchKeyMapper searchKeyMapper;
    @Autowired
    private ILoginInfoService loginInfoService;

    @GetMapping
    @ApiOperation(value = "根据关键词分页查询数据")
    public String index(@ApiParam(value = "关键词") @RequestParam(value = "keywords",defaultValue = "")String keywords,
                        @ApiParam(value = "当前页码", required = true) @RequestParam(value = "currentPage", defaultValue = "1")int currentPage,
                        @ApiParam(value = "每页显示数量", required = true) @RequestParam(value = "pageSize", defaultValue = "20")int pageSize,
                        Model model,HttpServletRequest request) {
        Locale finalSessionLocale = LocaleMessageUtil.getLocaleFromCookie(request);
        IPage<SearchRes> searchList = searchService.getSearchPageList(currentPage, pageSize, keywords);
        List<SearchRes> collect = searchList.getRecords().stream().parallel().peek(e -> {
            e.setTitle(e.getTitle().replaceAll(keywords,"<span style='color:#ff0000'>"+keywords+"</span>"));
        }).collect(Collectors.toList());
        searchList.setRecords(collect);
        model.addAttribute("searchList", searchList);
        //
        model.addAttribute("currentNavList", new ArrayList<>());
        // 热门文章
        IPage<ArticleRes> niceListPages = articleService.pages(1, 10,0,2,null, null, finalSessionLocale);
        model.addAttribute("niceList", niceListPages.getRecords());
        //
        model.addAttribute("keywords", keywords);
        // 搜索关键词
        List<SearchKey> searchKeys = searchKeyMapper.selectList(Wrappers.<SearchKey>lambdaQuery()
                        .eq(SearchKey::getModule,6)
                        .orderByDesc(SearchKey::getSort)
                        .orderByDesc(SearchKey::getId)
                        .last("limit 5"));
        model.addAttribute("searchKeys", searchKeys);
        // 面包屑
        List<MenuRes> breadCrumb = new ArrayList<>();
        breadCrumb.add(new MenuRes().setName("搜索结果"));
        //model.addAttribute("breadCrumb", breadCrumb);
        model.addAttribute("breadCrumb",LocaleMessageUtil.getTransWithChildren(breadCrumb, "zh_CN", "i18n/home", finalSessionLocale));

        model.addAttribute("headerClass", "search-keywords-head");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("currentPage",currentPage);
        paramMap.put("keywords",keywords);
        String jsonString = getJsonString(paramMap);
        if (StringUtils.isNotEmpty(jsonString)) {
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setSearchWord(jsonString);
            loginInfo.setVisitMoudle("全文搜索");
            loginInfoService.insertEntity(loginInfo);
        }

        return "search_global";
    }

    @GetMapping("country")
    @ApiOperation(value = "根据关键词分页查询相关国家数据")
    public String country(@ApiParam(value = "关键词") @RequestParam(value = "keywords", defaultValue = "")String keywords,
                          @ApiParam(value = "当前页码", required = true) @RequestParam(value = "currentPage", defaultValue = "1")int currentPage,
                          @ApiParam(value = "每页显示数量", required = true) @RequestParam(value = "pageSize", defaultValue = "20")int pageSize,
                          Model model, HttpServletRequest request) {
        Locale finalSessionLocale = LocaleMessageUtil.getLocaleFromCookie(request);

        String keyword=keywords;
        //keywords 转为中文
        if (!finalSessionLocale.equals(Locale.SIMPLIFIED_CHINESE)){
            keyword = LocaleMessageUtil.getTransMessage(keywords, finalSessionLocale.toString(), "i18n/home", Locale.SIMPLIFIED_CHINESE);
        }


        // 正常搜索数据
        IPage<GeneralArticleRes> articleList = searchService.getPageList(currentPage, pageSize, keyword);
        List<GeneralArticleRes> articles = articleList.getRecords();
        Optional<GeneralArticleRes> countryGuide = articles.stream().filter(e -> e.getTitle() != null && e.getTitle().contains("国别指南")).findFirst();
        if (countryGuide.isPresent()) {
            Collections.swap(articles, articles.indexOf(countryGuide.get()),0);
            articleList.setRecords(articles);
        }

        // 遍历处理搜索关键词高亮

        String finalKeyword = keyword;
        List<GeneralArticleRes> collect = articles
                .stream()
                .parallel()
                .peek(e -> {
                    e.setTitle(e.getTitle().replaceAll(finalKeyword,"<span style='color:#ff0000'>"+ finalKeyword +"</span>"));
                })
                .collect(Collectors.toList());
        articleList.setRecords(collect);
        model.addAttribute("articleList", articleList);
        //
        model.addAttribute("currentNavList", new ArrayList<>());


        //
        model.addAttribute("keywords", keywords);

        // 面包屑
        List<MenuRes> breadCrumb = new ArrayList<>();
        breadCrumb.add(new MenuRes().setName("综合功能应用").setAttribute(3).setLinkUrl("/funapp-through"));
        breadCrumb.add(new MenuRes().setName("全景服务").setAttribute(3).setLinkUrl("/funapp-service-law"));
        breadCrumb.add(new MenuRes().setName("智库服务"));
        //model.addAttribute("breadCrumb", breadCrumb);
        model.addAttribute("breadCrumb",LocaleMessageUtil.getTransWithChildren(breadCrumb, "zh_CN", "i18n/home", finalSessionLocale));
        model.addAttribute("headerClass","search-country-head");

        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("currentPage",currentPage);
        paramMap.put("keywords",keywords);
        String jsonString = getJsonString(paramMap);
        if (StringUtils.isNotEmpty(jsonString)) {
            LoginInfo loginInfo = new LoginInfo();
            loginInfo.setSearchWord(jsonString);
            loginInfo.setVisitMoudle("国别信息搜索");
            loginInfoService.insertEntity(loginInfo);
        }

        return "search_country";
    }

    private String getJsonString(Map<String, Object> map) {
        Map<String, Object> properties = new HashMap<>();
        for (String key : map.keySet()) {
            Object value = map.get(key);
            if (value != null) {
                if (value instanceof String && StringUtils.isEmpty(value.toString())) {
                    continue;
                }
                if ("keywords".equals(key)) {
                    properties.put("关键词", value);
                } else if ("currentPage".equals(key)) {
                    properties.put("页码", value);
                }
            }
        }
        String[] keys = new String[]{"关键词","页码"};
        StringBuilder result = new StringBuilder("");
        Arrays.asList(keys).forEach(item -> {
            if (properties.get(item) != null && StringUtils.isNotEmpty(properties.get(item).toString())) {
                result.append(item).append("：").append(properties.get(item)).append("，");
            }
        });
        return result.length()>0 ? result.substring(0, result.length()-1) : "";
    }
}
