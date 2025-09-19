package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.TParaExchrate;
import cloud.onion.cms.model.entity.TParaMerchElement;
import cloud.onion.cms.model.entity.vo.TParaExchrateVo;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface ITParaExchangeRateService {
    /**
     * 获取 外汇汇率列表信息
     * @param currentPage 当前页数
     * @param pageSize 页数
     * @param keywords 查询关键字
     * @param keywords2 查询关键字
     * @return IPage<TParaExchrate>
     */
    IPage<TParaExchrate> getPageList(int currentPage, int pageSize, String keywords, String keywords2);

    /**
     * 获取当前币制种类
     * @return List<String>
     */
    List<TParaExchrateVo> getExchangeType();
}
