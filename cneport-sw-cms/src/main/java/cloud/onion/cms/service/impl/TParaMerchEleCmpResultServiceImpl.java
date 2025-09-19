package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.TParaMerchEleCmpResultMapper;
import cloud.onion.cms.model.entity.TPara3CCert;
import cloud.onion.cms.model.entity.TParaMerchEleCmpResult;
import cloud.onion.cms.service.TParaMerchEleCmpResultService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zl
 * @Description
 * @date 2022/11/8 8:54
 **/
@Service
public class TParaMerchEleCmpResultServiceImpl extends ServiceImpl<TParaMerchEleCmpResultMapper,TParaMerchEleCmpResult> implements TParaMerchEleCmpResultService {
    @Override
    public IPage<TParaMerchEleCmpResult> getPageList(int currentPage, int pageSize, String keywords, String keywords2) {
        Page<TParaMerchEleCmpResult> pager = new Page<>(currentPage, pageSize);
        IPage<TParaMerchEleCmpResult> pageList = super.getBaseMapper().getPageList(pager, keywords,keywords2);
        return pageList;
    }
}
