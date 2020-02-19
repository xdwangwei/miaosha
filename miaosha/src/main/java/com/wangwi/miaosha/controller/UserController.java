package com.wangwi.miaosha.controller;

import com.wangwi.miaosha.controller.viewobject.UserVO;
import com.wangwi.miaosha.entity.CommonReturnEntity;
import com.wangwi.miaosha.exception.BusinessException;
import com.wangwi.miaosha.exception.EmBusinessError;
import com.wangwi.miaosha.service.UserService;
import com.wangwi.miaosha.service.model.UserModel;
import com.wangwi.miaosha.utils.AliyunShortMsgUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

/**
 * @Author: wangwei
 * @Time: 2020/2/11 周二 18:56
 * @Description: UserController
 **/
@RestController
@RequestMapping("/user")
// 允许ajax跨域及session共享
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${aliyun.shortmsg.appcode}")
    private String appCode;

    @PostMapping("/register/otp/code")
    public CommonReturnEntity sendOptCode(@RequestParam("telephone") String telephone) throws BusinessException {
        // 生成验证码
        String randomCode = AliyunShortMsgUtils.randomCode();
        // 将验证码和用户手机号绑定,保存到redis, 5分钟有效
        try {
            stringRedisTemplate.opsForValue().set(telephone, randomCode, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessError.REDIS_KEY_SAVE_FAILED);
        }
        // 将验证码发送给用户手机
        AliyunShortMsgUtils.sendShortMessage(appCode, randomCode, telephone);
        // 将结果返回给前端
        return CommonReturnEntity.success(null);
    }
    
    @PostMapping("/register/save")
    public CommonReturnEntity save(@RequestParam("telephone") String telephone,
                                   @RequestParam("randomCode") String randomCode,
                                   @RequestParam("name") String name,
                                   @RequestParam("originalPassword") String originalPassword,
                                   @RequestParam("gender") Integer gender,
                                   @RequestParam("age") Integer age) throws BusinessException {
        // 比较手机号与验证码是否匹配
        String randomCodeFromRedis = "";
        try {
            // 从redis取值
            randomCodeFromRedis = stringRedisTemplate.opsForValue().get(telephone);
        } catch (Exception e) {
            throw new BusinessException(EmBusinessError.REDIS_KEY_RETRIEVE_FAILED);
        }
        // 比较
        if (!StringUtils.equals(randomCode, randomCodeFromRedis))
            throw new BusinessException(EmBusinessError.PARAM_INVALID, "验证码不匹配!");
        // 构造UserModel进行保存
        UserModel userModel = new UserModel();
        userModel.setTelephone(telephone);
        userModel.setAge(age);
        userModel.setGender(gender);
        userModel.setName(name);
        userModel.setRegisterMode("byphone");
        // 密码加密
        userModel.setEncrptPassword(bCryptPasswordEncoder.encode(originalPassword));
        // 执行保存
        userService.save(userModel);
        return CommonReturnEntity.success(null);
    }
    
    @PostMapping("/login")
    public CommonReturnEntity login(@RequestParam("telephone") String telephone,
                                    @RequestParam("originalPassword") String originalPassword,
                                    HttpSession session) throws BusinessException {
        // 入参校验
        if (StringUtils.isEmpty(telephone) || StringUtils.isEmpty(originalPassword))
            throw new BusinessException(EmBusinessError.PARAM_INVALID);
        // 登录流程
        UserModel userModel = userService.login(telephone, originalPassword);
        // 保存进session
        session.setAttribute("IS_LOGIN", true);
        session.setAttribute("LOGIN_USER", userModel);
        return CommonReturnEntity.success(null);
    }

    @GetMapping("/get/{userId}")
    public CommonReturnEntity get(@PathVariable(name = "userId") Integer userId) throws BusinessException {
        UserModel userModel = userService.getUserById(userId);
        // 用户不存在
        if (userModel == null)
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST); // 抛出预定义异常统一处理
        // 将核心领域模型转为前端需要的view object
        return CommonReturnEntity.success(convertUserVOFromModel(userModel));
    }

    // 将核心领域模型转为前端需要的view object
    private UserVO convertUserVOFromModel(UserModel userModel) {
        if (userModel == null)
            return null;
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }
}
