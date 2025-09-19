package cloud.onion.cms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 允泽
 * @date 2022/9/7
 */
@Controller
@RequestMapping("error")
public class ErrorController {

    @GetMapping("401")
    public String Unauthorized() {
        return "error/401";
    }

    @GetMapping("404")
    public String NotFound() {
        return "error/404";
    }

    @GetMapping("500")
    public String Error() {
        return "error/500";
    }
}
