package com.wangwi.miaosha.exception;

/**
 * @Author: wangwei
 * @Time: 2020/2/11 周二 20:50
 * @Description: 通用异常接口规范，统一处理异常信息
 **/
public interface CommonError {
    
    Integer getErrorCode();
    
    String getErrorMsg();
    
    // 错误码不变的前提下，修改异常信息
    CommonError setErrorMsg(String errorMsg);
    
}
