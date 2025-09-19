//package cloud.onion.cms.config.sign;
//
//import cloud.onion.cms.utils.HttpDataUtil;
//import cloud.onion.cms.utils.RedisUtil;
//import cloud.onion.cms.utils.SignUtil;
//import cloud.onion.cms.utils.WebUtils;
//import cloud.onion.core.result.ResultCode;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.ObjectUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletRequestWrapper;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.SortedMap;
//
//@Slf4j
//public class SignFilter implements Filter {
//    @Autowired
//    private RedisUtil redisUtil;
//
//    //从fitler配置中获取sign过期时间
//    private Long signMaxTime;
//
//    private static final String NONCE_KEY = "x-nonce-";
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
//        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
//        HttpServletRequestWrapper requestWrapper = new SignRequestWrapper(httpRequest);
//
////        ServletContext context = httpRequest.getServletContext();
////        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
////        // 获取 Service
////        INavMenuService navMenuService  = ctx.getBean(INavMenuService.class);
////        List<Menu> list = navMenuService.list();
////        List<String> menuPath = new ArrayList<>(16);
////        for (Menu menu : list) {
////            menuPath.add((menu.getAttribute() == 3 || menu.getAttribute() == 4) ? menu.getLinkUrl():menu.getPath());
////        }
//
//        String[] urls = {".js,.gif,.jpg,.jpeg,.png,.css,.scss,.pdf,.ico,.eot,.ttf,.woff,.woff2,.svg"};
//
//        boolean flag = true;
//        for (String str : urls) {
//            String requestURI = httpRequest.getRequestURI();
//            if (requestURI.contains(str)) {
//                flag =false;
//                break;
//            }
//        }
//
//        if ("GET".equals(httpRequest.getMethod())) {
//            filterChain.doFilter(requestWrapper, servletResponse);
//            return;
//        }
//
//        if (flag) {
//            //构建请求头
//            RequestHeader requestHeader = RequestHeader.builder()
//                    .nonce(httpRequest.getHeader("X-Nonce"))
//                    .timestamp(Long.parseLong(httpRequest.getHeader("X-Time")))
//                    .sign(httpRequest.getHeader("X-Sign"))
//                    .build();
//
//            //验证请求头是否存在
//            if(StringUtils.isEmpty(requestHeader.getSign()) || ObjectUtils.isEmpty(requestHeader.getTimestamp()) || StringUtils.isEmpty(requestHeader.getNonce())){
//                log.error("过滤URL:{}", httpRequest.getRequestURI());
//                responseFail(httpResponse, ResultCode.ILLEGAL_HEADER);
//                return;
//            }
//
//            /*
//             * 1.重放验证
//             * 判断timestamp时间戳与当前时间是否超过60s（过期时间根据业务情况设置）,如果超过了就提示签名过期。
//             */
//            long now = System.currentTimeMillis() / 1000;
//
//            if (now - requestHeader.getTimestamp() > signMaxTime) {
//                log.error("过滤URL:{}", httpRequest.getRequestURI());
//                responseFail(httpResponse, ResultCode.REPLAY_ERROR);
//                return;
//            }
//
//            //2. 判断nonce
//            boolean nonceExists = redisUtil.hasKey(NONCE_KEY + requestHeader.getNonce());
//            if(nonceExists){
//                //请求重复
//                log.error("过滤URL:{}", httpRequest.getRequestURI());
//                responseFail(httpResponse,ResultCode.REPLAY_ERROR);
//                return;
//            }else {
//                redisUtil.set(NONCE_KEY+requestHeader.getNonce(), requestHeader.getNonce(), signMaxTime);
//            }
//
//
//            boolean accept;
//            SortedMap<String, Object> paramMap;
//            switch (httpRequest.getMethod()){
////                case "GET":
////                    paramMap = HttpDataUtil.getUrlParams(requestWrapper);
////                    accept = SignUtil.verifySign(paramMap, requestHeader);
////                    break;
//                case "POST":
//                case "PUT":
//                case "DELETE":
//                    paramMap = HttpDataUtil.getBodyParams(requestWrapper);
//                    accept = SignUtil.verifySign(paramMap, requestHeader);
//                    break;
//                default:
//                    accept = true;
//                    break;
//            }
//            if (accept) {
//                filterChain.doFilter(requestWrapper, servletResponse);
//            } else {
//                log.error("过滤URL:{}", httpRequest.getRequestURI());
//                responseFail(httpResponse,ResultCode.PARAMETER_SIGN_ERROR);
//            }
//        }
//    }
//
//    private void responseFail(HttpServletResponse httpResponse, ResultCode returnCode)  {
////        ResultCode<Object> resultData = ResultCode.fail(returnCode.code(), returnCode.message());
//        WebUtils.writeJson(httpResponse,returnCode);
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        String signTime = filterConfig.getInitParameter("signMaxTime");
//        signMaxTime = Long.parseLong(signTime);
//    }
//}