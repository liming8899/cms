package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.ConsumeStandardMapper;
import cloud.onion.cms.mapper.TParaExchrateMapper;
import cloud.onion.cms.model.entity.ConsumeStandard;
import cloud.onion.cms.model.entity.TParaExchrate;
import cloud.onion.cms.service.ConsumeStandardService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Li
 */
@Service
public class ConsumeStandardServiceImpl extends ServiceImpl<ConsumeStandardMapper, ConsumeStandard> implements ConsumeStandardService {
    @Override
    public IPage<ConsumeStandard> getPageList(int currentPage, int pageSize, String keywords, String keywords2) {
        Page<TParaExchrate> pager = new Page<>(currentPage, pageSize);
        return super.getBaseMapper().getPageList(pager, keywords,keywords2);
    }
}
