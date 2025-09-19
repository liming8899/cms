package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.Page;
import cloud.onion.cms.model.res.PageRes;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.javassist.NotFoundException;

/**
 * @author 允泽
 * @date 2022/9/5
 */
public interface IPageService extends IService<Page> {

    PageRes view(String accessPath) throws NotFoundException;
}
