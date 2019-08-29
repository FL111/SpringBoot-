package com.neuedu.common;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ObjectMapperApi {
    @Autowired
    ObjectMapper objectMapper;
    public  <T> String obj2String(T obj){
        if (obj==null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  <T> String obj2StringPretty(T obj){
        if (obj==null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符串转对象
     * */
    public  <T> T string2Obj(String str,Class<T> clazz){
        if (StringUtils.isEmpty(str)||clazz==null){
            return null;
        }
        try {
            return clazz.equals(String.class)? (T)str : objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *将json数组转集合
     * */
    public  <T> T string2Obj(String str, TypeReference<T> tTypeReference) {
        if (StringUtils.isEmpty(str)||tTypeReference==null){
            return null;
        }
        try {
            return tTypeReference.getType().equals(String.class)?(T)str:objectMapper.readValue(str,tTypeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public  <T>T string2Obj(String str,Class<?> collectionClass,Class<?>... elements){
        JavaType javaType=objectMapper.getTypeFactory().constructParametricType(collectionClass,elements);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
