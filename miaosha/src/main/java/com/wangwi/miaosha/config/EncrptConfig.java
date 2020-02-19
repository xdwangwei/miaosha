package com.wangwi.miaosha.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: wangwei
 * @Time: 2020/2/12 周三 19:27
 * @Description: BCryptPasswordEncoder使用SHA-256+随机盐+密钥把用户输入的密码进行hash处理，
 *              得到密码的hash值,不可逆,盐值不同,hash解雇不同
 *              使用SecureRandom生成的强（安全）随机数作为盐进行加密，不需要我们去记录这个盐，因为它会直接混在加密后的密码串中
 **/
@Configuration
public class EncrptConfig {
    
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
