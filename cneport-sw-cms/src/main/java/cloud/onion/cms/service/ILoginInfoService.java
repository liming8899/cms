package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.LoginInfo;
import com.baomidou.mybatisplus.extension.service.IService;


/**
 * @author 允泽
 * @date 2022/9/15
 */
public interface ILoginInfoService extends IService<LoginInfo> {
    void insertEntity(LoginInfo req);

    void insertNewEntity(LoginInfo req);
}
