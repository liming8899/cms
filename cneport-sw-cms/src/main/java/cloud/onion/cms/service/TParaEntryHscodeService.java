package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.ConsumeStandard;
import cloud.onion.cms.model.entity.TParaEntryHscode;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * @author Li
 */
public interface TParaEntryHscodeService {
    /**
     * 获取 归类实例列表信息
     * @param currentPage 当前页数
     * @param pageSize 页数
     * @param keywords 查询关键字
     * @param keywords2 查询关键字
     * @return IPage<TParaExchrate>
     */
    IPage<TParaEntryHscode> getPageList(int currentPage, int pageSize, String keywords, String keywords2);
}
