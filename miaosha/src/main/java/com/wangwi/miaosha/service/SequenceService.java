package com.wangwi.miaosha.service;

/**
 * @Author: wangwei
 * @Date: 2020/2/19 18:30
 * @Description: 序列号相关服务
 */
public interface SequenceService {
    /**
     * 查出指定模块当前序列号，并更新字段为下一序列值
     * @param sequcenceName
     * @return
     */
    int getCurrentValueAndUpdate(String sequcenceName);
}
