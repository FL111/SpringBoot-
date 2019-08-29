package com.neuedu.controller;

import com.neuedu.common.ObjectMapperApi;
import com.neuedu.common.ServerResponse;
import com.neuedu.redis.RedisApi;
import com.neuedu.redis.RedisPool;
import com.neuedu.redis.RedisProperties;
import com.neuedu.sevice.IUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
public class UploadController {



    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/redis")
    public String getJedis(){

        Jedis jedis=jedisPool.getResource();
        String value=jedis.set("root","root1");
        jedisPool.returnResource(jedis);
        return value;
    }

    @Autowired
    private RedisApi redisApi;
    @RequestMapping("/key")
    @ResponseBody
    public String getKey(@RequestParam("key")String key){
        String value=redisApi.get(key);
        return value;
    }
    @Autowired
    ObjectMapperApi objectMapperApi;

    @RequestMapping(value = "/upload" ,method = RequestMethod.GET)
    public String upload(){

       // System.out.println(redisProperties.getMaxIdle());

        return "upload";
    }

    @Value("${img.local.path}")
    private  String imgPath;
    @Autowired
    IUploadService uploadService;

    @RequestMapping(value = "/upload" ,method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse upload(@RequestParam("picfile")MultipartFile uploadFile){
        File newFile=null;
        if (uploadFile!=null&&uploadFile.getOriginalFilename()!=null&&!uploadFile.getOriginalFilename().equals("")){
            String uuid= UUID.randomUUID().toString();
            String fileName=uploadFile.getOriginalFilename();
            String fileextendname=fileName.substring(fileName.lastIndexOf(".")+1);
            String newFilename=uuid+"."+fileextendname;
            System.out.println(newFilename);
            File file=new File(imgPath);
            if(!file.exists()){
                file.mkdir();
            }
            newFile=new File(file,newFilename);
            try {
                uploadFile.transferTo(newFile);
                //写到七牛云
                return uploadService.uploadFile(newFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return ServerResponse.createServerResponseByFailure(1,"上传失败");
    }
}
