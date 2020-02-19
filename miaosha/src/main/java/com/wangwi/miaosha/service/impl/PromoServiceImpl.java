package com.wangwi.miaosha.service.impl;

import com.wangwi.miaosha.entity.po.PromoPO;
import com.wangwi.miaosha.mapper.PromoPOMapper;
import com.wangwi.miaosha.service.PromoService;
import com.wangwi.miaosha.service.model.PromoModel;
import com.wangwi.miaosha.utils.MiaoShaConstant;
import org.joda.time.DateTime;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Author: wangwei
 * @Time: 2020/2/15 周六 13:21
 * @Description: PromoServiceImpl
 **/
@Service
public class PromoServiceImpl implements PromoService {
    
    @Autowired
    private PromoPOMapper promoPOMapper;
    
    @Override
    public PromoModel getPromoByItemId(Integer itemId) {
        // po --> model,返回null说明当前商品没有促销活动
        return convertPromoModelFromPO(promoPOMapper.selectByItemId(itemId));
    }

    @Override
    public PromoModel getPromoById(Integer id) {
        return convertPromoModelFromPO(promoPOMapper.selectByPrimaryKey(id));
    }

    private PromoModel convertPromoModelFromPO(PromoPO promoPO){
        if (promoPO == null) {
            return null;
        }
        PromoModel promoModel = new PromoModel();
        BeanUtils.copyProperties(promoPO, promoModel);
        // double --> BigDecimal
        promoModel.setPromoPrice(BigDecimal.valueOf(promoPO.getPromoPrice()));
        // Date --> DateTime
        promoModel.setStartTime(new DateTime(promoPO.getStartTime()));
        promoModel.setEndTime(new DateTime(promoPO.getEndTime()));
        // 设置秒杀活动状态
        DateTime now = new DateTime();
        // 假设秒杀活动存入数据库都满足结束时间 > 开始时间
        if (promoModel.getStartTime().isAfter(now)){
            promoModel.setStatus(MiaoShaConstant.PROMO_STATUS_NOT_START);
        }else if (promoModel.getEndTime().isBefore(now)){
            promoModel.setStatus(MiaoShaConstant.PROMO_STATUS_HAS_ENDED);
        }else {
            promoModel.setStatus(MiaoShaConstant.PROMO_STATUS_ON_GOING);
        }
        return promoModel;
    }
}
