package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.TParaComplexChangeMapper;
import cloud.onion.cms.model.entity.TParaComplexChange;
import cloud.onion.cms.service.ITParaComplexChangeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author llcao
 * @Description:
 * @date
 */
@Service
public class TParaComplexChangeServiceImpl extends ServiceImpl<TParaComplexChangeMapper, TParaComplexChange> implements ITParaComplexChangeService {


    @Override
    public IPage<Map<Object,String>> getPageList(int page, int size, String keywords, String keywords2) {
        Page<Map<Object,String>> pager = new Page<>(page, size);
        IPage<Map<Object,String>> pageList = super.getBaseMapper().getPageList(pager, keywords,keywords2);
        return pageList;
    }
}
