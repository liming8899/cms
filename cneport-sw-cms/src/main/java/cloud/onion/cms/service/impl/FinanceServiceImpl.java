package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.FinanceContentReviewMapper;
import cloud.onion.cms.model.res.FinanceAgencyRes;
import cloud.onion.cms.model.res.FinanceHomepageRes;
import cloud.onion.cms.model.res.FinanceProductRes;
import cloud.onion.cms.service.IFinanceService;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FinanceServiceImpl implements IFinanceService {

    @Autowired
    private FinanceContentReviewMapper financeContentReviewMapper;

    @Override
    public FinanceAgencyRes getFinanceAgencyDetails(Long agencyId) throws NotFoundException {
        Map<String, Long> params = new HashMap<>();
        params.put("agencyId", agencyId);

        List<FinanceAgencyRes> financeAgencyResList = Optional.ofNullable(financeContentReviewMapper.getFinanceAgencyList(params)).orElseThrow(() -> new NotFoundException("404"));
        return financeAgencyResList.get(0);
    }

    @Override
    public List<FinanceProductRes> getFinanceProductResList(Long agencyId) {
        Map<String, Long> params = new HashMap<>();
        params.put("agencyId", agencyId);
        return financeContentReviewMapper.getFinanceProductList(params);
    }

    @Override
    public FinanceProductRes getFinanceProductDetails(Long productId) throws NotFoundException {
        Map<String, Long> params = new HashMap<>();
        params.put("productId", productId);

        List<FinanceProductRes> financeProductResList = Optional.ofNullable(financeContentReviewMapper.getFinanceProductList(params)).orElseThrow(() -> new NotFoundException("404"));
        return financeProductResList.get(0);
    }

    @Override
    public List<FinanceHomepageRes> getFinanceInfo() {
        //获取所有可用版块
        List<FinanceHomepageRes> financeHomepageResList = financeContentReviewMapper.getFinanceHomePageList();
        //获取所有可用机构
        List<FinanceAgencyRes> financeAgencyResList = financeContentReviewMapper.getFinanceAgencyList(null);
        //获取所有可用金融产品
        List<FinanceProductRes> financeProductResList = financeContentReviewMapper.getFinanceProductList(null);

        List<FinanceHomepageRes> collect = financeHomepageResList.stream().peek(financeHomepageRes -> {
            financeHomepageRes.setFinanceAgencyResList(financeAgencyResList.stream().filter(financeAgency -> financeAgency.getHomepageId().equals(financeHomepageRes.getId())).collect(Collectors.toList()));
        }).collect(Collectors.toList());

        for (FinanceHomepageRes financeHomepageRes:collect) {
            financeHomepageRes.setFinanceAgencyResList(financeHomepageRes.getFinanceAgencyResList().stream().peek(financeAgencyRes -> {
                financeAgencyRes.setFinanceProductResList(financeProductResList.stream().filter(financeProductRes -> financeProductRes.getAgencyId().equals(financeAgencyRes.getId())).collect(Collectors.toList()));
            }).collect(Collectors.toList()));
        }

        return collect;
    }

}
