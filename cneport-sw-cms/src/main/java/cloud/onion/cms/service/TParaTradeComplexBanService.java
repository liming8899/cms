package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.TParaTradeComplexBan;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface TParaTradeComplexBanService {
    IPage<TParaTradeComplexBan> getPageList(int currentPage, int pageSize, String keywords, String keywords2);
}
