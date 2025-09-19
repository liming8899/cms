package cloud.onion.cms.mapper;

import cloud.onion.cms.model.entity.LoginInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 允泽
 * @date 2022/9/15
 */
@Mapper
public interface LoginInfoMapper extends BaseMapper<LoginInfo> {
    @Select("select * from visit_log_info where ip_address=#{ipAddress}")
    List<LoginInfo> getLoginInfoByIp(String ipAddress);
}
