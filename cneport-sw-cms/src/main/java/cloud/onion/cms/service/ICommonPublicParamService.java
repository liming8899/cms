package cloud.onion.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

public interface ICommonPublicParamService {

    IPage<?> getPageList(int currentPage, int pageSize, String keywords, String keywords2);

    IPage<?> getPageList(int currentPage, int pageSize, String publicCode, String code, String name);
}
