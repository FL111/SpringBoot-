package com.neuedu.config;

import com.neuedu.controller.UploadController;
import com.qiniu.common.Zone;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("application.yml")
@ConfigurationProperties(prefix = "qiniu")
public class QiniuConfig {

    @Value("${accessKey}")
    String accessKey;
    @Value("${secretKey}")
    String secretKey;
    @Value("${bucket}")
    String bucket;


    @Bean
    public com.qiniu.storage.Configuration configuration(){
        return new com.qiniu.storage.Configuration(Zone.zone1());
    }
    @Bean
    public UploadManager uploadManager(){
        return new UploadManager(configuration());
    }
    @Bean
    public Auth auth(){
        return Auth.create(accessKey,secretKey);
    }

}
