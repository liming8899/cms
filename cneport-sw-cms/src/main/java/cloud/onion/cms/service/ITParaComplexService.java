package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.TParaComplex;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

public interface ITParaComplexService {
    public IPage<Map<Object,String>> getPageList(int currentPage, int pageSize, String keywords, String keywords2);
}
