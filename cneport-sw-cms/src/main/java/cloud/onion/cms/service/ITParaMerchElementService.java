package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.TParaMerchElement;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface ITParaMerchElementService {
    IPage<TParaMerchElement> getPageList(int currentPage, int pageSize, String keywords,String keywords2);
}
