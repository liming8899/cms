package cloud.onion.cms.controller;

import cloud.onion.cms.model.entity.LoginInfo;
import cloud.onion.cms.model.req.ConsultReq;
import cloud.onion.cms.service.IConsultService;
import cloud.onion.cms.service.ILoginInfoService;
import cloud.onion.core.result.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author 允泽
 * @date 2022/7/29
 */
@RestController
@RequestMapping("consult")
@Api(tags = "咨询信息处理接口")
public class ConsultController {

    @Autowired
    private IConsultService consultService;

    @Autowired
    private ILoginInfoService loginInfoService;

    @PostMapping
    @ApiOperation(value = "新增资讯信息")
    public Object create(@RequestBody @Valid ConsultReq req) {
        consultService.insertOne(req);

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setToLink("/consult");
        loginInfo.setVisitMoudle("我要咨询");
        loginInfoService.insertEntity(loginInfo);
        return ResultJson.success();
    }
}
