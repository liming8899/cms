package cloud.onion.cms.service;

import cloud.onion.cms.model.entity.Consult;
import cloud.onion.cms.model.req.ConsultReq;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author 允泽
 * @date 2022/8/11
 */
public interface IConsultService extends IService<Consult> {

    void insertOne(ConsultReq req);
}
