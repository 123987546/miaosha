package com.miaosha.day1.exception;

import com.miaosha.day1.result.CodeMsg;
import com.miaosha.day1.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobleExceptionHandler {
    /**
     * 全局异常处理器
     * @param request
     * @param e
     * @return
     */
    @ExceptionHandler(value=Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request,Exception e){
        e.printStackTrace();
        if(e instanceof GlobalException) {
            GlobalException ex = (GlobalException)e;
            return Result.error(ex.getCm());
        }else if(e instanceof BindException) {
            BindException ex = (BindException)e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROR.fillArgs(msg));
        }else {
            return Result.error(CodeMsg.SERVER_ERROR);
        }
    }
}

