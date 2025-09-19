package cloud.onion.cms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;

public class ICommonPublicParamAdapter implements ICommonPublicParamService{
    @Override
    public IPage<?> getPageList(int currentPage, int pageSize, String keywords, String keywords2) {
        return null;
    }

    @Override
    public IPage<?> getPageList(int currentPage, int pageSize, String publicCode, String code, String name) {
        return null;
    }
}
