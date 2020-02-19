package com.wangwi.miaosha.service;

import com.wangwi.miaosha.exception.BusinessException;
import com.wangwi.miaosha.service.model.OrderModel;

/**
 * @Author: wangwei
 * @Time: 2020/2/14 周五 10:16
 * @Description: OrderService
 **/
public interface OrderService {
    
    OrderModel saveOrder(Integer itemId, Integer userId, Integer amount, Integer promoId) throws BusinessException;
}
