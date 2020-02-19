package com.wangwi.miaosha.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: wangwei
 * @Time: 2020/2/11 周二 20:34
 * @Description: CommonReturnEntity
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonReturnEntity {
    
    // 表明某个请求处理结果 success / fail
    private String status;
    // 若 status 是success ,data 保存要返回的json数据
    // 若 status 是fail, data 保存 通用错误结果返回格式（错误码及错误信息）
    private Object data;
    
    // 如果只传数据，默认响应结果为 success
    public static CommonReturnEntity success(Object data){
        return new CommonReturnEntity("success", data);
    }

    public static CommonReturnEntity fail(Object data) {
        return new CommonReturnEntity("fail", data);
    }
    
}
