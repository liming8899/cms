package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.TParaPurpose;
import com.baomidou.mybatisplus.core.metadata.IPage;


public interface TParaPurposeService {
    /**
     * 用途
     * @param currentPage
     * @param pageSize
     * @param keywords
     * @param keywords2
     * @return
     */
    IPage<TParaPurpose> getPageList(int currentPage, int pageSize, String keywords, String keywords2);
}
