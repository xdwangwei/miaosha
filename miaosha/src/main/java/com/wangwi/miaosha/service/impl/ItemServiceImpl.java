package com.wangwi.miaosha.service.impl;

import com.wangwi.miaosha.entity.po.ItemPO;
import com.wangwi.miaosha.entity.po.ItemSalesPO;
import com.wangwi.miaosha.entity.po.ItemStockPO;
import com.wangwi.miaosha.exception.BusinessException;
import com.wangwi.miaosha.exception.EmBusinessError;
import com.wangwi.miaosha.mapper.ItemPOMapper;
import com.wangwi.miaosha.mapper.ItemSalesPOMapper;
import com.wangwi.miaosha.mapper.ItemStockPOMapper;
import com.wangwi.miaosha.service.ItemSalesService;
import com.wangwi.miaosha.service.ItemService;
import com.wangwi.miaosha.service.ItemStockService;
import com.wangwi.miaosha.service.PromoService;
import com.wangwi.miaosha.service.model.ItemModel;
import com.wangwi.miaosha.service.model.PromoModel;
import com.wangwi.miaosha.utils.MiaoShaConstant;
import com.wangwi.miaosha.validator.ValidationResult;
import com.wangwi.miaosha.validator.ValidatorImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: wangwei
 * @Time: 2020/2/13 周四 16:16
 * @Description: ItemServiceImpl
 **/
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemPOMapper itemPOMapper;

    @Autowired
    private ItemSalesService itemSalesService;

    @Autowired
    private ItemStockService itemStockService;
    
    @Autowired
    private PromoService promoService;

    @Autowired
    private ValidatorImpl validator;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public ItemModel saveItem(ItemModel itemModel) throws BusinessException {
        // 入参校验
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.PARAM_INVALID);
        }
        ValidationResult validationResult = validator.validate(itemModel);
        if (validationResult.isHasErrors())
            throw new BusinessException(EmBusinessError.PARAM_INVALID, validationResult.getValidationErrors());
        // itemModel --> itemPO
        ItemPO itemPO = convertItemPOFromModel(itemModel);
        // 数据库存入,获取到自增主键值
        itemPOMapper.insertSelective(itemPO);
        // 把主键赋值给itemModel
        itemModel.setId(itemPO.getId());
        // itemModel --> itemSalesPO
        // 数据库存入
        itemSalesService.save(convertItemSalesPOFromModel(itemModel));
        // itemModel --> itemStockPO
        // 数据库存入
        itemStockService.save(convertItemStockPOFromModel(itemModel));

        // 重新从数据库中获取一次商品信息，并返回
        return getItemById(itemPO.getId());
    }

    @Override
    public ItemModel getItemById(Integer id) throws BusinessException {
        // 获取到商品信息
        ItemPO itemPO = itemPOMapper.selectByPrimaryKey(id);
        if (itemPO == null)
            throw new BusinessException(EmBusinessError.PARAM_INVALID, "所选商品不存在");
        // 获取到销量信息
        ItemSalesPO itemSalesPO = itemSalesService.selectByItemId(id);
        // 获取到库存信息
        ItemStockPO itemStockPO = itemStockService.selectByItemId(id);
        // 构造领域模型对象
        ItemModel itemModel = convertItemModelFromPO(itemPO, itemSalesPO, itemStockPO);
        // 查询促销活动信息
        PromoModel promoModel = promoService.getPromoByItemId(id);
        if (promoModel != null && promoModel.getStatus() != MiaoShaConstant.PROMO_STATUS_HAS_ENDED) {
            // 存在促销活动,且此促销未结束
            itemModel.setPromoModel(promoModel);
        }
        return itemModel;
    }

    @Override
    public List<ItemModel> getItemList() throws BusinessException {
        List<ItemPO> itemPOList = itemPOMapper.itemList();
        // java8 stream流，类似于js的数组的map方法
/*        List<ItemModel> itemModels = itemPOList.stream().map(itemPO -> {
            // 对于itemPOList的每一个itemPO
            ItemSalesPO itemSalesPO = itemSalesService.selectByItemId(itemPO.getId());
            ItemStockPO itemStockPO = itemStockService.selectByItemId(itemPO.getId());
            // 都转换成一个itemModel,并返回出来
            return convertItemModelFromPO(itemPO, itemSalesPO, itemStockPO);
            // 最终又组合成一个List
        }).collect(Collectors.toList());*/
        List<ItemModel> itemModels = new ArrayList<>();
        for (ItemPO itemPO : itemPOList) {
            itemModels.add(convertItemModelFromPO(itemPO, 
                    itemSalesService.selectByItemId(itemPO.getId()), 
                    itemStockService.selectByItemId(itemPO.getId())));
        }
        return itemModels;
    }

    private ItemPO convertItemPOFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemPO itemPO = new ItemPO();
        BeanUtils.copyProperties(itemModel, itemPO);
        // BigDecimal --> double
        itemPO.setPrice(itemModel.getPrice().doubleValue());
        return itemPO;
    }

    private ItemSalesPO convertItemSalesPOFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemSalesPO itemSalesPO = new ItemSalesPO();
        itemSalesPO.setItemId(itemModel.getId());
        itemSalesPO.setSales(itemModel.getSales());
        return itemSalesPO;
    }

    private ItemStockPO convertItemStockPOFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemStockPO itemStockPO = new ItemStockPO();
        itemStockPO.setItemId(itemModel.getId());
        itemStockPO.setStock(itemModel.getStock());
        return itemStockPO;
    }

    private ItemModel convertItemModelFromPO(ItemPO itemPO, ItemSalesPO itemSalesPO, ItemStockPO itemStockPO) {
        ItemModel itemModel = new ItemModel();
        BeanUtils.copyProperties(itemPO, itemModel);
        // double --> BigDecimal
        itemModel.setPrice(BigDecimal.valueOf(itemPO.getPrice()));
        itemModel.setSales(itemSalesPO.getSales());
        itemModel.setStock(itemStockPO.getStock());
        return itemModel;
    }
}
