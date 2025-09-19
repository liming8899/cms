package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.TParaComplexMapper;
import cloud.onion.cms.mapper.TParaEntryHscodeMapper;
import cloud.onion.cms.model.entity.TParaComplex;
import cloud.onion.cms.model.entity.TParaEntryHscode;
import cloud.onion.cms.model.entity.TParaExchrate;
import cloud.onion.cms.service.TParaEntryHscodeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Li
 */
@Service
public class TParaEntryHscodeServiceImpl extends ServiceImpl<TParaEntryHscodeMapper, TParaEntryHscode> implements TParaEntryHscodeService {
    @Override
    public IPage<TParaEntryHscode> getPageList(int currentPage, int pageSize, String keywords, String keywords2) {
        Page<TParaEntryHscode> pager = new Page<>(currentPage, pageSize);
        return super.getBaseMapper().getPageList(pager, keywords, keywords2);
    }
}
