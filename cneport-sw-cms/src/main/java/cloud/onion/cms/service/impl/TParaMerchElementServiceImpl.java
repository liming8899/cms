package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.TParaMerchElementMapper;
import cloud.onion.cms.model.entity.TParaMerchElement;
import cloud.onion.cms.service.ITParaMerchElementService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author llcao
 * @Description:
 * @date
 */
@Service
public class TParaMerchElementServiceImpl extends ServiceImpl<TParaMerchElementMapper, TParaMerchElement> implements ITParaMerchElementService {
    @Override
    public IPage<TParaMerchElement> getPageList(int page, int size, String keywords, String keywords2) {
        Page<TParaMerchElement> pager = new Page<>(page, size);
        IPage<TParaMerchElement> pageList = super.getBaseMapper().getPageList(pager, keywords,keywords2);
        return pageList;
    }
}
