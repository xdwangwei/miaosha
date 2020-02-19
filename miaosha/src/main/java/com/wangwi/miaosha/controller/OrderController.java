package com.wangwi.miaosha.controller;

import com.wangwi.miaosha.entity.CommonReturnEntity;
import com.wangwi.miaosha.exception.BusinessException;
import com.wangwi.miaosha.exception.EmBusinessError;
import com.wangwi.miaosha.service.OrderService;
import com.wangwi.miaosha.service.model.OrderModel;
import com.wangwi.miaosha.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @Author: wangwei
 * @Time: 2020/2/14 周五 15:48
 * @Description: OrderController
 **/
@RestController
@RequestMapping("/order")
// 允许ajax跨域及session共享
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController {
    
    @Autowired
    private OrderService orderService;
    
    @PostMapping("/create")
    public CommonReturnEntity createOrder(@RequestParam("itemId") Integer itemId,
                                          @RequestParam(value = "amount", defaultValue = "1") Integer amount,
                                          @RequestParam(value = "promoId", required = false) Integer promoId,
                                          HttpSession session) throws BusinessException {
        
        // 判断用户是否登录
        Boolean isLogin = (Boolean) session.getAttribute("IS_LOGIN");
        if (isLogin == null || !isLogin.booleanValue())
            throw new BusinessException(EmBusinessError.USER_NOT_LOGIN);
        // 从session中获取用户登录信息
        UserModel userModel = (UserModel) session.getAttribute("LOGIN_USER");
        
        // 业务处理
        OrderModel orderModel = orderService.saveOrder(itemId, userModel.getId(), amount, promoId);
        
        // 返回
        return CommonReturnEntity.success(userModel);
    }
}
