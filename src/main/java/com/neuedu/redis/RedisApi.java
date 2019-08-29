package com.neuedu.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class RedisApi {
    @Autowired
    private JedisPool jedisPool;

    public String set(String key,String value){
        String result="";
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            result=jedis.set(key,value);
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
    //存在过期时间
    public String setex(String key,int second,String value){
        String result="";
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            result=jedis.setex(key,second,value);
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }catch (Exception e){
            jedisPool.returnBrokenResource(jedis);
        }
        return result;
    }
    public String get(String key){
        String result="";
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            result=jedis.get(key);
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }catch (Exception e){
            if (jedis!=null){
                jedisPool.returnBrokenResource(jedis);
            }
        }
        return result;
    }
    public Long del(String key){
        Long result=null;
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            result=jedis.del(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeJedis(jedis);
        }
        return result;
    }
    public Long set(String key,int second){
        Long result=null;
        Jedis jedis=null;
        try {
            jedis=jedisPool.getResource();
            result=jedis.expire(key,second);
            if (jedis!=null){
                jedisPool.returnResource(jedis);
            }
        }catch (Exception e){
            jedisPool.returnBrokenResource(jedis);
        }
        return result;
    }
    public void closeJedis( Jedis jedis ) {
        try {
            if ( jedis != null ) {
                if ( jedis != null ) {
                    jedis.close();
                }
            }
        } catch (Exception e) {
            closeBrokenResource(jedis);
        }
    }
    public void closeBrokenResource( Jedis jedis ) {
        try {
            jedisPool.returnBrokenResource( jedis );
        } catch ( Exception e ) {
            destroyJedis( jedis );
        }
    }
    public static void destroyJedis( Jedis jedis ) {
        if ( jedis != null ) {
            try {
                jedis.quit();
            } catch ( Exception e ) {
               e.printStackTrace();
            }
            try {
                jedis.disconnect();
            } catch ( Exception e ) {
                e.printStackTrace();
            }
        }
    }
}
