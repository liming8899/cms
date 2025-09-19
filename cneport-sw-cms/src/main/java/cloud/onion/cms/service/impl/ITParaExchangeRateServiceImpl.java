package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.TParaComplexChangeMapper;
import cloud.onion.cms.mapper.TParaExchrateMapper;
import cloud.onion.cms.model.entity.TParaComplexChange;
import cloud.onion.cms.model.entity.TParaExchrate;
import cloud.onion.cms.model.entity.TParaMerchElement;
import cloud.onion.cms.model.entity.vo.TParaExchrateVo;
import cloud.onion.cms.service.ITParaExchangeRateService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ITParaExchangeRateServiceImpl extends ServiceImpl<TParaExchrateMapper, TParaExchrate> implements ITParaExchangeRateService {
    @Override
    public IPage<TParaExchrate> getPageList(int page, int size, String keywords, String keywords2) {
        Page<TParaExchrate> pager = new Page<>(page, size);
        return super.getBaseMapper().getPageList(pager, keywords,keywords2);
    }

    @Override
    public List<TParaExchrateVo> getExchangeType() {
        return super.getBaseMapper().getExchangeType();
    }
}
