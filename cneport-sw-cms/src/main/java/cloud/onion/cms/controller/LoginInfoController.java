package cloud.onion.cms.controller;

import cloud.onion.cms.model.entity.LoginInfo;
import cloud.onion.cms.service.ILoginInfoService;
import cloud.onion.core.result.ResultJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author 允泽
 * @date 2022/8/11
 */
@Controller
@RequestMapping("loginInfo")
public class LoginInfoController {

    @Autowired
    private ILoginInfoService loginInfoService;
    @PostMapping
    @ResponseBody
    public Object create(@RequestBody LoginInfo req) {
        loginInfoService.insertEntity(req);
        return ResultJson.success();
    }

    /**
     * 物通调用接口
     * @param req
     * @return
     */
    @PostMapping("/newLog")
    @ResponseBody
    public Object newLog(@RequestBody LoginInfo req) {
        loginInfoService.insertNewEntity(req);
        return ResultJson.success();
    }
}
