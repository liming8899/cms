package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.TParaMerchElementMapper;
import cloud.onion.cms.mapper.TParaPublicCodeItemMapper;
import cloud.onion.cms.model.entity.TParaMerchElement;
import cloud.onion.cms.model.entity.TParaPublicCodeItem;
import cloud.onion.cms.service.TParaPublicCodeItemService;
import cloud.onion.core.annotation.IdNumberValid;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zl
 * @Description
 * @date 2022/11/7 11:10
 **/
@Service
public class TParaPublicCodeItemServiceImpl extends ServiceImpl<TParaPublicCodeItemMapper, TParaPublicCodeItem> implements TParaPublicCodeItemService {

    @Autowired
    private TParaPublicCodeItemMapper tParaPublicCodeItemMapper;

    @Override
    public IPage<TParaPublicCodeItem> getPageList(int currentPage, int pageSize, String publicCode, String code, String name) {
        Page<TParaPublicCodeItem> pager = new Page<>(currentPage, pageSize);
        IPage<TParaPublicCodeItem> pageList = super.getBaseMapper().getPageList(pager, publicCode,code,name);
        return pageList;
    }

    /**
     * @Description: 获得币制代码数据源
     * @Author: xuyang
     * @Date: 2022/11/7  16:51
     */
    @Override
    public IPage<TParaPublicCodeItem> getCurrPageList(int currentPage, int pageSize, String publicCode, String code, String name) {
        Page<TParaPublicCodeItem> pager = new Page<>(currentPage, pageSize);
        IPage<TParaPublicCodeItem> pageList = super.getBaseMapper().getPageList(pager, publicCode,code,name);
        List<TParaPublicCodeItem> tParaPublicCodeItemList = tParaPublicCodeItemMapper.list("CURR");
        List<TParaPublicCodeItem> collect = pageList.getRecords().stream().peek(e -> {
            tParaPublicCodeItemList.forEach(tParaPublicCodeItem -> {
                if (tParaPublicCodeItem.getName().equals(e.getName())) {
                    e.setCodeEn(tParaPublicCodeItem.getCode());
                }
            });
        }).collect(Collectors.toList());
        pageList.setRecords(collect);
        return pageList;
    }
}
