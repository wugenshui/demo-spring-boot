package com.github.wugenshui.best.practice.single.config;

import com.github.wugenshui.best.practice.single.entity.AjaxResult;
import com.github.wugenshui.best.practice.single.enums.AjaxResultEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
        return AjaxResult.error(StringUtils.defaultIfBlank(error.getDefaultMessage(), "参数校验异常！"));
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
        return AjaxResult.error(StringUtils.defaultIfBlank(error.getDefaultMessage(), "参数校验异常！"));
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
