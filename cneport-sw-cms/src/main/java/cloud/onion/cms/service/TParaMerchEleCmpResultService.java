package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.TParaMerchEleCmpResult;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface TParaMerchEleCmpResultService {
    IPage<TParaMerchEleCmpResult> getPageList(int currentPage, int pageSize, String keywords, String keywords2);
}
