package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.ConstructHistoryInfo;
import cloud.onion.cms.model.res.ConstructHistoryRes;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface IHistoryService extends IService<ConstructHistoryInfo> {
    IPage<ConstructHistoryRes> pages(int currentPage, int pageSize, String keywords);
}
