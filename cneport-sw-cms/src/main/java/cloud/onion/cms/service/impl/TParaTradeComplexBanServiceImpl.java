package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.TParaTradeComplexBanMapper;
import cloud.onion.cms.model.entity.TPara3CCert;
import cloud.onion.cms.model.entity.TParaTradeComplexBan;
import cloud.onion.cms.service.TParaTradeComplexBanService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zl
 * @Description
 * @date 2022/11/7 16:19
 **/
@Service
public class TParaTradeComplexBanServiceImpl extends ServiceImpl<TParaTradeComplexBanMapper, TParaTradeComplexBan> implements TParaTradeComplexBanService {
    @Override
    public IPage<TParaTradeComplexBan> getPageList(int page, int size, String keywords, String keywords2) {

        Page<TParaTradeComplexBan> pager = new Page<>(page, size);
        IPage<TParaTradeComplexBan> pageList = super.getBaseMapper().getPageList(pager, keywords,keywords2);
        return pageList;
    }
}
