package cloud.onion.cms.controller;


import cloud.onion.cms.model.entity.*;
import cloud.onion.cms.model.entity.vo.TParaExchrateVo;
import cloud.onion.cms.service.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/**
 * @author 允泽
 * @date 2022/7/15
 */
@Slf4j
@Controller
@Api(tags = "页面及文章展示请求接口")
@RequestMapping("homeParameters")
public class ArticlePublicParamController {
    @Autowired
    private ITParaComplexService iTParaComplexService;
    @Autowired
    private ITParaComplexChangeService tParaComplexChangeService;
    @Autowired
    private ITParaMerchElementService tParaMerchElementService;
    @Autowired
    private ITParaExchangeRateService itParaExchangeRateService;
    @Autowired
    private TParaPublicCodeItemService tParaPublicCodeItemService;
    @Autowired
    private TPara3CCertService tPara3CCertService;

    @Autowired
    private TParaTradeComplexBanService tParaTradeComplexBanService;

    @Autowired
    private TParaComplexBanService tParaComplexBanService;
    @Autowired
    private ITParaTradeComplexLtdService tParaTradeComplexLtdService;
    @Autowired
    private ConsumeStandardService consumeStandardService;
    @Autowired
    private TParaEntryHscodeService tParaEntryHscodeService;

    @Autowired
    private TParaMerchEleCmpResultService tParaMerchEleCmpResultService;
    @Autowired
    private TParaPurposeService tParaPurposeService;

    @GetMapping("/{path}")
    @ApiOperation(value = "网站页面跳转接口", notes = "查询出页面展示数据")
    public String index(@ApiParam(value = "页面路径", required = true) @PathVariable(value = "path") String path,
                        @RequestParam(value = "keywords", defaultValue = "") String keywords,
                        @RequestParam(value = "keywords2", defaultValue = "") String keywords2,
                        @ApiParam(value = "当前页码", required = true) @RequestParam(value = "currentPage", defaultValue = "1") int currentPage,
                        @ApiParam(value = "每页显示数量", required = true) @RequestParam(value = "pageSize", defaultValue = "18") int pageSize,
                        @RequestParam(value = "pqcode", defaultValue = "") String pqcode,
                        @RequestParam(value = "flush", defaultValue = "") String flush,
                        Model model) throws NotFoundException {

        if ("parameterSearch".equals(path)) {
            pageSize = 10;
            //查询商品编码
            model.addAttribute("keywords", keywords);
            model.addAttribute("keywords2", keywords2);
            IPage<Map<Object, String>> complexList = iTParaComplexService.getPageList(currentPage, pageSize, keywords, keywords2);
            List<Map<Object, String>> records = complexList.getRecords();
            model.addAttribute("detailList", complexList);
            model.addAttribute("path", path);
            //跳转固定页面
            if (StringUtils.isNotBlank(pqcode)) {
                return "parameterSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "parameterSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "parameterSearch::columns_recorde";
                }
            }
        } else if ("parameterChange".equals(path)) {
            pageSize = 10;
            IPage<Map<Object, String>> complexList = tParaComplexChangeService.getPageList(currentPage, pageSize, keywords, keywords2);
            model.addAttribute("keywords", keywords);
            model.addAttribute("keywords2", keywords2);
            model.addAttribute("detailList", complexList);
            model.addAttribute("path", path);
            if (StringUtils.isNotBlank(pqcode)) {
                return "parameterChange";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "parameterChange::search_recorde";
                } else {
                    //刷新 列表
                    return "parameterChange::columns_recorde";
                }
            }
        } else if ("elementSearch".equals(path)) {
            model.addAttribute("keywords", keywords);
            model.addAttribute("keywords2", keywords2);
            model.addAttribute("path", path);
            pageSize = 10;
            IPage<TParaMerchElement> complexList = tParaMerchElementService.getPageList(currentPage, pageSize, keywords, keywords2);
            model.addAttribute("detailList", complexList);

            if (StringUtils.isNotBlank(pqcode)) {
                return "elementSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "elementSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "elementSearch::columns_recorde";
                }
            }
        } else if ("elementCmpResultSearch".equals(path)) {
            //申报要素变更
            pageSize = 10;
            model.addAttribute("keywords", keywords);
            model.addAttribute("keywords2", keywords2);
            model.addAttribute("path", path);
            IPage<TParaMerchEleCmpResult> pageList = tParaMerchEleCmpResultService.getPageList(currentPage, pageSize, keywords, keywords2);
            model.addAttribute("detailList", pageList);
//            return "elementCmpResultSearch";

            //return "elementCmpResultSearch";

            if (StringUtils.isNotBlank(pqcode)) {
                return "elementCmpResultSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "elementCmpResultSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "elementCmpResultSearch::columns_recorde";
                }
            }

        } else if ("currSearch".equals(path)) {
            //币制代码
            pageSize = 10;
            IPage<TParaPublicCodeItem> currList = tParaPublicCodeItemService.getCurrPageList(currentPage, pageSize, "CURR_OUTDATED", keywords, keywords2);
            model.addAttribute("detailList", currList);
            model.addAttribute("path", path);
            if (StringUtils.isNotBlank(pqcode)) {
                return "currSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "currSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "currSearch::columns_recorde";
                }
            }

        } else if ("tradeSearch".equals(path)) {
            //监管方式
            pageSize = 10;
            IPage<TParaPublicCodeItem> tradeList = tParaPublicCodeItemService.getPageList(currentPage, pageSize, "TRADE_COMBINATION", keywords, keywords2);
            model.addAttribute("detailList", tradeList);
            model.addAttribute("path", path);


            if (StringUtils.isNotBlank(pqcode)) {
                return "tradeSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "tradeSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "tradeSearch::columns_recorde";
                }
            }

        } else if ("transfSearch".equals(path)) {
            //运输方式
            pageSize = 10;
            IPage<TParaPublicCodeItem> transfList = tParaPublicCodeItemService.getPageList(currentPage, pageSize, "TRANSF_COMBINATION", keywords, keywords2);
            model.addAttribute("detailList", transfList);
            model.addAttribute("path", path);
            if (StringUtils.isNotBlank(pqcode)) {
                return "transfSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "transfSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "transfSearch::columns_recorde";
                }
            }

        } else if ("unitSearch".equals(path)) {
            //计量单位
            pageSize = 10;
            IPage<TParaPublicCodeItem> unitList = tParaPublicCodeItemService.getPageList(currentPage, pageSize, "UNIT", keywords, keywords2);
            model.addAttribute("detailList", unitList);
            model.addAttribute("path", path);
            if (StringUtils.isNotBlank(pqcode)) {
                return "unitSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "unitSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "unitSearch::columns_recorde";
                }
            }

        } else if ("wrapSearch".equals(path)) {
            //包装种类
            pageSize = 10;
            IPage<TParaPublicCodeItem> tradeList = tParaPublicCodeItemService.getPageList(currentPage, pageSize, "WRAP_COMBINATION", keywords, keywords2);
            model.addAttribute("detailList", tradeList);
            model.addAttribute("path", path);

            if (StringUtils.isNotBlank(pqcode)) {
                return "wrapSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "wrapSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "wrapSearch::columns_recorde";
                }
            }
        } else if ("transacSearch".equals(path)) {
            //成交方式
            pageSize = 10;
            IPage<TParaPublicCodeItem> transacList = tParaPublicCodeItemService.getPageList(currentPage, pageSize, "TRANSAC", keywords, keywords2);
            model.addAttribute("detailList", transacList);
            model.addAttribute("path", path);
            if (StringUtils.isNotBlank(pqcode)) {
                return "transacSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "transacSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "transacSearch::columns_recorde";
                }
            }

        } else if ("purposeSearch".equals(path)) {
            //用途查询
            pageSize = 10;
            IPage<TParaPurpose> currList = tParaPurposeService.getPageList(currentPage, pageSize, keywords, keywords2);
            model.addAttribute("detailList", currList);
            model.addAttribute("path", path);
            if (StringUtils.isNotBlank(pqcode)) {
                return "purposeSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "purposeSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "purposeSearch::columns_recorde";
                }
            }
        } else if ("levyTypeSearch".equals(path)) {
            //征免性质
            pageSize = 10;
            IPage<TParaPublicCodeItem> tradeList = tParaPublicCodeItemService.getPageList(currentPage, pageSize, "LEVYTYPE", keywords, keywords2);
            model.addAttribute("detailList", tradeList);
            model.addAttribute("path", path);

            if (StringUtils.isNotBlank(pqcode)) {
                return "levyTypeSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "levyTypeSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "levyTypeSearch::columns_recorde";
                }
            }


        } else if ("customsRelSearch".equals(path)) {
            //关区代码
            pageSize = 10;
            IPage<TParaPublicCodeItem> customsRelList = tParaPublicCodeItemService.getPageList(currentPage, pageSize, "CUSTOMS_REL", keywords, keywords2);
            model.addAttribute("detailList", customsRelList);
            model.addAttribute("path", path);
            if (StringUtils.isNotBlank(pqcode)) {
                return "customsRelSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "customsRelSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "customsRelSearch::columns_recorde";
                }
            }
        } else if ("nationalityArea".equals(path)) {
            //国别地区查询
            pageSize = 10;
            IPage<TParaPublicCodeItem> tradeList = tParaPublicCodeItemService.getPageList(currentPage, pageSize, "COUNTRY_OUTDATED", keywords, keywords2);
            model.addAttribute("detailList", tradeList);
            model.addAttribute("path", path);
            if (StringUtils.isNotBlank(pqcode)) {
                return "nationalityArea";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "nationalityArea::search_recorde";
                } else {
                    //刷新 列表
                    return "nationalityArea::columns_recorde";
                }
            }
        } else if ("internalAreaSearch".equals(path)) {
               //国内地区代码查询
            pageSize = 10;
            IPage<TParaPublicCodeItem> tradeList = tParaPublicCodeItemService.getPageList(currentPage, pageSize, "AREA", keywords, keywords2);
            model.addAttribute("detailList", tradeList);
            if (StringUtils.isNotBlank(pqcode)) {
                return "internalAreaSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "internalAreaSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "internalAreaSearch::columns_recorde";
                }
            }
        } else if ("administrativeArea".equals(path)) {
            model.addAttribute("path", path);
            //行政区划代码
            pageSize = 10;
            IPage<TParaPublicCodeItem> tradeList = tParaPublicCodeItemService.getPageList(currentPage, pageSize, "POST_AREA", keywords, keywords2);
            model.addAttribute("detailList", tradeList);
            if (StringUtils.isNotBlank(pqcode)) {
                return "administrativeArea";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "administrativeArea::search_recorde";
                } else {
                    //刷新 列表
                    return "administrativeArea::columns_recorde";
                }
            }
        } else if ("classify".equals(path)) {
            //归类实例
            pageSize = 10;
            IPage<TParaEntryHscode> pageList = tParaEntryHscodeService.getPageList(currentPage, pageSize, keywords, keywords2);
            model.addAttribute("detailList", pageList);
            model.addAttribute("path", path);
            if (StringUtils.isNotBlank(pqcode)) {
                return "classify";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "classify::search_recorde";
                } else {
                    //刷新 列表
                    return "classify::columns_recorde";
                }
            }
        } else if ("consumerStandardSearch".equals(path)) {
            model.addAttribute("path", path);
            //单耗标准查询
            pageSize = 10;
            IPage<ConsumeStandard> pageList = consumeStandardService.getPageList(currentPage, pageSize, keywords,keywords2);
            model.addAttribute("detailList", pageList);
            if (StringUtils.isNotBlank(pqcode)) {
                return "consumerStandardSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "consumerStandardSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "consumerStandardSearch::columns_recorde";
                }
            }

        } else if ("exchangeRateSearch".equals(path)) {
            //外汇汇率查询
            pageSize = 10;
            IPage<TParaExchrate> pageList = itParaExchangeRateService.getPageList(currentPage, pageSize, keywords, keywords2);
            List<TParaExchrateVo> exchangeList = itParaExchangeRateService.getExchangeType();
            model.addAttribute("detailList", pageList);
            model.addAttribute("exchangeList", exchangeList);
            model.addAttribute("path", path);

            if (StringUtils.isNotBlank(pqcode)) {
                return "exchangeRateSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "exchangeRateSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "exchangeRateSearch::columns_recorde";
                }
            }
        } else if ("tpara3CCertSearch".equals(path)) {
            //3C认证查询
            pageSize = 10;
            IPage<TPara3CCert> tradeList = tPara3CCertService.getPageList(currentPage, pageSize, keywords, keywords2);
            model.addAttribute("detailList", tradeList);
            model.addAttribute("path", path);

            if (StringUtils.isNotBlank(pqcode)) {
                return "tpara3CCertSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "tpara3CCertSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "tpara3CCertSearch::columns_recorde";
                }
            }
            //加贸禁止类
        } else if ("complex_banSearch".equals(path)) {
            pageSize = 10;
            IPage<TParaTradeComplexBan> tradeList = tParaTradeComplexBanService.getPageList(currentPage, pageSize, keywords, keywords2);
            model.addAttribute("detailList", tradeList);
            model.addAttribute("path", path);

            if (StringUtils.isNotBlank(pqcode)) {
                return "complex_banSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "complex_banSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "complex_banSearch::columns_recorde";
                }
            }
            //禁止类
        } else if ("tParaComplexBanSearch".equals(path)) {
            pageSize = 10;
            IPage<TParaComplexBan> tradeList = tParaComplexBanService.getPageList(currentPage, pageSize, keywords, keywords2);
            model.addAttribute("detailList", tradeList);
            model.addAttribute("path", path);
            if (StringUtils.isNotBlank(pqcode)) {
                return "tParaComplexBanSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "tParaComplexBanSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "tParaComplexBanSearch::columns_recorde";
                }
            }
        } else if ("complexLimitSearch".equals(path)) {
            //加贸限制类查询
            pageSize = 10;
            IPage<Map<Object, String>> tParaTradeComplexLtdList = tParaTradeComplexLtdService.getPageList(currentPage, pageSize, keywords, keywords2);
            model.addAttribute("detailList", tParaTradeComplexLtdList);
            model.addAttribute("path", path);

            if (StringUtils.isNotBlank(pqcode)) {
                return "complexLimitSearch";
            } else {
                //刷新页面
                if (StringUtils.isBlank(flush)) {
                    return "complexLimitSearch::search_recorde";
                } else {
                    //刷新 列表
                    return "complexLimitSearch::columns_recorde";
                }
            }

        }


        return "funapp-throughnew";
    }


}
