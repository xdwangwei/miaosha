package com.wangwi.miaosha.service.impl;

import com.wangwi.miaosha.entity.po.SequenceInfoPO;
import com.wangwi.miaosha.mapper.SequenceInfoPOMapper;
import com.wangwi.miaosha.service.SequenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author: wangwei
 * @Date: 2020/2/19 18:30
 * @Description: 序列号相关服务
 */
@Service
public class SequenceServiceImpl implements SequenceService {

    @Autowired
    private SequenceInfoPOMapper sequenceInfoPOMapper;

    /**
     * 为确保唯一性，此操作一般单独事务
     *
     * @param sequcenceName
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public int getCurrentValueAndUpdate(String sequcenceName) {
        // 获取 指定模块 当前序列号
        SequenceInfoPO sequenceInfoPO = sequenceInfoPOMapper.getCurrentValueByName(sequcenceName);
        Integer currentValue = sequenceInfoPO.getCurrentValue();
        // 更新序列号表当前序列值
        sequenceInfoPO.setCurrentValue(currentValue + sequenceInfoPO.getIncreaseStep());
        // 判断更新后是否达到最大值
        if (sequenceInfoPO.getCurrentValue() > sequenceInfoPO.getMaxValue()) {
            // 回到初值
            sequenceInfoPO.setCurrentValue(sequenceInfoPO.getInitValue());
        }
        // 更新
        sequenceInfoPOMapper.updateByPrimaryKeySelective(sequenceInfoPO);
        // 返回
        return currentValue;
    }
}