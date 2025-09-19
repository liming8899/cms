package cloud.onion.cms.utils;

import cloud.onion.cms.config.sign.RequestHeader;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.SortedMap;

@Slf4j
@UtilityClass
public class SignUtil {
    /**
     * 验证签名
     * 验证算法：把timestamp + JSONUtil.toJsonStr(SortedMap)合成字符串，然后MD5
     */
    @SneakyThrows
    public  boolean verifySign(SortedMap<String, Object> map, RequestHeader requestHeader) {
        StringBuilder paramMap = new StringBuilder();
        for (String key : map.keySet()) {
            Object value = map.get(key);
            paramMap.append("&").append(key).append("=").append(value);
        }
        String params = requestHeader.getNonce() + requestHeader.getTimestamp() + paramMap.toString();
        return verifySign(params, requestHeader);
    }

    /**
     * 验证签名
     */
    public boolean verifySign(String params, RequestHeader requestHeader) {
        log.info("客户端签名: {}", requestHeader.getSign());
        if (StringUtils.isEmpty(params)) {
            return false;
        }
        log.info("客户端上传内容: {}", params);
        String paramsSign = DigestUtils.md5DigestAsHex(params.getBytes()).toUpperCase();
        log.info("客户端上传内容加密后的签名结果: {}", paramsSign);
        return requestHeader.getSign().toUpperCase().equals(paramsSign);
    }
}