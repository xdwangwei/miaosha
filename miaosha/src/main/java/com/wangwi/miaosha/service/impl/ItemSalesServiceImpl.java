package com.wangwi.miaosha.service.impl;

import com.wangwi.miaosha.entity.po.ItemSalesPO;
import com.wangwi.miaosha.exception.BusinessException;
import com.wangwi.miaosha.exception.EmBusinessError;
import com.wangwi.miaosha.mapper.ItemSalesPOMapper;
import com.wangwi.miaosha.service.ItemSalesService;
import com.wangwi.miaosha.validator.ValidationResult;
import com.wangwi.miaosha.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author: wangwei
 * @Time: 2020/2/13 周四 22:10
 * @Description: ItemSalesServiceImpl
 **/
@Service
public class ItemSalesServiceImpl implements ItemSalesService {
    
    @Autowired
    private ItemSalesPOMapper itemSalesPOMapper;
    
    @Autowired
    private ValidatorImpl validator;
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void increaseSales(Integer itemId, Integer amount) {
        itemSalesPOMapper.increaseSales(itemId, amount);
    }

    @Override
    public ItemSalesPO selectByItemId(Integer itemId) throws BusinessException {
        ItemSalesPO itemSalesPO = itemSalesPOMapper.selectByItemId(itemId);
        if (itemSalesPO == null) {
            throw new BusinessException(EmBusinessError.PARAM_INVALID, "所选商品信息不存在");
        }
        return itemSalesPO;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public void save(ItemSalesPO record) throws BusinessException {
        // 入参校验
        if (record == null)
            throw new BusinessException(EmBusinessError.PARAM_INVALID);
        ValidationResult result = validator.validate(record);
        if (result.isHasErrors())
            throw new BusinessException(EmBusinessError.PARAM_INVALID, result.getValidationErrors());
        // 记录入库
        itemSalesPOMapper.insertSelective(record);
    }
}
