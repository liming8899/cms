package cloud.onion.cms.service.impl;
import cloud.onion.cms.model.entity.TParaComplexBan;
import cloud.onion.cms.mapper.TParaComplexBanMapper;
import cloud.onion.cms.model.entity.TPara3CCert;
import cloud.onion.cms.service.TParaComplexBanService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author zl
 * @Description
 * @date 2022/11/7 16:54
 **/
@Service
public class TParaComplexBanServiceImpl extends ServiceImpl<TParaComplexBanMapper,TParaComplexBan> implements TParaComplexBanService {
    @Override
    public IPage<TParaComplexBan>  getPageList(int page, int size, String keywords, String keywords2) {
        Page<TParaComplexBan> pager = new Page<>(page, size);
        IPage<TParaComplexBan> pageList = super.getBaseMapper().getPageList(pager, keywords,keywords2);
        return pageList;
    }
}
