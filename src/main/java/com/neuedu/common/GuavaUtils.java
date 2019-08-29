package com.neuedu.common;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class GuavaUtils {
    private static final LoadingCache<String,String> loadingCache= CacheBuilder.
            newBuilder()
            .initialCapacity(1000)//初始缓存项
            .maximumSize(10000) //缓存最大值
            .expireAfterAccess(10, TimeUnit.MINUTES) //缓存项在给定时间内没有被读/写，则回收
            .build(new CacheLoader<String, String>() {
        @Override
        public String load(String key) throws Exception {
            return "null";
        }
    });

    public static void put(String key,String value){
        loadingCache.put(key,value);
    }
    public static  String  get(String key){
        try {
            String result=loadingCache.get(key);
            if (!result.equals("null")){
                return result;
            }else {
                return "null";
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {

        long time=new Date().getTime();
        System.out.println(""+time);
        Date date=new Date(time);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(sdf.format(date));
    }
}
