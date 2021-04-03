package com.gps.bean;

public class Message {
    //接收消息的格式   {status:0,result: "", data:{}}
    //消息的内容
    private String result;
    //消息所携带的一组数据
    private  Object data;
    //状态码 0成功  -1失败
    private int status;


    public Message( int status,String result) {
        this.result = result;
        this.status = status;
    }

    public Message(int status) {
        this.status = status;
    }

    public Message(Object data) {
        this.data = data;
    }

    public Message(String result) {
        this.result = result;
    }

    public Message(String result, Object data, int status) {
        this.result = result;
        this.data = data;
        this.status = status;
    }

    public Message() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
