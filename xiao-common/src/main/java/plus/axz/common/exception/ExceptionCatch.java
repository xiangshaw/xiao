package plus.axz.common.exception;

import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import plus.axz.model.common.dtos.ResponseResult;
import plus.axz.model.common.enums.ResultEnum;

/**
 * @author xiaoxiang
 * description 抓捕全局异常
 */
@ControllerAdvice//控制器增强
@Log4j2//记录日志信息
public class ExceptionCatch {
    /*指定要拦截的类型注解,这里指定拦截所有错误，都需经过此处*/
    @ExceptionHandler(Exception.class)
    /*返回JSON数据*/
    @ResponseBody
    /*Exception接收错误信息*/
    public ResponseResult<?> exception(Exception e){
        /*打印异常信息*/
        log.error("catch exception:{}",e.getMessage());
        /*返回通用的异常信息*/
        return ResponseResult.errorResult(ResultEnum.SERVER_ERROR);
    }
}
