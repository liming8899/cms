package cloud.onion.cms.service;


import cloud.onion.cms.model.entity.Link;
import cloud.onion.cms.model.res.LinkRes;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author 允泽
 * @date 2022/7/15
 */
public interface ILinkService extends IService<Link> {

    /**
     * 获取链接数据，两级数据
     * @return
     */
    List<LinkRes> getLists();
}
