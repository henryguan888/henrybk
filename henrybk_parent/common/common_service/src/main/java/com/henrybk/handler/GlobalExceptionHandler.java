package com.henrybk.handler;

import com.henrybk.exception.BusinessException;
import com.henrybk.result.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;

/**
 * @description 全局异常处理
 * @author Henry
 * @since 2023-05-16
 */
@ControllerAdvice // 凡是标记@ControllerAdvice的类都表示全局异常处理类
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 参数校验的异常(post)
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R methodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getBindingResult().getFieldError();
        log.error("参数校验失败(POST):{}", fieldError.getDefaultMessage());
        return R.error().message(fieldError.getDefaultMessage());
    }

    /**
     * 参数校验的异常(get)
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    public R constraintViolationException(ConstraintViolationException e) {
        log.error("参数校验失败(GET):{}", e);
        return R.error().message(e.getMessage());
    }


    /**
     * 业务异常
     * @param e
     * @return
     */
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public R businessException(BusinessException e){
        log.error("业务异常:{}---------------",e);
        return R.error(e.getCode(),e.getMsg());
    }

    /**
     * 全局异常
     * @param e
     * @return
     * @throws Exception
     */
    @ExceptionHandler(Exception.class)
    public R handlerException(Exception e) {
        // 普通异常, 输出：500 + 异常信息
        log.error("全局异常:{}---------------", e);
        return R.error(50000, e.getMessage());

    }
}
