package com.wugenshui.security.exception;

import cn.hutool.core.util.StrUtil;
import com.wugenshui.security.entity.AjaxResult;
import com.wugenshui.security.enums.AjaxResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 *
 * @author : chenbo
 * @date : 2021-02-12
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理参数校验异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public AjaxResult handlerMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        FieldError error = ex.getBindingResult().getFieldError();
        assert error != null;
        return AjaxResult.error(error.getDefaultMessage());
    }

    /**
     * 处理参数校验异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException ex) {
        FieldError error = ex.getBindingResult().getFieldError();
        assert error != null;
        return AjaxResult.error(error.getDefaultMessage());
    }

    /**
     * 处理其它未处理异常
     *
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public AjaxResult handleOtherException(Exception ex) {
        log.error("系统全局异常", ex);
        if (log.isInfoEnabled()) {
            return AjaxResult.error(ex.getMessage());
        }
        return AjaxResult.error(AjaxResultEnum.ERROR.getMsg());
    }
}
