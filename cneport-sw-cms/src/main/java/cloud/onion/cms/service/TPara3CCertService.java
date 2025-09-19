package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.TPara3CCert;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.Map;

/**
 * @author zl
 * @Description
 * @date 2022/11/7 15:26
 **/
public interface TPara3CCertService {

    IPage<TPara3CCert> getPageList(int currentPage, int pageSize, String keywords, String keywords2);
}
