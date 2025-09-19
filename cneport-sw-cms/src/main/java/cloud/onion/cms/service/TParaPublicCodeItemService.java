package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.TParaPublicCodeItem;
import com.baomidou.mybatisplus.core.metadata.IPage;

public interface TParaPublicCodeItemService {
    IPage<TParaPublicCodeItem> getPageList(int currentPage, int pageSize, String publicCode, String code, String name);

    /**
     * @Description: 获得币制代码数据源
     * @Author: xuyang
     * @Date: 2022/11/7  16:51
     */
    IPage<TParaPublicCodeItem> getCurrPageList(int currentPage, int pageSize,String publicCode,  String code, String name);
}
