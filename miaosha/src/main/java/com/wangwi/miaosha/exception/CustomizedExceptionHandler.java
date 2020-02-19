package com.wangwi.miaosha.exception;

import com.wangwi.miaosha.entity.CommonReturnEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangwei
 * @Time: 2020/2/11 周二 21:07
 * @Description: ExceptionHandler
 **/
@ControllerAdvice
public class CustomizedExceptionHandler {

    /**
     * 捕捉异常，并将其构造成 status + data json格式，使 成功/失败，返回格式一致，前端便于解析
     * 成功是 { status:   , data:{   }  }
     * 失败是 { status: fail, data: {errorCode:  , errMsg:   }}
     * @param ex
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public CommonReturnEntity handler(Exception ex){
        Map<String, Object> returnData = new HashMap<>();
        // 自己抛出的都是 BusinessException,
        if (ex instanceof BusinessException){
            // 强转
            BusinessException businessException = (BusinessException)ex;
            returnData.put("errorCode", businessException.getErrorCode());
            returnData.put("errorMsg", businessException.getErrorMsg());
        // 发生了其他错误
        }else {
            ex.printStackTrace();
            // 使，不管是预定义错误，还是运行异常，最终返回结构一致
            returnData.put("errorCode", EmBusinessError.UNKNOWN_ERROR.getErrorCode());
            returnData.put("errorMsg", EmBusinessError.UNKNOWN_ERROR.getErrorMsg());
        }
        return CommonReturnEntity.fail(returnData);
    }
    
    // @ExceptionHandler(value = Exception.class)
    // @ResponseBody
    // public CommonReturnEntity handler2(Exception ex){
    //     if (ex instanceof BusinessException){
    //         Map<String, Object> returnData = new HashMap<>();
    //         BusinessException businessException = (BusinessException)ex;
    //         returnData.put("errorCode", businessException.getErrorCode());
    //         returnData.put("errorMsg", businessException.getErrorMsg());
    //         return CommonReturnEntity.fail(returnData);
    //     }else {
    //         // 这种方式，EmBusinessError.UNKNOWN_ERROR 虽然有两个属性字段，但是序列化会有问题
    //         // 返回前端的json是，
    //         // {
    //         //      status: "fail",
    //         //      data: "UNKNOWN_ERROR" // 只序列化出其errorMsg属性
    //         // }
    //         return CommonReturnEntity.fail(EmBusinessError.UNKNOWN_ERROR);
    //     }
    // }
}
