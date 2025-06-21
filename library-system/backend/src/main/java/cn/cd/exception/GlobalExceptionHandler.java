package cn.cd.exception;

import cn.cd.util.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//对加了@RestController的类【接口类】的增强 - 全局异常处理
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //当前方法用来处理业务异常
    @ExceptionHandler(BusinessException.class)
    public AjaxResult handleBusinessException(BusinessException e){
        return AjaxResult.me()
                .setSuccess(false)
                .setMessage(e.getMessage());
    }

    //当前方法用来处理其他异常
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e){
        e.printStackTrace();
        //log.error("系统异常:", e); // 建议添加日志记录
        return AjaxResult.me()
                .setSuccess(false)
                .setMessage("系统繁忙，请稍后重试");
    }

}
