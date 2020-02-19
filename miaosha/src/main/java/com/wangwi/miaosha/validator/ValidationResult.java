package com.wangwi.miaosha.validator;

import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wangwei
 * @Time: 2020/2/13 周四 13:28
 * @Description: 此对象用于保存校验结果
 **/
@Data
public class ValidationResult {
    
    // 默认值为false
    private boolean hasErrors;
    
    private Map<String, String> errorMsgMap = new HashMap<>();

    /**
     * 将所有校验结果错误信息（","分割）组合成字符串
     * @return
     */
    public String getValidationErrors(){
        return StringUtils.join(getErrorMsgMap().values().toArray(), ",");
    }
    
}
