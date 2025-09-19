package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.TParaTradeComplexLtdMapper;
import cloud.onion.cms.model.entity.TParaTradeComplexLtd;
import cloud.onion.cms.service.ITParaTradeComplexLtdService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Map;


/**
 * @Description: 加贸限制类查询
 * @Author: xuyang
 * @Date: 2022/11/7  13:25
 */
@Service
public class TParaTradeComplexLtdServiceImpl extends ServiceImpl<TParaTradeComplexLtdMapper, TParaTradeComplexLtd> implements ITParaTradeComplexLtdService {


    @Override
    public IPage<Map<Object,String>> getPageList(int page, int size, String keywords, String keywords2) {
        Page<Map<Object,String>> pager = new Page<>(page,size);
        IPage<Map<Object,String>> pageList = super.getBaseMapper().getPageList(pager, keywords,keywords2);
        return pageList;
    }
}
