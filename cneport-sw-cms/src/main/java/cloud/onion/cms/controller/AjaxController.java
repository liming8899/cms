package cloud.onion.cms.controller;

import cloud.onion.cms.service.IXhArticleService;
import cloud.onion.core.result.ResultJson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 允泽
 * @date 2022/8/2
 */
@RestController
@RequestMapping("ajax")
@Api(tags = "前台ajax请求接口")
public class AjaxController {

    @Autowired
    private IXhArticleService articleService;

    @ApiOperation("查询国情图解")
    @GetMapping("country/img/{id}/{titleContent}")
    public Object getCountryImg(@ApiParam(value = "国家对应栏目id",required = true) @PathVariable(value = "id")Long id,  @PathVariable(value = "titleContent")String titleContent) {
        return ResultJson.success(articleService.getImageByCatalogId(id, titleContent));
    }
}
