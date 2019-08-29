package com.neuedu.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/***
 *
 * 服务端响应对象
 * */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ServerResponse<T> {
    //接口状态
    private int status;
    //接口的返回信息
    private String msg;
    //接口返回的数据
    private T data;

    private ServerResponse(){}
    private ServerResponse(int status){
        this.status=status;
    }
    private ServerResponse(int status,String msg){
        this.status=status;
        this.msg=msg;
    }
    private ServerResponse(int status,T data){
        this.status=status;
        this.data=data;
    }
    private ServerResponse(String msg, T data) {
        this.msg = msg;
        this.data = data;
    }
    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static <T> ServerResponse<T> createServerResponseBySuccess(){
        return new ServerResponse<>(0);
    }
    public static <T> ServerResponse<T> createServerResponseBySuccess(T data){
        return new ServerResponse<>(0,data);
    }
//    public static <T> ServerResponse<T> createServerResponseBySuccess(String msg){
//        return new ServerResponse<>(0,msg);
//    }
    public static <T> ServerResponse<T> createServerResponseBySuccess(int status,String msg,T data){
    return new ServerResponse<>(0,msg,data);
}

    public static <T> ServerResponse<T> createServerResponseByFailure(int status,String msg){
        return new ServerResponse<>(status,msg);
    }

    public static <T> ServerResponse<T> createServerResponseBySuccess(String msg,T data){
        return new ServerResponse<>(msg,data);
    }
    public static <T> ServerResponse<T> createServerResponseBySuccess(int status,String msg){
        return new ServerResponse<>(status,msg);
    }
    /**
     * 判断接口是否调用成功
     * */
    @JsonIgnore
    public boolean isSuccess(){
        return this.status==0;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
