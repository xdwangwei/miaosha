package com.wangwi.miaosha.exception;


/**
 * @Author: wangwei
 * @Time: 2020/2/11 周二 20:53
 * @Description: 全局异常信息定义枚举
 **/
public enum EmBusinessError implements CommonError{
    
    // 9000开头的为通用异常
    UNKNOWN_ERROR(90001, "未知错误"),
    // 如某些信息填写不合格
    PARAM_INVALID(90002, "参数不合法"), 
    
    // 8000开头的为aliyun短信服务异常
    ALIYUN_SHORT_MESSAGE_SEND_FAILED(80001, "阿里云短信发送失败"),
    
    // 7000开头的为redis相关错误
    REDIS_KEY_SAVE_FAILED(70001, "redis键值存入失败"),
    REDIS_KEY_RETRIEVE_FAILED(70002, "redis获取键值失败"),
    
    
    // 1000开头的为 用户模块 相关异常
    USER_NOT_EXIST(10001,"用户不存在"),
    USER_ACCOUT_ALREADY_USE(10002, "此账号已被使用"),
    USER_LOGIN_FAILED(10003, "账号或密码错误"),
    USER_NOT_LOGIN(10004, "用户未登录"),
    
    
    // 2000开头为订单模块相关错误
    ITEM_STOCK_NOT_ENOUGH(20001, "所选商品库存不足")
    ;
    
    private Integer errorCode;
    
    private String errorMsg;

    EmBusinessError(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public Integer getErrorCode() {
        return errorCode;
    }

    @Override
    public String getErrorMsg() {
        return errorMsg;
    }

    @Override
    public CommonError setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }
}
