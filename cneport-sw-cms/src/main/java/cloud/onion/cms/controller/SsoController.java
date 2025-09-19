package cloud.onion.cms.controller;

import cloud.onion.core.result.ResultJson;
import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.sso.SaSsoHandle;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 允泽
 * @date 2022/7/27
 */
@Slf4j
@RestController
public class SsoController {

    @Value("${mainUrl}")
    private String mainUrl;

    @RequestMapping("/sso/*")
    public Object ssoRequest() {
        return SaSsoHandle.clientRequest();
    }

    // 配置SSO相关参数
    @Autowired
    private void configSso(SaSsoConfig sso) {
        // 配置Http请求处理器
        sso.setSendHttp(HttpUtil::get);
    }

    @RequestMapping("/cmsWeb/isLogin")
    public Object isLogin() {
        return StpUtil.isLogin();
    }

    /**
     * 上综服平台退出，返回主页
     */
    @GetMapping("/sso/logout")
    @ResponseBody
    public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("系统登出："+request.getParameter("mainUrl"));
        try {
            boolean login = StpUtil.isLogin();
            log.info("判断是否退出："+login);
            if (login) {
                StpUtil.logout();
            }
        }catch (Exception e){
            log.error("退出失败："+e.getMessage(),e);
        }
        response.sendRedirect(mainUrl);
    }
}
