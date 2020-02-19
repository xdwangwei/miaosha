package com.wangwi.miaosha.exception;

/**
 * @Author: wangwei
 * @Time: 2020/2/11 周二 21:00
 * @Description: 全局业务异常统一处理
 **/
public class BusinessException extends Exception implements CommonError{
    
    // 用于根据传过来的枚举异常构造业务异常
    private CommonError commonError;
    
    public BusinessException(CommonError commonError){
        this.commonError = commonError;
    }

    // 用于根据传过来的枚举异常构造业务异常，并改变异常信息
    // 比如 90000 未知异常，90001 参数不合法 都是通用异常，可以保证状态码正常，错误信息修改
    public BusinessException(CommonError commonError, String errorMsg){
        this.commonError = commonError;
        commonError.setErrorMsg(errorMsg);
    }
    
    @Override
    public Integer getErrorCode() {
        return commonError.getErrorCode();
    }

    @Override
    public String getErrorMsg() {
        return commonError.getErrorMsg();
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        commonError.setErrorMsg(errorMsg);
        return commonError;
    }
}
