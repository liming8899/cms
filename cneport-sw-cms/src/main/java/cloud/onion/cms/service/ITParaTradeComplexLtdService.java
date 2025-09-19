package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.TParaTradeComplexLtd;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

/**
 * @Description: 加贸限制类查询
 * @Author: xuyang
 * @Date: 2022/11/7  13:25
 */
public interface ITParaTradeComplexLtdService {
    public IPage<Map<Object,String>> getPageList(int page, int size, String keywords, String keywords2);
}
