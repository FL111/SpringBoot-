package com.neuedu.redis;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Data
@Configuration
@Component
@PropertySource("application.yml")
public class RedisProperties {
    @Value("${spring.redis.max.total}")
    private int maxTotal;
    @Value("${spring.redis.max.idle}")
    private int maxIdle;
    @Value("${spring.redis.min.idle}")
    private int minIdle;
    @Value("${spring.redis.host}")
    private String redisIp;
    @Value("${spring.redis.port}")
    private int redisPort;
    @Value("${spring.redis.test.borrow}")
    private boolean testBorrow;
    @Value("${spring.redis.test.return}")
    private boolean testReturn;
    @Value("${spring.redis.password}")
    private String redisPassword;



}
