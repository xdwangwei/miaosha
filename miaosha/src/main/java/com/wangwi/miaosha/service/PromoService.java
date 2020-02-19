package com.wangwi.miaosha.service;

import com.wangwi.miaosha.service.model.PromoModel;

/**
 * @Author: wangwei
 * @Time: 2020/2/15 周六 13:19
 * @Description: PromoService
 **/
public interface PromoService {

    /**
     * 查出指定商品的促销活动
     * @param itemId
     * @return
     */
    PromoModel getPromoByItemId(Integer itemId);

    /**
     * 查出指定促销活动
     * @param id
     * @return
     */
    PromoModel getPromoById(Integer id);
}
