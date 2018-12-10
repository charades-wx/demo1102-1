package com.qf.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 生成验证码(google提供的方法)
 */
@Configuration
public class KaptchaConfig {

    @Bean(name="defaultKaptcha")
    public DefaultKaptcha defaultKaptcha(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.put("kaptcha.border","no");//边框
        properties.put("kaptcha.textproducer.font.color","black");//颜色
        properties.put("kaptcha.textproducer.char.length","2");//个数
        Config config = new Config(properties);

        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
