package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.TPara3CCertMapper;
import cloud.onion.cms.mapper.TParaComplexChangeMapper;
import cloud.onion.cms.model.entity.TPara3CCert;
import cloud.onion.cms.model.entity.TParaComplexChange;
import cloud.onion.cms.service.TPara3CCertService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author zl
 * @Description
 * @date 2022/11/7 15:26
 **/
@Service
public class TPara3CCertServiceImpl extends ServiceImpl<TPara3CCertMapper, TPara3CCert> implements TPara3CCertService {
    @Override
    public IPage<TPara3CCert>  getPageList(int page, int size, String keywords, String keywords2) {
        Page<TPara3CCert> pager = new Page<>(page, size);
        IPage<TPara3CCert> pageList = super.getBaseMapper().getPageList(pager, keywords,keywords2);
        return pageList;
    }
}
