package com.wangwi.miaosha.service.impl;

import com.wangwi.miaosha.entity.po.OrderPO;
import com.wangwi.miaosha.entity.po.SequenceInfoPO;
import com.wangwi.miaosha.exception.BusinessException;
import com.wangwi.miaosha.exception.EmBusinessError;
import com.wangwi.miaosha.mapper.OrderPOMapper;
import com.wangwi.miaosha.mapper.PromoPOMapper;
import com.wangwi.miaosha.mapper.SequenceInfoPOMapper;
import com.wangwi.miaosha.service.*;
import com.wangwi.miaosha.service.model.ItemModel;
import com.wangwi.miaosha.service.model.OrderModel;
import com.wangwi.miaosha.service.model.PromoModel;
import com.wangwi.miaosha.service.model.UserModel;
import com.wangwi.miaosha.utils.MiaoShaConstant;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * @Author: wangwei
 * @Time: 2020/2/14 周五 10:17
 * @Description: OrderServiceImpl
 **/
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemStockService itemStockService;

    @Autowired
    private ItemSalesService itemSalesService;

    @Autowired
    private PromoService promoService;

    @Autowired
    private OrderPOMapper orderPOMapper;

    // @Autowired
    // private SequenceInfoPOMapper sequenceInfoPOMapper;

    @Autowired
    private SequenceService sequenceService;

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public OrderModel saveOrder(Integer itemId, Integer userId, Integer amount, Integer promoId) throws BusinessException {
        // 1. 校验商品状态（是否存在，上架状态，是否开卖），用户信息是否存在，下单数量是否超限
        ItemModel itemModel = itemService.getItemById(itemId);
        if (itemModel == null) {
            throw new BusinessException(EmBusinessError.PARAM_INVALID, "商品信息不存在");
        }
        // 校验下单用户
        UserModel userById = userService.getUserById(userId);
        if (userById == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        // 校验促销活动是否存在，是否属于这个商品，活动是否结束
        PromoModel promoModel = null;
        if (promoId != null) {
            promoModel = promoService.getPromoById(promoId);
            if (promoModel == null) {
                throw new BusinessException(EmBusinessError.PARAM_INVALID, "促销活动不存在");
            } else if (promoModel.getItemId() != itemId) {
                throw new BusinessException(EmBusinessError.PARAM_INVALID, "当前促销活动不属于此商品");
            } else if (promoModel.getStatus() == MiaoShaConstant.PROMO_STATUS_HAS_ENDED) {
                throw new BusinessException(EmBusinessError.PARAM_INVALID, "当前促销活动已结束");
            }
        }
        // 限制用户下单数量 0 -- 99
        if (amount < MiaoShaConstant.EACH_ORDER_MIN_COUNT || amount > MiaoShaConstant.EACH_ORDER_MAX_COUNT) {
            throw new BusinessException(EmBusinessError.PARAM_INVALID, "下单数量不合法");
        }
        // 2. 落单减库存，支付成功后销量增加，在此省略此过程，直接销量增加
        boolean res = itemStockService.decreaseStock(itemId, amount);
        if (!res)
            throw new BusinessException(EmBusinessError.ITEM_STOCK_NOT_ENOUGH);
        itemSalesService.increaseSales(itemId, amount);
        // 3. 订单入库
        // 构造OrderModel对象
        OrderModel orderModel = new OrderModel();
        orderModel.setOrderNo(generateOrderNo());
        orderModel.setUserId(userId);
        orderModel.setItemId(itemId);
        orderModel.setAmount(amount);
        // 设置promoId(数据库此字段有默认值)
        orderModel.setPromoId(promoId);
        // 能运行到这里说明促销活动校验通过
        if (promoModel != null) {
            orderModel.setItemPrice(promoModel.getPromoPrice());
        } else {
            // 没有促销活动就是平常价
            orderModel.setItemPrice(itemModel.getPrice());
        }
        orderModel.setOrderPrice(orderModel.getItemPrice().multiply(BigDecimal.valueOf(amount)));
        // orderModel --> orderPO
        OrderPO orderPO = convertOrderPOFromModel(orderModel);
        // 入库
        orderPOMapper.insertSelective(orderPO);
        // 返回orderModel
        return orderModel;
    }

    /**
     * 生成16位订单号
     * 年月日 8 位
     * 中间6位为递增序列(长度根据每天的订单数目自行调整)，不够6位加前导0
     * 后两位是分库信息，如可以根据下单人id对100取模，是同一人的订单保存在同一库
     * <p>
     * 从数据库表中获取下一个序列号，因为此步骤包含在上一个入库操作中，序列化一旦被取走数据库就更新
     * 即便下单出错事务回滚，这个序列化也不能再用了，因此获取序列化操作需要包含在一个新的事务中，
     * 不受外围事务的影响
     *
     * @return
     * AOP事务
     * @Transactional 加于private方法, 无效
     * @Transactional 加于未加入接口的public方法, 再通过普通接口方法调用, 无效
     * @Transactional 加于接口方法, 无论下面调用的是private或public方法, 都有效
     * @Transactional 加于接口方法后, 被本类普通接口方法直接调用, 无效
     * @Transactional 加于接口方法后, 被本类普通接口方法通过接口调用, 有效
     * @Transactional 加于接口方法后, 被它类的接口方法调用, 有效
     * @Transactional 加于接口方法后, 被它类的私有方法调用后, 有效
     * 总结: Transactional是否生效, 仅取决于是否加载于接口方法, 并且是否通过接口方法调用(而不是本类调用)
     *
     * https://www.cnblogs.com/milton/p/6046699.html
     *
     * 若想对private方法加事务，建议使用AspectJ
     */
/*    @Transactional(propagation = Propagation.REQUIRES_NEW)
    // 不影响运行，但事务控制无用
    private String generateOrderNo() {

        StringBuilder builder = new StringBuilder();
        // 年月日 8 位
        builder.append(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).replace("-", ""));
        // 中间6位

        // 获取订单表当前序列号
        SequenceInfoPO sequenceInfoPO = sequenceInfoPOMapper.getCurrentValueByName("order_sequence");
        Integer currentValue = sequenceInfoPO.getCurrentValue();
        // 更新序列号表当前序列值
        sequenceInfoPO.setCurrentValue(currentValue + sequenceInfoPO.getIncreaseStep());
        // 更新
        sequenceInfoPOMapper.updateByPrimaryKeySelective(sequenceInfoPO);

        // 拼凑中间六位
        String currentValueStr = String.valueOf(currentValue);
        // 不够六位流拼0
        for (int i = 0; i < 6 - currentValueStr.length(); ++i)
            builder.append("0");
        builder.append(currentValueStr);

        // 分库分表两位（只建了一张表，这里写死00确定到这张表）
        builder.append("00");
        return builder.toString();
    }*/

    // 改进版，单独创建service进行序列号查取与更新
    private String generateOrderNo() {

        StringBuilder builder = new StringBuilder();
        // 年月日 8 位
        builder.append(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE).replace("-", ""));
        // 中间6位

        // 获取订单表当前序列号并更新
        int currentValue = sequenceService.getCurrentValueAndUpdate("order_sequence");

        // 拼凑中间六位
        String currentValueStr = String.valueOf(currentValue);
        // 不够六位流拼0
        for (int i = 0; i < 6 - currentValueStr.length(); ++i)
            builder.append("0");
        builder.append(currentValueStr);

        // 分库分表两位（只建了一张表，这里写死00确定到这张表）
        builder.append("00");
        return builder.toString();
    }

    private OrderPO convertOrderPOFromModel(OrderModel orderModel) {
        if (orderModel == null)
            return null;
        OrderPO orderPO = new OrderPO();
        BeanUtils.copyProperties(orderModel, orderPO);
        // BigDecimal --> double
        orderPO.setItemPrice(orderModel.getItemPrice().doubleValue());
        orderPO.setOrderPrice(orderModel.getOrderPrice().doubleValue());
        return orderPO;
    }

}
