package com.neuedu.common;

import com.alipay.demo.trade.model.hb.Type;
import com.google.common.collect.Lists;
import com.neuedu.pojo.UserInfo;
import org.apache.catalina.User;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.omg.CORBA.UShortSeqHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Configuration
public class JsonUtils {

    @Bean
    public ObjectMapper objectMapper(){
        //将所有对象序列化为Json
        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonSerialize.Inclusion.ALWAYS);
        //取消默认timestamp格式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);
        //忽略空Bean
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);

        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        return objectMapper;
    }



//    public static void main(String[] args) {
//        UserInfo userInfo=new UserInfo();
//        userInfo.setEmail("231");
//        userInfo.setCreateTime(new Date());
//        //System.out.println(obj2StringPretty(userInfo));
//        String json=obj2String(userInfo);
//        UserInfo userInfo1=string2Obj(json,UserInfo.class);
//        //System.out.println(userInfo1);
//        UserInfo userInfo2=new UserInfo();
//        userInfo.setEmail("231e1e");
//        userInfo.setCreateTime(new Date());
//        List<UserInfo> userInfoList= Lists.newArrayList();
//        userInfoList.add(userInfo);
//        userInfoList.add(userInfo1);
//        userInfoList.add(userInfo2);
//        String s=obj2String(userInfoList);
//        List<UserInfo> userInfoList1=string2Obj(s, new TypeReference<List<UserInfo>>() {
//        });
//        List<UserInfo> userInfoList2=string2Obj(s,List.class,UserInfo.class);
//        System.out.println(userInfoList2);
//
//
//    }
}
