package cloud.onion.cms.controller;

import cloud.onion.cms.model.res.FinanceAgencyRes;
import cloud.onion.cms.model.res.FinanceProductRes;
import cloud.onion.cms.model.res.MenuRes;
import cloud.onion.cms.service.IFinanceService;
import cloud.onion.cms.service.INavMenuService;
import cloud.onion.cms.utils.LocaleMessageUtil;
import cloud.onion.core.exception.BizException;
import cloud.onion.core.result.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("finance")
public class FinanceController {

    @Autowired
    private IFinanceService financeService;
    @Autowired
    private INavMenuService navMenuService;

    @GetMapping("agencyDetail/{agencyId}")
    public String agencyDetail(@PathVariable(value = "agencyId") long agencyId, Model model, HttpServletRequest request) throws NotFoundException {
        Locale finalSessionLocale = LocaleMessageUtil.getLocaleFromCookie(request);

        FinanceAgencyRes financeAgencyDetails = Optional.ofNullable(financeService.getFinanceAgencyDetails(agencyId)).orElseThrow(() -> new BizException(ResultCode.NOT_FOUND));

        // 面包屑
        List<MenuRes> breadCrumb = navMenuService.getParents(18L);
        String language = finalSessionLocale.getLanguage();
        switch (language) {
            case "en":
                breadCrumb.add(new MenuRes().setName(StringUtils.isNotEmpty(financeAgencyDetails.getEnName())?financeAgencyDetails.getEnName():financeAgencyDetails.getName()));
                break;
            case "ru":
                breadCrumb.add(new MenuRes().setName(StringUtils.isNotEmpty(financeAgencyDetails.getRuName())?financeAgencyDetails.getRuName():financeAgencyDetails.getName()));
                break;
            default:
                breadCrumb.add(new MenuRes().setName(financeAgencyDetails.getName()));
                break;
        }

        model.addAttribute("breadCrumb", LocaleMessageUtil.getTransWithChildren(breadCrumb, "zh_CN", "i18n/home", finalSessionLocale));
        model.addAttribute("financeAgencyDetails", financeAgencyDetails);

        return "/pageIndex/funapp-finance-agency-detail";
    }

    @GetMapping("productList/{agencyId}")
    public String productList(@PathVariable(value = "agencyId") long agencyId, Model model, HttpServletRequest request) throws NotFoundException {
        Locale finalSessionLocale = LocaleMessageUtil.getLocaleFromCookie(request);

        List<FinanceProductRes> financeProductResList = financeService.getFinanceProductResList(agencyId);
        FinanceAgencyRes financeAgencyDetails = Optional.ofNullable(financeService.getFinanceAgencyDetails(agencyId)).orElseThrow(() -> new BizException(ResultCode.NOT_FOUND));
        // 面包屑
        List<MenuRes> breadCrumb = navMenuService.getParents(18L);
        String language = finalSessionLocale.getLanguage();
        switch (language) {
            case "en":
                breadCrumb.add(new MenuRes().setName(StringUtils.isNotEmpty(financeAgencyDetails.getEnName())?financeAgencyDetails.getEnName():financeAgencyDetails.getName()));
                break;
            case "ru":
                breadCrumb.add(new MenuRes().setName(StringUtils.isNotEmpty(financeAgencyDetails.getRuName())?financeAgencyDetails.getRuName():financeAgencyDetails.getName()));
                break;
            default:
                breadCrumb.add(new MenuRes().setName(financeAgencyDetails.getName()));
                break;
        }
        model.addAttribute("breadCrumb", LocaleMessageUtil.getTransWithChildren(breadCrumb, "zh_CN", "i18n/home", finalSessionLocale));
        model.addAttribute("financeAgencyDetails", financeAgencyDetails);
        model.addAttribute("financeProductResList",financeProductResList);
        return "/pageIndex/funapp-finance-product-list";
    }

    @GetMapping("productDetail/{productId}")
    public String productDetail(@PathVariable(value = "productId") long productId, Model model, HttpServletRequest request) throws NotFoundException {
        Locale finalSessionLocale = LocaleMessageUtil.getLocaleFromCookie(request);

        FinanceProductRes financeProductDetails = Optional.ofNullable(financeService.getFinanceProductDetails(productId)).orElseThrow(() -> new BizException(ResultCode.NOT_FOUND));

        List<MenuRes> breadCrumb = navMenuService.getParents(18L);
        String language = finalSessionLocale.getLanguage();
        switch (language) {
            case "en":
                breadCrumb.add(new MenuRes().setName(StringUtils.isNotEmpty(financeProductDetails.getEnName())?financeProductDetails.getEnName():financeProductDetails.getName()));
                break;
            case "ru":
                breadCrumb.add(new MenuRes().setName(StringUtils.isNotEmpty(financeProductDetails.getRuName())?financeProductDetails.getRuName():financeProductDetails.getName()));
                break;
            default:
                breadCrumb.add(new MenuRes().setName(financeProductDetails.getName()));
                break;
        }

        model.addAttribute("breadCrumb", LocaleMessageUtil.getTransWithChildren(breadCrumb, "zh_CN", "i18n/home", finalSessionLocale));
        model.addAttribute("financeProductDetails", financeProductDetails);
        return "/pageIndex/funapp-finance-product-detail";
    }
}
