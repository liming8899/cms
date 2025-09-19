package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.ConsultMapper;
import cloud.onion.cms.model.convertor.ConsultConvertor;
import cloud.onion.cms.model.entity.Consult;
import cloud.onion.cms.model.req.ConsultReq;
import cloud.onion.cms.service.IConsultService;
import cloud.onion.core.exception.BizException;
import cloud.onion.core.result.ResultCode;
import cloud.onion.core.utils.ContextUtil;
import cloud.onion.core.utils.IpAddressUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 允泽
 * @date 2022/8/11
 */
@Service
public class ConsultServiceImpl extends ServiceImpl<ConsultMapper, Consult> implements IConsultService {

    @Override
    public void insertOne(ConsultReq req) {
        String ipAddress = IpAddressUtil.getIpAddr(ContextUtil.getRequest());
        LambdaQueryWrapper<Consult> wrapper = Wrappers.<Consult>lambdaQuery();
        wrapper.eq(Consult::getIpAddress, ipAddress);
        wrapper.apply("TO_DAYS(create_time) = TO_DAYS(NOW())");
        long count = super.count(wrapper);
        if (count > 3) {
            throw new BizException("请勿频繁提交");
        }
        Consult entity = ConsultConvertor.INSTANCE.from(req);
        entity.setIpAddress(ipAddress);
        boolean result = super.save(entity);
        if (!result) {
            throw new BizException(ResultCode.INSERT_FAIL);
        }
    }
}
