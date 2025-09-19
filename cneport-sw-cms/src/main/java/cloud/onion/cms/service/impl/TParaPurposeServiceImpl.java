package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.TParaPurposeMapper;
import cloud.onion.cms.model.entity.TParaPurpose;
import cloud.onion.cms.service.TParaPurposeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zl
 * @Description
 * @date 2022/11/11 9:43
 **/
@Service
public class TParaPurposeServiceImpl extends ServiceImpl<TParaPurposeMapper, TParaPurpose> implements TParaPurposeService {
    @Override
    public IPage<TParaPurpose> getPageList(int currentPage, int pageSize, String keywords, String keywords2) {
        Page<TParaPurpose> tParaPurposePage = new Page<>(currentPage, pageSize);
        IPage<TParaPurpose> pageList = super.getBaseMapper().getPageList(tParaPurposePage, keywords, keywords2);
        return pageList;
    }
}
