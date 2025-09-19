package cloud.onion.cms.service;

import cloud.onion.cms.model.res.FinanceAgencyRes;
import cloud.onion.cms.model.res.FinanceHomepageRes;
import cloud.onion.cms.model.res.FinanceProductRes;
import org.apache.ibatis.javassist.NotFoundException;

import java.util.List;

public interface IFinanceService {

    FinanceAgencyRes getFinanceAgencyDetails(Long agencyId) throws NotFoundException;

    List<FinanceProductRes> getFinanceProductResList(Long agencyId);

    FinanceProductRes getFinanceProductDetails(Long productId) throws NotFoundException;

    List<FinanceHomepageRes> getFinanceInfo();
}
