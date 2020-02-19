package com.wangwi.miaosha.controller;

import com.wangwi.miaosha.controller.viewobject.ItemVO;
import com.wangwi.miaosha.entity.CommonReturnEntity;
import com.wangwi.miaosha.exception.BusinessException;
import com.wangwi.miaosha.service.ItemService;
import com.wangwi.miaosha.service.model.ItemModel;
import com.wangwi.miaosha.utils.MiaoShaConstant;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: wangwei
 * @Time: 2020/2/13 周四 16:13
 * @Description: ItemController
 **/
@RestController
@RequestMapping("/item")
// 允许ajax跨域及session共享
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // 创建/添加商品
    @PostMapping("/create")
    public CommonReturnEntity create(@RequestParam("title") String title,
                                     @RequestParam("description") String description,
                                     @RequestParam("imgUrl") String imgUrl,
                                     @RequestParam("price") BigDecimal price,
                                     @RequestParam("stock") Integer stock) throws BusinessException {
        // 构造itemModel对象
        ItemModel itemModel = new ItemModel();
        itemModel.setTitle(title);
        itemModel.setDescription(description);
        itemModel.setPrice(price);
        itemModel.setImgUrl(imgUrl);
        itemModel.setStock(stock);
        // 业务层逻辑处理
        ItemModel item = itemService.saveItem(itemModel);
        // 将得到的ItemModel转为ItemVO返回给前端
        return CommonReturnEntity.success(convertItemVOFromModel(item));
    }
    
    // 获取商品列表
    @GetMapping("/list")
    public CommonReturnEntity getItemList() throws BusinessException {
        List<ItemModel> itemModelList = itemService.getItemList();
        // 将itemModelList转为itemVOList,借助java8,stream
        // 对于每个itemModel都转换成itemVO返回出来，组合成一个新的list，如果只有一句话可以简写并省略return
        List<Object> itemVOList = itemModelList.stream().map(itemModel -> convertItemVOFromModel(itemModel)).collect(Collectors.toList());
        // 返回itemVOList
        return CommonReturnEntity.success(itemVOList);
    }
    
    // 浏览某个商品信息
    @GetMapping("/get/{itemId}")
    public CommonReturnEntity getItem(@PathVariable("itemId") Integer itemId) throws BusinessException {
        return CommonReturnEntity.success(convertItemVOFromModel(itemService.getItemById(itemId)));
    }
    
    private ItemVO convertItemVOFromModel(ItemModel itemModel) {
        if (itemModel == null) {
            return null;
        }
        ItemVO itemVO = new ItemVO();
        BeanUtils.copyProperties(itemModel, itemVO);
        // 若有促销活动（未开始/正在进行），则itemVO补充这些信息
        if (itemModel.getPromoModel() != null){
            itemVO.setPromoId(itemModel.getPromoModel().getId());
            itemVO.setPromoStatus(itemModel.getPromoModel().getStatus());
            itemVO.setPromoPrice(itemModel.getPromoModel().getPromoPrice());
            itemVO.setPromoStartTime(itemModel.getPromoModel().getStartTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
            itemVO.setPromoEndTime(itemModel.getPromoModel().getEndTime().toString(DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")));
        }else 
            itemVO.setPromoStatus(MiaoShaConstant.PROMO_STATUS_NOT_EXIST);
        return itemVO;
    }
}
