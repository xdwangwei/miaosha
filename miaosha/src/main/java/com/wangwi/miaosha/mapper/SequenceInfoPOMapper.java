package com.wangwi.miaosha.mapper;

import com.wangwi.miaosha.entity.po.SequenceInfoPO;

public interface SequenceInfoPOMapper {
    int deleteByPrimaryKey(String sequenceName);

    int insert(SequenceInfoPO record);

    int insertSelective(SequenceInfoPO record);

    /**
     * 获取指定序列信息
     * @param sequenceName
     * @return
     * 使用select…for update会把数据给锁住，不过我们需要注意一些锁的级别，
     * MySQL InnoDB默认Row-Level Lock，所以只有「明确」地指定主键，MySQL 才会执行Row lock (只锁住被选取的数据) ，
     * 否则MySQL 将会执行Table Lock (将整个数据表单给锁住)
     * 原文链接：https://blog.csdn.net/HEYUTAO007/article/details/51729456
     */
    SequenceInfoPO getCurrentValueByName(String sequenceName);

    SequenceInfoPO selectByPrimaryKey(String sequenceName);

    int updateByPrimaryKeySelective(SequenceInfoPO record);

    int updateByPrimaryKey(SequenceInfoPO record);
}