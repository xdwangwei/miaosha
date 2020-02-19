package com.wangwi.miaosha.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @Author: wangwei
 * @Time: 2020/2/13 周四 13:38
 * @Description: 在javax原生校验器基础上，封装一个自定义校验器，并借助自定义校验结果对象封装校验结果
 **/
@Component
public class ValidatorImpl implements InitializingBean {

    Validator validator;
    
    public ValidationResult validate(Object o){
        // 构造ValidationResult对象用于封装校验结果
        ValidationResult validationResult = new ValidationResult();
        // 校验器校验
        Set<ConstraintViolation<Object>> validateResultSet = validator.validate(o);
        // 某属性字段出错
        if (validateResultSet.size() > 0){
            validationResult.setHasErrors(true);
            for (ConstraintViolation<Object> constraintViolation : validateResultSet) {
                // 出错属性的名字
                String errorProperty = constraintViolation.getPropertyPath().toString();
                // 出错信息
                String errorMsg = constraintViolation.getMessage();
                // 此属性校验结果加入校验结果集
                validationResult.getErrorMsgMap().put(errorProperty, errorMsg);
            }
        }
        // 返回校验结果
        return validationResult;
    }

    /**
     * 可以通过写配置类，在容器中创建一个javax.validation.Validator对象，然后对于奔雷validator属性进行自动注入
     * 也可以通过实现InitializingBean，在ioc容器bean创建完后回调用afterPropertiesSet()方法，在此方法中中validator属性进行赋值
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 工厂构造方式，借助hibernate的validator构造出javax的validator(实现完整java的校验标准)
       validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
