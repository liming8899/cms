package cloud.onion.cms.config;

import cloud.onion.core.exception.BizException;
import cloud.onion.core.result.ResultCode;
import cloud.onion.core.result.ResultJson;
import cn.dev33.satoken.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 允泽
 * @date 2022/7/25
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionConfig {

    /**
     * Exception全局异常信息
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception e) {
        log.error(e.getMessage());
        return "forward:/500.html";
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String  handleNotFoundException(NotFoundException e) {
        log.error(e.getMessage());
        return "forward:/error/404";
    }

    /**
     * 空指针异常
     * @param e
     * @return
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResultJson handlerNullPointerException(NullPointerException e) {
        log.error("空指针异常：{}",e.getStackTrace());
        return ResultJson.fail(ResultCode.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    /**
     * 404错误
     * @param e
     * @return
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ResultJson handleNoHandlerFoundException(NoHandlerFoundException e) {
        return ResultJson.fail(ResultCode.NOT_FOUND);
    }

    /**
     * 处理自定义全局异常
     * @param e
     * @return
     */
    @ExceptionHandler(BizException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultJson handleBasicException(BizException e){
        return ResultJson.fail(e.getCode(), e.getMessage());
    }


    /**
     * 没有登陆或Token错误
     * @param e
     * @return
     */
    @ExceptionHandler(NotLoginException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultJson handleNotLoginException(NotLoginException e){
        return ResultJson.fail(ResultCode.UNAUTHORIZED.code(), e.getMessage());
    }

    /**
     * 请求参数JSON反序列化错误
     * @param e
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultJson handleJsonParseException(HttpMessageNotReadableException e){
        return ResultJson.fail(ResultCode.PARAMETER_ERROR);
    }

    /**
     * 请求参数校验错误
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultJson handleValidException(MethodArgumentNotValidException e){
        List<String> errorInfo = e.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        return ResultJson.fail(ResultCode.PARAMETER_REQUIRED.code(), errorInfo.toString());
    }

    /**
     * 请求url参数校验错误
     * @param e
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultJson handleConstraintViolationException(ConstraintViolationException e){
        String message = null;
        if (StringUtils.isNoneBlank(e.getMessage())) {
            String[] split = e.getMessage().split(":");
            if (split.length > 1) {
                message = split[1].trim();
            }
        }
        return ResultJson.fail(ResultCode.PARAMETER_REQUIRED.code(), message);
    }

    /**
     * 绑定的参数错误
     * @param e
     * @return
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultJson handleValidException(BindException e){
        return ResultJson.fail(ResultCode.PARAMETER_ERROR);
    }

    /**
     * 请求方法参数类型不匹配
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultJson handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResultJson.fail(ResultCode.PARAMETER_TYPE_ERROR);
    }


    /**
     * 请求方式错误
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultJson handleHttpRequestMethodNotSupportedException(Exception e) {
        return ResultJson.fail(ResultCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 上传超过最大容量
     * @param e
     * @return
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResultJson handleMaxUploadSizeExceededException(Exception e) {
        return ResultJson.fail(ResultCode.UPLOAD_FILE_EXCEEDS_MAXIMUM);
    }
}
