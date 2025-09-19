package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.TParaComplexBan;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface TParaComplexBanService {
    IPage<TParaComplexBan> getPageList(int currentPage, int pageSize, String keywords, String keywords2);
}
