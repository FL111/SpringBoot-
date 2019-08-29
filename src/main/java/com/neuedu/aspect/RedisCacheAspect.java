package com.neuedu.aspect;

import com.neuedu.common.ObjectMapperApi;
import com.neuedu.common.ServerResponse;
import com.neuedu.pojo.Product;
import com.neuedu.redis.RedisApi;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Aspect
public class RedisCacheAspect {

    @Autowired
    RedisApi redisApi;
    @Autowired
    ObjectMapperApi objectMapperApi;

    //@Pointcut("execution(* com.neuedu.sevice.impl.ProductServiceImpl.findXXX(..))")
    public void pointcut(){

    }

    //@Around("pointcut()")
    public  Object arount (ProceedingJoinPoint joinPoint) throws Throwable {
        Object object=null;
        try {
            StringBuffer stringBuffer=new StringBuffer();
            String className=joinPoint.getTarget().getClass().getName();
            stringBuffer.append(className);
            String methodName=joinPoint.getSignature().getName();
            stringBuffer.append(methodName);
            Object[] objects=joinPoint.getArgs();
            if (objects!=null){
                for (Object arg:objects){
                    stringBuffer.append(arg);
                }
            }
            String result="";
            result=redisApi.get(stringBuffer.toString());
            if (result!=null&&!result.equals("")){
                System.out.println("读取的缓存");
                ServerResponse serverResponse=objectMapperApi.string2Obj(result,ServerResponse.class);
                return serverResponse;
            }
            object=joinPoint.proceed();
            if (object!=null){
                System.out.println("读取的数据库");
                String value=objectMapperApi.obj2String(object);
                redisApi.set(stringBuffer.toString(),value);
            }
            //System.out.println("返回值"+object);
        }catch (Throwable throwable){
            throwable.printStackTrace();
        }
        return object;
    }
}
