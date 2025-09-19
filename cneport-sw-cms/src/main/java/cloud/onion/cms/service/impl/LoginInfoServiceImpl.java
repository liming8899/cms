package cloud.onion.cms.service.impl;

import cloud.onion.cms.mapper.LoginInfoMapper;
import cloud.onion.cms.model.entity.LoginInfo;
import cloud.onion.cms.service.ILoginInfoService;
import cloud.onion.core.utils.ContextUtil;
import cloud.onion.core.utils.IpAddressUtil;
import cloud.onion.core.utils.IpUtils;
import cn.dev33.satoken.stp.StpUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 允泽
 * @date 2022/9/15
 */
@Service
public class LoginInfoServiceImpl extends ServiceImpl<LoginInfoMapper, LoginInfo> implements ILoginInfoService {
    @Resource
    LoginInfoMapper loginInfoMapper;
    @Resource
    RedisTemplate redisTemplate;

    @Override
    public void insertEntity(LoginInfo loginInfo) {
        if (StpUtil.isLogin()){
            //登录账号
            loginInfo.setLoginAccount(StpUtil.getExtra("username")==null?"":StpUtil.getExtra("username").toString());
            //登录人姓名
            loginInfo.setUsername(StpUtil.getExtra("realName")==null?"":StpUtil.getExtra("realName").toString());
        }
        loginInfo.setVisitTime(new Date());

        String ipAddress = IpAddressUtil.getIpAddr(ContextUtil.getRequest());
        loginInfo.setIpAddress(ipAddress);
        List<LoginInfo> loginInfos = loginInfoMapper.getLoginInfoByIp(ipAddress);
        if (CollectionUtils.isEmpty(loginInfos)){
            Map<String, String> stringStringMap = IpUtils.requestIp(ipAddress);
            if (stringStringMap != null) {
                String country = stringStringMap.get("country");
                String regionName = stringStringMap.get("province");
                String city = stringStringMap.get("city");
                loginInfo.setCountry(country);
                //国内IP记录省市
                if ("中国".equals(country)) {
                    loginInfo.setRegion(regionName);
                    loginInfo.setCity(city);
                }
            }
        }else {
            LoginInfo info = loginInfos.stream().findFirst().get();
            loginInfo.setCountry(info.getCountry());
            loginInfo.setRegion(info.getRegion());
            loginInfo.setCity(info.getCity());
        }
        loginInfoMapper.insert(loginInfo);

    }

    @Override
    public void insertNewEntity(LoginInfo loginInfo) {
        String ipAddress = loginInfo.getIpAddress();
        List<LoginInfo> loginInfos = loginInfoMapper.getLoginInfoByIp(ipAddress);
        if (CollectionUtils.isEmpty(loginInfos)){
            Map<String, String> stringStringMap = IpUtils.requestIp(ipAddress);
            if (stringStringMap != null) {
                String country = stringStringMap.get("country");
                String regionName = stringStringMap.get("province");
                String city = stringStringMap.get("city");
                loginInfo.setCountry(country);
                //国内IP记录省市
                if ("中国".equals(country)) {
                    loginInfo.setRegion(regionName);
                    loginInfo.setCity(city);
                }
            }
        }else {
            LoginInfo info = loginInfos.stream().findFirst().get();
            loginInfo.setCountry(info.getCountry());
            loginInfo.setRegion(info.getRegion());
            loginInfo.setCity(info.getCity());
        }
        loginInfoMapper.insert(loginInfo);

    }
}
