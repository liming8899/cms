package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.TParaComplexChange;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

public interface ITParaComplexChangeService {
    IPage<Map<Object,String>> getPageList(int currentPage, int pageSize, String keywords, String keywords2);
}
